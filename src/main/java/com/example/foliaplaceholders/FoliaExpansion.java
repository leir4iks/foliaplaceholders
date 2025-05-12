package com.example.foliaplaceholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

/**
 * PlaceholderAPI expansion для Folia region TPS и MSPT.
 */
public class FoliaExpansion extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final DecimalFormat df = new DecimalFormat("0.00");

    public FoliaExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "YourName"; // Замените на ваше имя
    }

    @Override
    public String getIdentifier() {
        return "folia"; // Идентификатор плейсхолдеров: %folia_region_tps%
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        RegionStats stats = getRegionStatsForPlayer(player);
        if (stats == null) return "";

        switch (identifier) {
            case "region_tps":
                return colorizeTps(stats.tps5s);
            case "region_mspt":
                return colorizeMspt(stats.mspt5s);
            default:
                return null;
        }
    }

    private RegionStats getRegionStatsForPlayer(Player player) {
        if (player == null) {
            return getDummyStats();
        }

        // TODO: Реализуйте получение реальных данных из Folia API
        // Пример:
        // Region region = FoliaAPI.getRegionAt(player.getLocation());
        // if (region == null) return null;
        // double tps5s = region.getTps5s();
        // double mspt5s = region.getMspt5s();
        // return new RegionStats(tps5s, mspt5s);

        return getDummyStats();
    }

    private RegionStats getDummyStats() {
        return new RegionStats(19.8, 45.0);
    }

    private String colorizeTps(double tps) {
        String color = tps >= 18 ? "§a" : (tps >= 15 ? "§e" : "§c");
        return color + df.format(tps);
    }

    private String colorizeMspt(double mspt) {
        String color = mspt <= 50 ? "§a" : (mspt <= 100 ? "§e" : "§c");
        return color + df.format(mspt);
    }

    private static class RegionStats {
        final double tps5s;
        final double mspt5s;

        public RegionStats(double tps5s, double mspt5s) {
            this.tps5s = tps5s;
            this.mspt5s = mspt5s;
        }
    }
}
