package net.mangolise.combat;

import net.minestom.server.coordinate.Point;
import net.minestom.server.entity.Entity;
import net.minestom.server.entity.damage.Damage;
import net.minestom.server.entity.damage.DamageType;
import net.minestom.server.registry.DynamicRegistry;
import net.minestom.server.registry.RegistryKey;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MangoCombatDamage extends Damage {
    /**
     * Creates a new damage type.
     *
     * @param type           the type of this damage
     * @param source         The source of the damage. For direct hits (melee), this will be the same as the attacker. For indirect hits (projectiles), this will be the projectile
     * @param attacker       The attacker that initiated this damage
     * @param sourcePosition The position of the source of damage
     * @param amount         amount of damage
     */
    public MangoCombatDamage(RegistryKey<DamageType> type, @Nullable Entity source, @Nullable Entity attacker, @Nullable Point sourcePosition, float amount) {
        super(type, source, attacker, sourcePosition, amount);
    }

    public MangoCombatDamage(Damage child) {
        super(child.getType(), child.getSource(), child.getAttacker(), child.getSourcePosition(), child.getAmount());
    }
}
