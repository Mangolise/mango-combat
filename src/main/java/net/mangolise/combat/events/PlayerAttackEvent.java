package net.mangolise.combat.events;

import net.minestom.server.entity.Player;
import net.minestom.server.event.Event;

public class PlayerAttackEvent implements Event {
    private final Player attacker;
    private final Player victim;
    private float damage;
    private boolean cancelled;

    public PlayerAttackEvent(Player attacker, Player victim, float damage) {
        this.attacker = attacker;
        this.victim = victim;
        this.damage = damage;
    }

    public Player attacker() {
        return attacker;
    }

    public Player victim() {
        return victim;
    }

    public float damage() {
        return damage;
    }

    public void setDamage(float damage) {
        this.damage = damage;
    }

    public boolean cancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void makeNonLethal() {
        if (damage < victim.getHealth()) {
            return;
        }

        damage = victim.getHealth() - 0.1f;
    }
}
