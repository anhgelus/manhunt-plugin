package world.anhgelus.manhuntplugin.utils;

import org.bukkit.Material;
import world.anhgelus.gamelibrary.util.config.Config;

import java.util.List;

public class MaterialHelper {
    public static List<String> generatePossibilitiesForTab() {
        final Material[] values = Material.values();
        final String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].name();
        }
        return List.of(names);
    }

    public static Material getFromConfig(Config config, String path) {
        return getFromConfig(config, path, "ARROW");
    }

    public static Material getFromConfig(Config config, String path, String defaultValue) {
        return Material.valueOf(config.get().getString(path, defaultValue));
    }
}
