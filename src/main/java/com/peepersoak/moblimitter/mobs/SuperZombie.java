package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.Utils;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;
import java.util.Random;

public class SuperZombie implements Listener {

    private final Random rand = new Random();

    private final ItemsList list = new ItemsList();

    @EventHandler
    public void onZombieSpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Zombie zombie)) return;
        if (zombie.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) return;
        if (zombie.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.DROWNED) return;
        if (zombie.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.INFECTION) return;
        if (zombie.getEntitySpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) return;
        if (e.getLocation().getY() > Utils.buffMobYSpawn()) return;
        if (e.getLocation().getWorld().getEnvironment() != World.Environment.NORMAL) return;

        if (rand.nextInt(100) + 1 < 25) {
            zombie.getEquipment().setHelmet(list.getHelmet());
        }

        if (rand.nextInt(100) + 1 < 25) {
            zombie.getEquipment().setChestplate(list.getChestplate());
        }

        if (rand.nextInt(100) + 1 < 25) {
            zombie.getEquipment().setLeggings(list.getLeggings());
        }

        if (rand.nextInt(100) + 1 < 25) {
            zombie.getEquipment().setBoots(list.getBoots());
        }

        if (rand.nextInt(100) + 1 < 25) {
            zombie.getEquipment().setItemInMainHand(list.getMain());
        }

        zombie.setCanPickupItems(false);

        double health = 40;
        Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(health);
        zombie.setHealth(health);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Zombie)) return;
        e.getDrops().removeIf(i -> i.getItemMeta().getPersistentDataContainer().has(list.getKey()));
    }
}
