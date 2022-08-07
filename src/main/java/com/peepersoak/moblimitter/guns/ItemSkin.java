package com.peepersoak.moblimitter.guns;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class ItemSkin implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 1) {
            String type = args[0];
            ItemStack main = player.getInventory().getItemInMainHand();
            if (main.getType() != Material.CROSSBOW) return false;

            ItemMeta meta = main.getItemMeta();
            if (meta == null) return false;

            if (type.equalsIgnoreCase("operator")) {
                meta.setCustomModelData(1913161774);
            }
            main.setItemMeta(meta);
        }

        return false;
    }
}
