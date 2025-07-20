package com.koirdsuzu.joinfailnotifier;

import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JoinFailNotifier extends JavaPlugin {
    public static JoinFailNotifier plugin;
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        plugin = this;
        this.loadConfiguration();
        this.getServer().getPluginManager().registerEvents(new PlayerLoginListener(), this);
        final PluginCommand command = this.getCommand("joinfailnotifier");
        if (command != null){
            command.setExecutor(new Commands());
        }
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    private void loadConfiguration(){
        final File configFile = new File(this.getDataFolder(), "config.yml");
        if (!configFile.exists()){
            this.saveDefaultConfig();
        }
        config = this.getConfig();
    }

    public void reload(){
        this.reloadConfig();
        config = this.getConfig();
    }
}