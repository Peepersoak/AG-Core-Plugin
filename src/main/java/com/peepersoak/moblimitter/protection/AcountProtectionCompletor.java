package com.peepersoak.moblimitter.protection;

import com.peepersoak.moblimitter.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AcountProtectionCompletor implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            List<String> arr = new ArrayList<>();
            arr.add("set");
            arr.add("login");
            arr.add("remove");

            return arr;
        }

        if (args.length == 2) {
            List<String> arr = new ArrayList<>();
            arr.add(Utils.color("&7[password]"));

            return arr;
        }

        return null;
    }
}
