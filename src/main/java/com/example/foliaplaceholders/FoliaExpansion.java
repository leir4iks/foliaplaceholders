package com.example.foliaregionexpansion;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;

/**
 * Плагин расширения PlaceholderAPI для Folia region TPS и MSPT.
 */
public class FoliaRegionExpansion extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private final DecimalFormat df = new DecimalFormat("0.00");

    public FoliaRegionExpansion(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "YourName"; // Ваш ник
    }

    @Override
    public String getIdentifier() {
        return "foliaregion"; // Идентификатор плейсхолдеров: %foliaregion_region_tps%
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * Обработка запроса плейсхолдера.
     * Поддерживаются:
     * - region_tps
     * - region_mspt
     * 
     * @param player игрок (может быть null)
     * @param identifier идентификатор плейсхолдера без префикса
     * @return строка с заменой или пустая строка
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        // Получаем статистику региона игрока (если player == null, возвращаем null или глобальные данные)
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
     * Метод получения статистики региона для игрока.
     * Здесь нужно интегрировать вызовы Folia API для реальных данных.
     * 
     * @param player игрок, для которого нужно получить региональные данные
     * @return RegionStats с данными или null если данных нет
     */
    private RegionStats getRegionStatsForPlayer(Player player) {
        if (player == null) {
            // Можно вернуть глобальные данные или null
            return getDummyStats();
        }

        // TODO: Реализуйте получение региона игрока и статистики TPS/MSPT из Folia API
        // Пример псевдокода:
        // Region region = FoliaAPI.getRegionAt(player.getLocation());
        // if (region == null) return null;
        //
        // double tps5s = region.getTps5s();
        // double mspt5s = region.getMspt5s();
        //
        // return new RegionStats(tps5s, mspt5s);

        // Пока возвращаем заглушку
        return getDummyStats();
    }

    private RegionStats getDummyStats() {
        // Заглушка: TPS 19.8, MSPT 45.0
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
