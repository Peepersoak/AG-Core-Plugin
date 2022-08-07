package com.peepersoak.moblimitter.guns;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Sounds implements Listener {

    @EventHandler
    public void onShoot(EntityShootBowEvent e) {
        ItemStack bow = e.getBow();

        if (bow == null) return;
        if (bow.getType() != Material.CROSSBOW) return;

        ItemMeta meta = bow.getItemMeta();
        if (meta == null) return;
        if (!meta.hasCustomModelData()) return;
        if (meta.getCustomModelData() != 1913161774) return;

        e.getEntity().getWorld().playSound(e.getEntity().getLocation(), "custom:sniper", 100, 1);
    }
}
