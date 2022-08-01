package com.peepersoak.moblimitter;

import org.bukkit.ChatColor;

import java.util.Random;

public class Utils {
    private static final Random rand = new Random();
    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static boolean getRandom(int min, int max) {
        int random = rand.nextInt(max) + 1;
        return random <= min;
    }

    public static int getRandomNumber(int max) {
        return rand.nextInt(max);
    }
}
