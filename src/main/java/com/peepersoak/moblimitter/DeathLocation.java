package com.peepersoak.moblimitter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DeathLocation implements Listener, CommandExecutor {

    private final HashMap<UUID, BossBar> deathTimer = new HashMap<>();
    private final List<UUID> hasRespawn = new ArrayList<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        UUID uuid = player.getUniqueId();
        Location location = player.getLocation();

        Chunk chunk = location.getChunk();
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        String world = player.getLocation().getWorld().getName();
        String message = ChatColor.GRAY + "You died! [" + x + "-" + y + "-" + z + " in world " + world + "]";

        player.sendMessage(message);

        if (deathTimer.containsKey(uuid)) return;
        hasRespawn.remove(uuid);

        String title = Utils.color("&cDespawn Timer: &6" + x + "x " + y + "y " + z + "z" + " &b%distance%");

        BossBar bossBar = Bukkit.createBossBar(title.replace("%distance%", ""), BarColor.YELLOW, BarStyle.SOLID);
        bossBar.addPlayer(player);
        bossBar.setVisible(true);

        deathTimer.put(uuid, bossBar);

        new BukkitRunnable() {
            final double despawnIn = 300.0;
            double count = despawnIn;
            @Override
            public void run() {
                if (!deathTimer.containsKey(uuid)) {
                    this.cancel();
                    return;
                }
                // Remove player when they are near enough to the location
                if (hasRespawn.contains(uuid)) {
                    Player target = Bukkit.getPlayer(uuid);
                    if (target != null && target.isOnline()) {
                        if (location.getWorld() == target.getWorld()) {
                            int distance = (int) Math.floor(target.getLocation().distance(location));
                            bossBar.setTitle(title.replace("%distance%", distance + " blocks away"));
                            if (distance < 10) {
                                removePlayer(player);
                                this.cancel();
                            }
                        } else {
                            bossBar.setTitle(title.replace("%distance%", ""));
                        }
                    }
                }

                if (location.getWorld().isChunkLoaded(chunkX, chunkZ)) {
                    double progress = count/despawnIn;
                    bossBar.setProgress(progress);
                    count--;
                }

                if (count <= 0) {
                    removePlayer(player);
                    this.cancel();
                }
            }
        }.runTaskTimer(MobLimitter.instance, 0, 20);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (deathTimer.containsKey(uuid)) {
            BossBar bossBar = deathTimer.get(uuid);
            bossBar.addPlayer(player);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        hasRespawn.add(e.getPlayer().getUniqueId());
    }

    private void removePlayer(Player player) {
        UUID uuid = player.getUniqueId();

        if (deathTimer.containsKey(uuid)) {
            BossBar bossBar = deathTimer.get(uuid);
            bossBar.setVisible(false);
            bossBar.removeAll();
        }

        deathTimer.remove(uuid);
        hasRespawn.remove(uuid);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player player) {
            removePlayer(player);
        }

        return false;
    }
}
