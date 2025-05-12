package com.example.foliaplaceholders;

import com.tcoded.folialib.api.FoliaLib;
import com.tcoded.folialib.api.region.Region;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

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
        return "YourName";
    }

    @Override
    public String getIdentifier() {
        return "folia";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        RegionStats stats = getRegionStatsForPlayer(player);
        if (stats == null) return "";

        switch (identifier) {
            case "region_tps":
                return colorizeTps(stats.tps);
            case "region_mspt":
                return colorizeMspt(stats.mspt);
            default:
                return null;
        }
    }

    private RegionStats getRegionStatsForPlayer(Player player) {
        FoliaLib foliaLib = FoliaLib.getInstance();
        Region region = foliaLib.getRegionManager().getRegionAt(player.getLocation());
        if (region == null) return null;

        double tps = region.getTps5s();
        double mspt = region.getMspt5s();

        return new RegionStats(tps, mspt);
    }

    private String colorizeTps(double tps) {
        int rounded = (int) Math.round(tps);
        String color = rounded >= 18 ? "§a" : (rounded >= 15 ? "§e" : "§c");
        return color + rounded;
    }

    private String colorizeMspt(double mspt) {
        String color = mspt <= 50 ? "§a" : (mspt <= 100 ? "§e" : "§c");
        return color + df.format(mspt);
    }

    private static class RegionStats {
        final double tps;
        final double mspt;

        public RegionStats(double tps, double mspt) {
            this.tps = tps;
            this.mspt = mspt;
        }
    }
}
