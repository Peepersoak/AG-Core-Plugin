package com.peepersoak.moblimitter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilName implements Listener {

    @Deprecated
    @EventHandler
    public void onName(PrepareAnvilEvent e) {
        if (e.getResult() != null) {
            ItemStack item = e.getResult();

            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.color(meta.getDisplayName()));
            item.setItemMeta(meta);

            e.setResult(item);
        }
    }
}
