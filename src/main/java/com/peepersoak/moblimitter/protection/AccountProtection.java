package com.peepersoak.moblimitter.protection;

import com.peepersoak.moblimitter.MobLimitter;
import com.peepersoak.moblimitter.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class AccountProtection implements Listener, CommandExecutor {

    private final NamespacedKey key = new NamespacedKey(MobLimitter.instance, "AccountPassword");
    private final NamespacedKey opKey = new NamespacedKey(MobLimitter.instance, "OpSpecialKey");
    private final String PERMISSION = "NeedPermission";
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) return false;

        if (args.length == 2) {
            String cmd = args[0];
            String text = args[1];

            if (cmd.equalsIgnoreCase("set")) {
                if (player.getPersistentDataContainer().has(key)) {
                    player.sendMessage(Utils.color("&cA password has already been set on this account! Please remove it first before setting a new password using &e/account remove [password]"));
                    return false;
                }
                player.getPersistentDataContainer().set(key, PersistentDataType.STRING, text);
                player.sendMessage(Utils.color("&6Password has been set!"));
            }
            else if (cmd.equalsIgnoreCase("login")) {
                if (player.getPersistentDataContainer().has(key)) {
                    String pass = player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    if (pass != null && pass.equalsIgnoreCase(text)) {
                        player.addAttachment(MobLimitter.instance, PERMISSION, false);
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                        player.setGameMode(GameMode.SURVIVAL);
                        if (player.isOp()) {
                            player.getPersistentDataContainer().set(opKey, PersistentDataType.STRING, "OpKey");
                        }
                    } else {
                        player.sendMessage(Utils.color("&cWrong password!"));
                    }
                } else {
                    player.sendMessage(Utils.color("&cNo password has been set for this account!"));
                }
            }
            else if (cmd.equalsIgnoreCase("remove")) {
                if (player.isOp()) {
                    if (!player.getPersistentDataContainer().has(opKey)) {
                        player.sendMessage(Utils.color("&cLogin first before doing this!!"));
                        return false;
                    }
                    Player target = Bukkit.getPlayer(text);
                    if (target != null) {
                        target.getPersistentDataContainer().remove(key);
                        player.sendMessage(Utils.color("&6Password has been removed for " + target.getName()));
                        return false;
                    }
                }
                if (player.getPersistentDataContainer().has(key)) {
                    String pass = player.getPersistentDataContainer().get(key, PersistentDataType.STRING);
                    if (player.hasPermission(PERMISSION)) {
                        player.sendMessage(Utils.color("&cLogin first before doing this!!"));
                        return false;
                    }
                    if (pass != null && pass.equalsIgnoreCase(text)) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.removePotionEffect(PotionEffectType.BLINDNESS);
                        player.getPersistentDataContainer().remove(key);
                        player.sendMessage(Utils.color("&6Password has been removed!"));
                    } else {
                        player.sendMessage(Utils.color("&cWrong password!"));
                    }
                } else {
                    player.sendMessage(Utils.color("&cNo password has been set for this account!"));
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        if (player.isOp()) {
            player.getPersistentDataContainer().remove(opKey);
        }
        if (player.getPersistentDataContainer().has(key) || player.isOp()) {
            player.setGameMode(GameMode.SPECTATOR);
            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0));
            player.addAttachment(MobLimitter.instance, PERMISSION, true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (e.getPlayer().isOp()) {
            e.getPlayer().getPersistentDataContainer().remove(opKey);
        }
    }

    @Deprecated
    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (player.isOp() && player.getPersistentDataContainer().has(opKey)) return;
        if (player.hasPermission(PERMISSION)) {
            e.setCancelled(true);
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(Utils.color("&cPlease ENTER YOUR PASSWORD using, &e/account login [yourpassword]")));
        }
    }

    @Deprecated
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent e) {
        Player player = (Player) e.getPlayer();
        if (player.isOp() && player.getPersistentDataContainer().has(opKey)) return;
        if (player.hasPermission(PERMISSION)) {
            e.setCancelled(true);
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(Utils.color("&cPlease ENTER YOUR PASSWORD using, &e/account login [yourpassword]")));
        }
    }
    @Deprecated
    @EventHandler
    public void onThrow(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (player.isOp() && player.getPersistentDataContainer().has(opKey)) return;
        if (player.hasPermission(PERMISSION)) {
            e.setCancelled(true);
            player.spigot().sendMessage(
                    ChatMessageType.ACTION_BAR,
                    TextComponent.fromLegacyText(Utils.color("&cPlease ENTER YOUR PASSWORD using, &e/account login [yourpassword]")));
        }
    }
}
