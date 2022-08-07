package com.peepersoak.moblimitter;

import com.peepersoak.moblimitter.guns.ItemSkin;
import com.peepersoak.moblimitter.commands.OpenInventory;
import com.peepersoak.moblimitter.commands.TeleportWorld;
import com.peepersoak.moblimitter.commands.TeleportWorldCompleter;
import com.peepersoak.moblimitter.guns.Sounds;
import com.peepersoak.moblimitter.mobs.*;
import com.peepersoak.moblimitter.protection.AccountProtection;
import com.peepersoak.moblimitter.protection.AcountProtectionCompletor;
import com.peepersoak.moblimitter.world.WorldEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

public final class MobLimitter extends JavaPlugin implements Listener {

    public static MobLimitter instance;
    private LimitMobs limitMobs;
    private final DeathLocation deathLocation = new DeathLocation();
    private List<String> worldNames;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        instance = this;
        worldNames = getConfig().getStringList("Add_World");

        limitMobs = new LimitMobs(getConfig());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(deathLocation, this);
        Bukkit.getPluginManager().registerEvents(new InteractiveChat(), this);
        Bukkit.getPluginManager().registerEvents(new AnvilName(), this);
//        Bukkit.getPluginManager().registerEvents(new FishPlayerEvent(), this);
        Bukkit.getPluginManager().registerEvents(new Izon(), this);
        Bukkit.getPluginManager().registerEvents(new CreeperMob(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerZombie(), this);

        Bukkit.getPluginManager().registerEvents(new WorldEvent(), this);

        Bukkit.getPluginManager().registerEvents(new ZombieSnatcher(), this);
        Bukkit.getPluginManager().registerEvents(new SuperZombie(), this);

        Bukkit.getPluginManager().registerEvents(new GeneralEvents(), this);

        Objects.requireNonNull(getCommand("removetimer")).setExecutor(deathLocation);
        Objects.requireNonNull(getCommand("open")).setExecutor(new OpenInventory());

        Objects.requireNonNull(getCommand("moveworld")).setExecutor(new TeleportWorld());
        Objects.requireNonNull(getCommand("moveworld")).setTabCompleter(new TeleportWorldCompleter());

        final AccountProtection accountProtection = new AccountProtection();
        Bukkit.getPluginManager().registerEvents(accountProtection, this);
        Objects.requireNonNull(getCommand("account")).setExecutor(accountProtection);
        Objects.requireNonNull(getCommand("account")).setTabCompleter(new AcountProtectionCompletor());

        Objects.requireNonNull(getCommand("val")).setExecutor(new ItemSkin());
        Bukkit.getPluginManager().registerEvents(new Sounds(), this);

        loadWorld();
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

    private void loadWorld() {
        for (String worldName: worldNames) {
            new WorldCreator(worldName).createWorld();
            MobLimitter.instance.getLogger().info(worldName + " has been loaded");
        }
    }

    public List<String> getWorldNames() {
        return worldNames;
    }
}
