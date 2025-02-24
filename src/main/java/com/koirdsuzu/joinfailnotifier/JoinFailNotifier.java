package com.koirdsuzu.joinfailnotifier;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class JoinFailNotifier extends JavaPlugin implements Listener {
    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("joinfailnotifier").setExecutor(new PluginCommand(this));
    }

    @Override
    public void onDisable() {
        saveConfig();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if (event.getResult() != PlayerLoginEvent.Result.ALLOWED) {
            String playerName = event.getPlayer().getName();
            String defaultReason = event.getResult().name();
            Map<String, Object> reasonMappings = config.getConfigurationSection("kick_reasons").getValues(false);
            String reason = reasonMappings.getOrDefault(defaultReason, defaultReason).toString();

            String message = ChatColor.translateAlternateColorCodes('&', config.getString("message_format", "&c[警告] &e{player} &cがサーバーに参加できませんでした: {reason}"))
                    .replace("{player}", playerName)
                    .replace("{reason}", reason);

            notifyOps(message);
        }
    }

    private void notifyOps(String message) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.isOp())
                .forEach(player -> player.sendMessage(message));
    }
}

class PluginCommand implements CommandExecutor {
    private final JoinFailNotifier plugin;

    public PluginCommand(JoinFailNotifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = plugin.getConfig();

        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.help_message", "&eUsage: /joinfailnotifier <reload/help>")));
            return true;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.reload_success", "&a設定をリロードしました。")));
            return true;
        }

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("messages.invalid_command", "&c無効なコマンドです。/joinfailnotifier help を使用してください。")));
        return true;
    }
}
