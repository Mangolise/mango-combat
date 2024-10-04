package net.mangolise.combat;

import net.minestom.server.item.Material;

public class CombatUtils {

    public static boolean isHoe(Material material) {
        return material == Material.WOODEN_HOE ||
                material == Material.STONE_HOE ||
                material == Material.IRON_HOE ||
                material == Material.DIAMOND_HOE ||
                material == Material.GOLDEN_HOE ||
                material == Material.NETHERITE_HOE;
    }

    public static boolean isPickaxe(Material material) {
        return material == Material.WOODEN_PICKAXE ||
                material == Material.STONE_PICKAXE ||
                material == Material.IRON_PICKAXE ||
                material == Material.DIAMOND_PICKAXE ||
                material == Material.GOLDEN_PICKAXE ||
                material == Material.NETHERITE_PICKAXE;
    }

    public static boolean isAxe(Material material) {
        return material == Material.WOODEN_AXE ||
                material == Material.STONE_AXE ||
                material == Material.IRON_AXE ||
                material == Material.DIAMOND_AXE ||
                material == Material.GOLDEN_AXE ||
                material == Material.NETHERITE_AXE;
    }

    public static boolean isShovel(Material material) {
        return material == Material.WOODEN_SHOVEL ||
                material == Material.STONE_SHOVEL ||
                material == Material.IRON_SHOVEL ||
                material == Material.DIAMOND_SHOVEL ||
                material == Material.GOLDEN_SHOVEL ||
                material == Material.NETHERITE_SHOVEL;
    }

    public static boolean isSword(Material material) {
        return material == Material.WOODEN_SWORD ||
                material == Material.STONE_SWORD ||
                material == Material.IRON_SWORD ||
                material == Material.DIAMOND_SWORD ||
                material == Material.GOLDEN_SWORD ||
                material == Material.NETHERITE_SWORD;
    }

    public static boolean isChestplate(Material material) {
        return material == Material.LEATHER_CHESTPLATE ||
                material == Material.CHAINMAIL_CHESTPLATE ||
                material == Material.IRON_CHESTPLATE ||
                material == Material.GOLDEN_CHESTPLATE ||
                material == Material.DIAMOND_CHESTPLATE ||
                material == Material.NETHERITE_CHESTPLATE;
    }

    public static boolean isHelmet(Material material) {
        return material == Material.LEATHER_HELMET ||
                material == Material.CHAINMAIL_HELMET ||
                material == Material.IRON_HELMET ||
                material == Material.GOLDEN_HELMET ||
                material == Material.DIAMOND_HELMET ||
                material == Material.NETHERITE_HELMET;
    }

    public static boolean isLeggings(Material material) {
        return material == Material.LEATHER_LEGGINGS ||
                material == Material.CHAINMAIL_LEGGINGS ||
                material == Material.IRON_LEGGINGS ||
                material == Material.GOLDEN_LEGGINGS ||
                material == Material.DIAMOND_LEGGINGS ||
                material == Material.NETHERITE_LEGGINGS;
    }

    public static boolean isBoots(Material material) {
        return material == Material.LEATHER_BOOTS ||
                material == Material.CHAINMAIL_BOOTS ||
                material == Material.IRON_BOOTS ||
                material == Material.GOLDEN_BOOTS ||
                material == Material.DIAMOND_BOOTS ||
                material == Material.NETHERITE_BOOTS;
    }

    public static boolean isArmor(Material material) {
        return isHelmet(material) || isChestplate(material) || isLeggings(material) || isBoots(material);
    }

    public static ItemTier getTier(Material material) {
        String name = material.name();
        if (name.contains("wooden_")) return ItemTier.WOOD;
        if (name.contains("leather_")) return ItemTier.LEATHER;
        if (name.contains("chainmail_")) return ItemTier.STONE;
        if (name.contains("stone_")) return ItemTier.STONE;
        if (name.contains("iron_")) return ItemTier.IRON;
        if (name.contains("golden_")) return ItemTier.GOLD;
        if (name.contains("diamond_")) return ItemTier.DIAMOND;
        if (name.contains("netherite_")) return ItemTier.NETHERITE;
        return ItemTier.NONE;
    }

    public static ItemType getType(Material material) {
        if (isAxe(material)) return ItemType.AXE;
        if (isPickaxe(material)) return ItemType.PICKAXE;
        if (isHoe(material)) return ItemType.HOE;
        if (isShovel(material)) return ItemType.SHOVEL;
        if (isSword(material)) return ItemType.SWORD;
        if (isHelmet(material)) return ItemType.HELMET;
        if (isChestplate(material)) return ItemType.CHESTPLATE;
        if (isLeggings(material)) return ItemType.LEGGINGS;
        if (isBoots(material)) return ItemType.BOOTS;
        return ItemType.NONE;
    }
}
