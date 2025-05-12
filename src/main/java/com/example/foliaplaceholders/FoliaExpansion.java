package com.example.foliaplaceholders;

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

        return switch (identifier) {
            // TPS
            case "tps" -> formatTps(stats.tps);
            case "tps_5s" -> formatTps(stats.tps5s);
            case "tps_15s" -> formatTps(stats.tps15s);
            case "tps_1m" -> formatTps(stats.tps1m);
            case "tps_5m" -> formatTps(stats.tps5m);
            case "tps_15m" -> formatTps(stats.tps15m);

            case "tps_5s_colored" -> colorizeTps(stats.tps5s);
            case "tps_15s_colored" -> colorizeTps(stats.tps15s);
            case "tps_1m_colored" -> colorizeTps(stats.tps1m);
            case "tps_5m_colored" -> colorizeTps(stats.tps5m);
            case "tps_15m_colored" -> colorizeTps(stats.tps15m);

            // MSPT
            case "mspt" -> formatMspt(stats.mspt);
            case "mspt_5s" -> formatMspt(stats.mspt5s);
            case "mspt_15s" -> formatMspt(stats.mspt15s);
            case "mspt_1m" -> formatMspt(stats.mspt1m);
            case "mspt_5m" -> formatMspt(stats.mspt5m);
            case "mspt_15m" -> formatMspt(stats.mspt15m);

            case "mspt_5s_colored" -> colorizeMspt(stats.mspt5s);
            case "mspt_15s_colored" -> colorizeMspt(stats.mspt15s);
            case "mspt_1m_colored" -> colorizeMspt(stats.mspt1m);
            case "mspt_5m_colored" -> colorizeMspt(stats.mspt5m);
            case "mspt_15m_colored" -> colorizeMspt(stats.mspt15m);

            // Utilization
            case "util" -> formatUtil(stats.utilization);
            case "util_colored" -> colorizeUtil(stats.utilization);

            default -> null;
        };
    }

    // Заглушка: здесь нужно получить реальные данные из Folia API
    private RegionStats getRegionStatsForPlayer(Player player) {
        // TODO: Реализуйте получение региона игрока и статистики TPS/MSPT из Folia API
        // Ниже пример заглушки с фиктивными данными (13 параметров!)
        return new RegionStats(
                19.8, 19.7, 19.6, 19.5, 19.4, 19.3,  // TPS 6 значений
                45, 48, 50, 52, 55, 53,              // MSPT 6 значений
                0.35                                 // Utilization (35%)
        );
    }

    private String formatTps(double tps) {
        return df.format(tps);
    }

    private String colorizeTps(double tps) {
        String color = tps >= 18 ? "§a" : (tps >= 15 ? "§e" : "§c");
        return color + df.format(tps);
    }

    private String formatMspt(double mspt) {
        return df.format(mspt);
    }

    private String colorizeMspt(double mspt) {
        String color = mspt <= 50 ? "§a" : (mspt <= 100 ? "§e" : "§c");
        return color + df.format(mspt);
    }

    private String formatUtil(double util) {
        return df.format(util * 100) + "%";
    }

    private String colorizeUtil(double util) {
        double percent = util * 100;
        String color = percent <= 50 ? "§a" : (percent <= 75 ? "§e" : "§c");
        return color + df.format(percent) + "%";
    }

    // Внутренний класс для хранения статистики региона
    private static class RegionStats {
        final double tps, tps5s, tps15s, tps1m, tps5m, tps15m;
        final double mspt, mspt5s, mspt15s, mspt1m, mspt5m, mspt15m;
        final double utilization;

        public RegionStats(double tps, double tps5s, double tps15s, double tps1m, double tps5m, double tps15m,
                           double mspt, double mspt5s, double mspt15s, double mspt1m, double mspt5m, double mspt15m,
                           double utilization) {
            this.tps = tps;
            this.tps5s = tps5s;
            this.tps15s = tps15s;
            this.tps1m = tps1m;
            this.tps5m = tps5m;
            this.tps15m = tps15m;
            this.mspt = mspt;
            this.mspt5s = mspt5s;
            this.mspt15s = mspt15s;
            this.mspt1m = mspt1m;
            this.mspt5m = mspt5m;
            this.mspt15m = mspt15m;
            this.utilization = utilization;
        }

        // Конструктор для заглушки с одинаковыми MSPT значениями
        public RegionStats(double tps, double tps5s, double tps15s, double tps1m, double tps5m, double tps15m,
                           double mspt, double utilization) {
            this(tps, tps5s, tps15s, tps1m, tps5m, tps15m,
                    mspt, mspt, mspt, mspt, mspt, mspt,
                    utilization);
        }
    }
}
