package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.Utils;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class Izon implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Zombie zombie)) return;

        zombie.setCustomName(Utils.color("&4&l"));
    }
}
