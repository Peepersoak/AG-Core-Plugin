package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.MobLimitter;
import com.peepersoak.moblimitter.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.Objects;
import java.util.Random;

public class Izon implements Listener {

    private final NamespacedKey zombieKey = new NamespacedKey(MobLimitter.instance, "Izonian");
    private Zombie izon = null;
    private final Random rand = new Random();

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Zombie zombie)) return;
        if (e.getLocation().getY() > Utils.buffMobYSpawn()) return;
        if (rand.nextInt(100) + 1 > 25) return;
        if (izon != null && !izon.isDead()) return;

        zombie.getPersistentDataContainer().set(zombieKey, PersistentDataType.STRING, "Izonbie");

        ItemStack helmet = getFullEnchantItem(new ItemStack(Material.NETHERITE_HELMET));
        ItemStack chestplate = getFullEnchantItem(new ItemStack(Material.NETHERITE_CHESTPLATE));
        ItemStack leggings = getFullEnchantItem(new ItemStack(Material.NETHERITE_LEGGINGS));
        ItemStack boots = getFullEnchantItem(new ItemStack(Material.NETHERITE_BOOTS));
        ItemStack axe = getFullEnchantItem(new ItemStack(Material.NETHERITE_SWORD));
        ItemStack shield = getFullEnchantItem(new ItemStack(Material.SHIELD));

        double MAX_HEALTH = 50;
        double DAMAGE = 20;

        Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(MAX_HEALTH);
        Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(DAMAGE);

        zombie.setCanPickupItems(false);
        zombie.setShouldBurnInDay(false);
        zombie.setBaby();
        zombie.setHealth(MAX_HEALTH);
        zombie.setCustomName(Utils.color("&cIzonbie"));
        zombie.setCustomNameVisible(true);
        zombie.setRemoveWhenFarAway(true);
        
        zombie.getEquipment().setHelmet(helmet);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);
        zombie.getEquipment().setItemInMainHand(axe);
        zombie.getEquipment().setItemInOffHand(shield);

        izon = zombie;
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Zombie zombie)) return;
        if (zombie.getPersistentDataContainer().has(zombieKey)) {
            e.getDrops().clear();
            izon = null;
        }
    }

    private ItemStack getFullEnchantItem(ItemStack itemStack) {
        for (Enchantment enchantment : Enchantment.values()) {
            if (enchantment.canEnchantItem(itemStack)) {
                itemStack.addUnsafeEnchantment(enchantment, enchantment.getMaxLevel());
            }
        }
        return itemStack;
    }
}
