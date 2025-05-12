package com.example.foliaplaceholders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new FoliaExpansion(this).register();
            getLogger().info("foliaplaceholders enabled!");
        } else {
            getLogger().severe("PlaceholderAPI not found! Disabling foliaplaceholders.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }
}
