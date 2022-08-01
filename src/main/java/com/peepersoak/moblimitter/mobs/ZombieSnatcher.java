package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ZombieSnatcher implements Listener {

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Zombie)) return;
        if (!(e.getEntity() instanceof Player player)) return;

        boolean shouldSnatch = Utils.getRandom(3, 100);
        if (!shouldSnatch) return;

        List<ItemStack> items = new ArrayList<>();
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item == null || item.getType() == Material.AIR) continue;
            items.add(item);
        }

        if (items.isEmpty()) return;

        ItemStack item = items.get(Utils.getRandomNumber(items.size()));
        String material = item.getType().toString().toLowerCase();
        ItemStack air = new ItemStack(Material.AIR);

        if (material.contains("helmet")) {
            player.getInventory().setHelmet(air);
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else if (material.contains("chestplate")) {
            player.getInventory().setChestplate(air);
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else if (material.contains("leggings")) {
            player.getInventory().setLeggings(air);
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        } else if (material.contains("boots")) {
            player.getInventory().setBoots(air);
            player.getWorld().dropItemNaturally(player.getLocation(), item);
        }
    }
}
