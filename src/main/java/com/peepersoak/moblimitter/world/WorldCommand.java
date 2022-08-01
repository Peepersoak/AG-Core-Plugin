package com.peepersoak.moblimitter.world;

import com.peepersoak.moblimitter.MobLimitter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;
        if (!player.isOp()) return false;

        if (args.length == 1) {
            String cmd = args[0];

            if (cmd.equalsIgnoreCase("unload")) {
                for (String worldName : MobLimitter.instance.getWorldNames()) {
                    World world = Bukkit.getWorld(worldName);
                    if (world == null) continue;
                    Bukkit.unloadWorld(world, false);
                }
            }
        }

        return false;
    }
}
