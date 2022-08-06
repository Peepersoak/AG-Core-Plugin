package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.Utils;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class CreeperMob implements Listener {

    private final Random rand = new Random();

    @EventHandler
    public void onSpawn(EntitySpawnEvent e) {
        if (!(e.getEntity() instanceof Creeper creeper)) return;
        if (e.getLocation().getY() > Utils.buffMobYSpawn()) return;
        if (rand.nextInt(100) + 1 > 3) return;

        creeper.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0, false , false));
    }
}
