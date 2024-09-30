package net.mangolise.combat;

import net.minestom.server.entity.Player;

public interface CombatProfile {
    float calculateDamage(Player attacker, Player victim);
    void applyKb(Player attacker, Player victim);
}
