package net.mangolise.combat.profiles;

import net.mangolise.combat.CombatProfile;
import net.mangolise.combat.CombatUtils;
import net.minestom.server.entity.EquipmentSlot;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemComponent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.EnchantmentList;
import net.minestom.server.item.enchant.Enchantment;

/**
 * Combat profile based on <a href="https://github.com/CoPokBl/BetterCombat">BetterCombat (AKA Serble Combat)</a>
 */
public class MangoProfile implements CombatProfile {

    private static float calculateBaseDamage(Player player) {
        Material material = player.getItemInHand(Player.Hand.MAIN).material();
        float typeBase = switch (CombatUtils.getType(material)) {
            case AXE -> 4;
            case SWORD -> 3;
            case HOE -> 2.2f;
            case SHOVEL -> 2;
            default -> 1;
        };
        if (typeBase == 1) {
            return 1f;
        }
        float tierMod = switch (CombatUtils.getTier(material)) {
            case STONE -> 1.2f;
            case IRON, GOLD -> 1.5f;
            case DIAMOND -> 1.8f;
            case NETHERITE -> 2f;
            default -> 1f;
        };
        return typeBase * tierMod;
    }

    private static float getArmourValue(ItemStack item) {
        Material material = item.material();
        float baseAmount = switch (CombatUtils.getType(material)) {
            case HELMET -> 1.3f;
            case CHESTPLATE -> 2f;
            case LEGGINGS -> 1.7f;
            case BOOTS -> 1f;
            default -> 0f;
        };
        if (baseAmount == 0) {
            return 0f;
        }
        float tierMod = switch (CombatUtils.getTier(material)) {
            case LEATHER -> 1f;
            case IRON, GOLD, STONE -> 1.2f;
            case DIAMOND -> 1.5f;
            case NETHERITE -> 1.8f;
            default -> throw new IllegalStateException();
        };
        return baseAmount * tierMod;
    }

    private static float calculateBaseReduction(Player player) {
        ItemStack helmet = player.getEquipment(EquipmentSlot.HELMET);
        ItemStack chestplate = player.getEquipment(EquipmentSlot.CHESTPLATE);
        ItemStack leggings = player.getEquipment(EquipmentSlot.LEGGINGS);
        ItemStack boots = player.getEquipment(EquipmentSlot.BOOTS);

        float armourValue = getArmourValue(helmet) + getArmourValue(chestplate) + getArmourValue(leggings) + getArmourValue(boots);
        return 1 - (armourValue / 14);
    }

    @Override
    public float calculateDamage(Player attacker, Player victim) {
        float baseDmg = calculateBaseDamage(attacker);
        float reduction = calculateBaseReduction(victim);

        return baseDmg * reduction;
    }

    private int getKbLevel(Player player) {
        ItemStack stack = player.getItemInMainHand();
        EnchantmentList enchs = stack.get(ItemComponent.ENCHANTMENTS);
        if (enchs == null) {
            return 0;
        }
        if (!enchs.has(Enchantment.KNOCKBACK)) {
            return 0;
        }
        return enchs.level(Enchantment.KNOCKBACK);
    }

    @Override
    public void applyKb(Player attacker, Player victim) {
        int kb = getKbLevel(attacker);

        victim.setVelocity(attacker.getPosition().direction().mul(10 + (8 * kb)).withY(8));
    }
}
