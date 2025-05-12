package com.example.foliaplaceholders;

import dev.folia.api.FoliaAPI;
import dev.folia.api.region.Region;
import com.example.foliaplaceholders.lib.folialib.api.FoliaLib;
import com.example.foliaplaceholders.lib.folialib.api.region.Region;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

/**
 * PlaceholderAPI expansion для отображения TPS и MSPT региона Folia,
 * в котором находится игрок.
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
        return "folia"; // Используйте плейсхолдеры с префиксом %folia_...
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * Обработка плейсхолдеров:
     * - %folia_region_tps%  - TPS региона (округлённый)
     * - %folia_region_mspt% - MSPT региона (с 2 знаками после запятой)
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

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

    /**
     * Получение статистики региона Folia по локации игрока.
     * Возвращает null, если регион не найден или API недоступно.
     */
    private RegionStats getRegionStatsForPlayer(Player player) {
        Region region = FoliaAPI.getRegionAt(player.getLocation());
        if (region == null) return null;

        // Получаем усреднённые значения за 5 секунд
        double tps5s = region.getTps5s();
        double mspt5s = region.getMspt5s();

        return new RegionStats(tps5s, mspt5s);
    }

    /**
     * Окрашивание TPS:
     * - >=18 зелёный
     * - >=15 жёлтый
     * - иначе красный
     */
    private String colorizeTps(double tps) {
        int rounded = (int) Math.round(tps);
        String color = rounded >= 18 ? "§a" : (rounded >= 15 ? "§e" : "§c");
        return color + rounded;
    }

    /**
     * Окрашивание MSPT:
     * - <=50 зелёный
     * - <=100 жёлтый
     * - иначе красный
     */
    private String colorizeMspt(double mspt) {
        String color = mspt <= 50 ? "§a" : (mspt <= 100 ? "§e" : "§c");
        return color + df.format(mspt);
    }

    /**
     * Внутренний класс для хранения статистики региона.
     */
    private static class RegionStats {
        final double tps5s;
        final double mspt5s;

        public RegionStats(double tps5s, double mspt5s) {
            this.tps5s = tps5s;
            this.mspt5s = mspt5s;
        }
    }
}
