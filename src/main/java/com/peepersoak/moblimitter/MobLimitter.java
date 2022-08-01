package com.peepersoak.moblimitter;

import com.peepersoak.moblimitter.commands.OpenInventory;
import com.peepersoak.moblimitter.commands.TeleportWorld;
import com.peepersoak.moblimitter.commands.TeleportWorldCompleter;
import com.peepersoak.moblimitter.mobs.ZombieSnatcher;
import com.peepersoak.moblimitter.world.WorldCommand;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MobLimitter extends JavaPlugin implements Listener {

    private LimitMobs limitMobs;
    private final DeathLocation deathLocation = new DeathLocation();

    public static MobLimitter instance;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        instance = this;

        limitMobs = new LimitMobs(getConfig());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(deathLocation, this);
        Bukkit.getPluginManager().registerEvents(new InteractiveChat(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilName(), this);
//        Bukkit.getPluginManager().registerEvents(new FishPlayerEvent(), this);

        Bukkit.getPluginManager().registerEvents(new ZombieSnatcher(), this);

        Objects.requireNonNull(getCommand("removetimer")).setExecutor(deathLocation);
        Objects.requireNonNull(getCommand("open")).setExecutor(new OpenInventory());

        Objects.requireNonNull(getCommand("moveworld")).setExecutor(new TeleportWorld());
        Objects.requireNonNull(getCommand("moveworld")).setTabCompleter(new TeleportWorldCompleter());

        Objects.requireNonNull(getCommand("world")).setExecutor(new WorldCommand());
    }

    @EventHandler
    public void onBreed(EntityBreedEvent e) {
        if (!(e.getEntity() instanceof Animals animal)) return;
        boolean limit = limitMobs.limitMob(animal);
        if (limit) {
            if (e.getBreeder() != null) {
                e.getBreeder().sendMessage(Utils.color("&cYou reached the breeding limit"));
            }
            animal.remove();
        }
    }
}
