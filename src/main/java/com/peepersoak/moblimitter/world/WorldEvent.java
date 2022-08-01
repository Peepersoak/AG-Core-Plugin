package com.peepersoak.moblimitter.world;

import com.peepersoak.moblimitter.MobLimitter;
import com.peepersoak.moblimitter.Utils;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.inventory.Inventory;

public class WorldEvent implements Listener {

    @EventHandler
    public void onWorldInit(WorldInitEvent e) {
        if (MobLimitter.instance.getWorldNames().contains(e.getWorld().getName())) {
            e.getWorld().setKeepSpawnInMemory(false);
        }
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Location location = e.getFrom();
        if (MobLimitter.instance.getWorldNames().contains(location.getWorld().getName())) {
               e.setCancelled(true);
               e.getPlayer().sendMessage(Utils.color("&cPortal is disabled on this world!"));
        }
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent e) {
        if (e.getEntity() instanceof Player) return;
        if (!(e.getEntity() instanceof LivingEntity)) return;
        Location location = e.getLocation();
        if (MobLimitter.instance.getWorldNames().contains(location.getWorld().getName())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEnderOpen(InventoryOpenEvent e) {
        if (e.getInventory().getType() != InventoryType.ENDER_CHEST) return;
        Location location = e.getPlayer().getLocation();
        if (MobLimitter.instance.getWorldNames().contains(location.getWorld().getName())) {
            e.setCancelled(true);
        }
    }
}
