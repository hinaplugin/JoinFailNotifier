package com.koirdsuzu.joinfailnotifier;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    private FileConfiguration config;

    public String getHelp(){
        this.configLoad();
        return config.getString("messages.help_message", "&eUsage: /joinfailnotifier <reload/help>");
    }

    public String getReload(){
        this.configLoad();
        return config.getString("messages.reload_success", "&a設定をリロードしました。");
    }

    public String getInvalid(){
        this.configLoad();
        return config.getString("messages.invalid_command", "&c無効なコマンドです。/joinfailnotifier help を使用してください。");
    }

    public String getReason(){
        this.configLoad();
        return config.getString("kick_reasons", "&c[警告] &e{player} &cがサーバーに参加できませんでした: {reason}");
    }

    private void configLoad(){
        config = JoinFailNotifier.config;
    }
}
