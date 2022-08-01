package com.peepersoak.moblimitter;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

public class LimitMobs {

    public LimitMobs(FileConfiguration config) {
        this.config = config;
        init();
    }

    private final FileConfiguration config;
    private int xDistance = 10;
    private int yDistance = 5;
    private int zDistance = 10;
    private int maxMobs = 16;

    private void init() {
        ConfigurationSection distanceSection = config.getConfigurationSection("Distance");
        if (distanceSection == null) return;
        xDistance = distanceSection.getInt("x");
        yDistance = distanceSection.getInt("y");
        zDistance = distanceSection.getInt("z");
        maxMobs = config.getInt("Max_Mobs");
    }

    public boolean limitMob(Entity entity) {
        List<Entity> entities = entity.getNearbyEntities(xDistance, yDistance, zDistance);
        int count = 0;
        for (Entity ent : entities) {
            if (entity.getType() == ent.getType()) count++;
        }
        return count >= maxMobs;
    }
}
