package com.peepersoak.moblimitter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GeneralEvents implements Listener {

    @Deprecated
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.getPlayer().setResourcePack("https://www.dropbox.com/s/9qtlro3mcx6jry7/AG2022.zip?dl=1");
    }
}
