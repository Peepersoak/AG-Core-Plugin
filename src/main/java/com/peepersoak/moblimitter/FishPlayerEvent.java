package com.peepersoak.moblimitter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class FishPlayerEvent implements Listener {

    @EventHandler
    public void onPlayerFish(PlayerFishEvent e) {
        Player player = e.getPlayer();
        if (e.getState() == PlayerFishEvent.State.CAUGHT_ENTITY) {
            if (e.getCaught() instanceof Player target) {
                e.setCancelled(true);
            }
        }
    }
}
