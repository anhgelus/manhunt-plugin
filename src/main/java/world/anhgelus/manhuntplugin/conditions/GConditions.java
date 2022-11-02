package world.anhgelus.manhuntplugin.conditions;

import org.bukkit.Bukkit;
import world.anhgelus.gamelibrary.game.Game;
import world.anhgelus.gamelibrary.game.engine.conditions.GeneralConditions;

public class GConditions implements GeneralConditions {
    @Override
    public void onPause(Game game) {
        Bukkit.getOnlinePlayers().forEach(player -> player.setWalkSpeed(0f));
    }

    @Override
    public void onResume(Game game) {
        Bukkit.getOnlinePlayers().forEach(player -> player.setWalkSpeed(0f));
    }
}
