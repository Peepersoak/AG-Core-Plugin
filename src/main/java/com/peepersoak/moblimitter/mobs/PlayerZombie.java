package com.peepersoak.moblimitter.mobs;

import com.peepersoak.moblimitter.MobLimitter;
import com.peepersoak.moblimitter.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

public class PlayerZombie implements Listener {

    private final NamespacedKey key = new NamespacedKey(MobLimitter.instance, "PlayerZombie");

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getPlayer();

        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        Location location = player.getLocation();

        if (location.getY() > Utils.buffMobYSpawn()) return;

        String name = Utils.color("&c" + player.getName());

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(player);
        skull.setItemMeta(meta);

        Zombie zombie = (Zombie) player.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        zombie.getPersistentDataContainer().set(key, PersistentDataType.STRING, "PZombie");

        zombie.setCustomName(name);
        zombie.setCustomNameVisible(true);
        zombie.setCanPickupItems(false);
        zombie.setRemoveWhenFarAway(true);

        zombie.getEquipment().setHelmet(skull);
        zombie.getEquipment().setChestplate(chestplate);
        zombie.getEquipment().setLeggings(leggings);
        zombie.getEquipment().setBoots(boots);
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {
        if (!(e.getEntity() instanceof Zombie zombie)) return;
        if (zombie.getPersistentDataContainer().has(key)) {
            e.getDrops().clear();
        }
    }
}
