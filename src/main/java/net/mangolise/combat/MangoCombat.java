package net.mangolise.combat;

import net.mangolise.combat.events.PlayerAttackEvent;
import net.mangolise.combat.events.PlayerKilledEvent;
import net.minestom.server.MinecraftServer;
import net.minestom.server.coordinate.Vec;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.attribute.Attribute;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.entity.EntityAttackEvent;
import net.minestom.server.event.entity.EntityDamageEvent;
import net.minestom.server.event.player.PlayerDeathEvent;
import net.minestom.server.event.player.PlayerMoveEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.tag.Tag;
import net.minestom.server.tag.Taggable;
import org.jetbrains.annotations.Nullable;

public class MangoCombat {
    private static final Tag<Player> LAST_ATTACKER = Tag.Transient("last_attacker");
    private static final Tag<Damage> LAST_DAMAGE = Tag.Transient("last_damage");
    private static final Tag<Long> LAST_DAMAGE_TIME = Tag.Long("last_damage_time").defaultValue(0L);
    private static CombatConfig config;

    public static void enableGlobal(CombatConfig config) {
        MangoCombat.config = config;
        registerEvents(MinecraftServer.getGlobalEventHandler());
    }

    private static void registerEvents(GlobalEventHandler events) {
        events.addListener(PlayerSpawnEvent.class, e ->
                e.getPlayer().getAttribute(Attribute.ATTACK_SPEED).setBaseValue(100));
        events.addListener(EntityAttackEvent.class, e -> {
            if (!(e.getEntity() instanceof Player attacker)) return;
            if (!(e.getTarget() instanceof Player victim)) return;

            if (attacker.getGameMode() == GameMode.SPECTATOR) return;
            if (victim.getGameMode() != GameMode.ADVENTURE && victim.getGameMode() != GameMode.SURVIVAL) return;

            processAttack(attacker, victim);
        });
        events.addListener(PlayerDeathEvent.class, e -> {
            if (config.disableBuiltinDeathMsgs()) {
                e.setChatMessage(null);
            }
            processDeath(e.getPlayer(), false, e);
        });
        events.addListener(EntityDamageEvent.class, e -> {
            if (e.getDamage() instanceof MangoCombatDamage) {  // Don't process twice
                return;
            }
            if (e.getEntity() instanceof Player victim) {
                processDamage(victim, e.getDamage());
                e.setCancelled(true);
            }
        });

        if (config.voidDeath()) {
            events.addListener(PlayerMoveEvent.class, e -> {
                if (e.getNewPosition().y() < config.voidLevel()) {
                    processDamage(e.getPlayer(),
                            new Damage(
                                    DamageType.OUT_OF_WORLD,
                                    null,
                                    null,
                                    e.getNewPosition(),
                                    100f));
                }
            });
        }
    }

    public static void kill(Player player) {
        processDeath(player, true, null);
    }

    private static void processAttack(Player attacker, Player victim) {
        if ((System.currentTimeMillis() - victim.getTag(LAST_DAMAGE_TIME)) < config.iframes()) {
            return;
        }

        float damage = config.combat().calculateDamage(attacker, victim);

        PlayerAttackEvent attackEvent = new PlayerAttackEvent(attacker, victim, damage);
        MinecraftServer.getGlobalEventHandler().call(attackEvent);

        if (attackEvent.cancelled()) {
            return;
        }

        victim.setTag(LAST_ATTACKER, attacker);

        damage = attackEvent.damage();
        damage(victim, attacker, damage);
        config.combat().applyKb(attacker, victim);
    }

    private static void damage(Player victim, Player attacker, float damage) {
        Damage realDamage = new Damage(DamageType.PLAYER_ATTACK, attacker, attacker, attacker.getPosition(), damage);
        processDamage(victim, realDamage);
    }

    private static void processDamage(Player victim, Damage damage) {
        boolean lethal = damage.getAmount() >= victim.getHealth();
        MangoCombatDamage processed = new MangoCombatDamage(damage);

        victim.setTag(LAST_DAMAGE, damage);
        victim.setTag(LAST_DAMAGE_TIME, System.currentTimeMillis());

        if (!lethal) {
            victim.damage(processed);
        } else {
            if (config.fakeDeath()) {
                processed.setAmount(0);
                victim.damage(processed);
            } else {
                victim.damage(processed);
                return;  // ProcessDeath will be called by death event since it is real death
            }
        }

        if (!lethal) {
            return;
        }

        // Death
        if (config.fakeDeath()) {
            processDeath(victim, true, null);
        }
    }

    private static void fakeDeath(Player dead) {
        dead.setHealth(20);
        dead.setVelocity(Vec.ZERO);
        dead.teleport(dead.getRespawnPoint());
    }

    private static <T> @Nullable T getTagOrNull(Taggable entity, Tag<T> tag) {
        if (entity.hasTag(tag)) return entity.getTag(tag);
        return null;
    }

    private static void processDeath(Player dead, boolean doRealDeath, @Nullable PlayerDeathEvent deathEvent) {
        Damage lastDamage = getTagOrNull(dead, LAST_DAMAGE);
        Player lastAttacker = getTagOrNull(dead, LAST_ATTACKER);

        PlayerKilledEvent event = new PlayerKilledEvent(dead, lastAttacker, lastDamage, null, null);
        MinecraftServer.getGlobalEventHandler().call(event);

        if (event.cancelled()) {
            return;
        }

        if (deathEvent != null) {
            deathEvent.setDeathText(event.deathText());
            deathEvent.setChatMessage(event.deathMsg());
        }

        if (config.fakeDeath()) {
            fakeDeath(dead);
            if (event.deathMsg() != null && deathEvent == null) {
                dead.getInstance().sendMessage(event.deathMsg());
            }
            return;
        }

        if (doRealDeath) {
            dead.kill();
        }

        if (config.automaticRespawn()) {
            MinecraftServer.getSchedulerManager().scheduleNextTick(dead::respawn);
        }
    }
}
