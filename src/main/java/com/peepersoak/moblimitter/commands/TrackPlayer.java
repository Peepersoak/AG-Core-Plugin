package com.peepersoak.moblimitter.commands;

import com.peepersoak.moblimitter.MobLimitter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class TrackPlayer implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 1) {
            String targetName = args[0];

            Player target = Bukkit.getPlayer(targetName);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (target == null || !target.isOnline()) {
                        this.cancel();
                        return;
                    }
                    player.setCompassTarget(target.getLocation());
                }
            }.runTaskTimer(MobLimitter.instance, 0, 20);
        } else {
            player.setCompassTarget(player.getWorld().getSpawnLocation());
        }

        return false;
    }
}
