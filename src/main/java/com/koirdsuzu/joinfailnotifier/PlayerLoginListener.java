package com.koirdsuzu.joinfailnotifier;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.permissions.ServerOperator;

import java.util.Locale;

public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event){
        final PlayerLoginEvent.Result result = event.getResult();
        if (result != PlayerLoginEvent.Result.ALLOWED){
            final String name = event.getPlayer().getName();
            final String reason = JoinFailNotifier.config.getString(result.name().toLowerCase(Locale.ROOT), result.name());
            final String message = JoinFailNotifier.config.getString("kick_reasons", "&c[警告] &e{player} &cがサーバーに参加できませんでした: {reason}")
                    .replace("{player}", name)
                    .replace("{reason}", reason);
            Bukkit.getOnlinePlayers().stream().filter(ServerOperator::isOp).forEach(player -> player.sendMessage(message));
        }
    }
}
