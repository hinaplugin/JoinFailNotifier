package com.koirdsuzu.joinfailnotifier;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;

public class Commands extends Config implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, String[] args) {

        if (args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getHelp()));
            return true;
        }

        switch (args[0]){
            case "help" -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getHelp()));
            case "reload" -> {
                JoinFailNotifier.plugin.reload();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getReload()));
            }
            default -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', this.getInvalid()));
        }
        return true;
    }
}
