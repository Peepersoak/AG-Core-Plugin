package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.MobLimitter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemsList {

    public ItemsList() {
        helmet.add(Material.CHAINMAIL_HELMET);
        helmet.add(Material.IRON_HELMET);
        helmet.add(Material.GOLDEN_HELMET);
        helmet.add(Material.DIAMOND_HELMET);
        helmet.add(Material.NETHERITE_HELMET);

        chestplate.add(Material.CHAINMAIL_CHESTPLATE);
        chestplate.add(Material.IRON_CHESTPLATE);
        chestplate.add(Material.GOLDEN_CHESTPLATE);
        chestplate.add(Material.DIAMOND_CHESTPLATE);
        chestplate.add(Material.NETHERITE_CHESTPLATE);

        leggings.add(Material.CHAINMAIL_LEGGINGS);
        leggings.add(Material.IRON_LEGGINGS);
        leggings.add(Material.GOLDEN_LEGGINGS);
        leggings.add(Material.DIAMOND_LEGGINGS);
        leggings.add(Material.NETHERITE_LEGGINGS);

        boots.add(Material.CHAINMAIL_BOOTS);
        boots.add(Material.IRON_BOOTS);
        boots.add(Material.GOLDEN_BOOTS);
        boots.add(Material.DIAMOND_BOOTS);
        boots.add(Material.NETHERITE_BOOTS);

        main.add(Material.GOLDEN_SWORD);
        main.add(Material.DIAMOND_SWORD);
        main.add(Material.NETHERITE_SWORD);

        main.add(Material.GOLDEN_AXE);
        main.add(Material.DIAMOND_AXE);
        main.add(Material.NETHERITE_AXE);

        main.add(Material.GOLDEN_PICKAXE);
        main.add(Material.DIAMOND_PICKAXE);
        main.add(Material.NETHERITE_PICKAXE);

        main.add(Material.GOLDEN_SHOVEL);
        main.add(Material.DIAMOND_SHOVEL);
        main.add(Material.NETHERITE_SHOVEL);

        main.add(Material.GOLDEN_HOE);
        main.add(Material.DIAMOND_HOE);
        main.add(Material.NETHERITE_HOE);
    }

    private final List<Material> helmet = new ArrayList<>();
    private final List<Material> chestplate = new ArrayList<>();
    private final List<Material> leggings = new ArrayList<>();
    private final List<Material> boots = new ArrayList<>();
    private final List<Material> main = new ArrayList<>();
    private final Random random = new Random();

    private final NamespacedKey key = new NamespacedKey(MobLimitter.instance, "CustomItem");

    public ItemStack getHelmet() {
        ItemStack item = new ItemStack(helmet.get(random.nextInt(helmet.size())));
        return enchantItems(item);
    }

    public ItemStack getChestplate() {
        ItemStack item = new ItemStack(chestplate.get(random.nextInt(chestplate.size())));
        return enchantItems(item);
    }

    public ItemStack getLeggings() {
        ItemStack item = new ItemStack(leggings.get(random.nextInt(leggings.size())));
        return enchantItems(item);
    }

    public ItemStack getBoots() {
        ItemStack item = new ItemStack(boots.get(random.nextInt(boots.size())));
        return enchantItems(item);
    }

    public ItemStack getMain() {
        ItemStack item = new ItemStack(main.get(random.nextInt(main.size())));
        return enchantItems(item);
    }

    public NamespacedKey getKey() {
        return key;
    }

    private ItemStack enchantItems(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "CustomItem");
        item.setItemMeta(meta);

        for (Enchantment enchantment : Enchantment.values()) {
            if (enchantment.getKey().toString().split(":")[1].equalsIgnoreCase("thorns")) continue;
            if (enchantment.canEnchantItem(item)) {
                item.addUnsafeEnchantment(enchantment, enchantment.getMaxLevel());
            }
        }

        return item;
    }
}
