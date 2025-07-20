package com.koirdsuzu.joinfailnotifier;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

import static com.koirdsuzu.joinfailnotifier.JoinFailNotifier.config;
import static com.koirdsuzu.joinfailnotifier.JoinFailNotifier.plugin;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, String[] args) {

        if (args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.help_message", "&eUsage: /joinfailnotifier <reload/help>")));
            return true;
        }

        switch (args[0]){
            case "help" -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.help_message", "&eUsage: /joinfailnotifier <reload/help>")));
            case "reload" -> {
                plugin.reload();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.reload_success", "&a設定をリロードしました。")));
            }
            default -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.invalid_command", "&c無効なコマンドです。/joinfailnotifier help を使用してください。")));
        }
        return true;
    }
}
