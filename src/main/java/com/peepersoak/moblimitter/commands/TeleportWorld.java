package com.peepersoak.moblimitter.commands;

import com.peepersoak.moblimitter.MobLimitter;
import com.peepersoak.moblimitter.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class TeleportWorld implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!player.isOp()) return false;

        if (args.length == 1) {
            String worldName = args[0];
            World world = Bukkit.getWorld(worldName);
            if (world == null) return false;
            Location location = world.getSpawnLocation();

            new BukkitRunnable() {
                int count = 10;
                @Override
                public void run() {
                    if (count == 10) {
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            target.sendMessage(Utils.color("&c10 seconds before teleporting! Your Inventory will be cleared so removed all of your items!!"));
                        }
                    }

                    if (count <= 0) {
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            target.getInventory().clear();
                            target.teleport(location);
                        }
                        this.cancel();
                        return;
                    }

                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.sendMessage(Utils.color("&c" + count + " seconds remaining"));
                    }

                    count--;
                }
            }.runTaskTimer(MobLimitter.instance, 0, 20);
        } else if (args.length == 2) {
            String targetName = args[0];
            String worldName = args[1];

            Player target = Bukkit.getPlayer(targetName);
            if (target == null || !target.isOnline()) return false;

            World world = Bukkit.getWorld(worldName);
            if (world == null) return false;
            Location location = world.getSpawnLocation();

            new BukkitRunnable() {
                int count = 10;
                @Override
                public void run() {
                    if (!target.isOnline()) {
                        this.cancel();
                        return;
                    }
                    if (count == 10) {
                        target.sendMessage(Utils.color("&c10 seconds before teleporting! Your Inventory will be cleared so removed all of your items!!"));
                    }
                    if (count <= 0) {
                        target.getInventory().clear();
                        target.teleport(location);
                        this.cancel();
                        return;
                    }
                    target.sendMessage(Utils.color("&c" + count + " seconds remaining"));
                    count--;
                }
            }.runTaskTimer(MobLimitter.instance, 0, 20);
        }
        return false;
    }
}
