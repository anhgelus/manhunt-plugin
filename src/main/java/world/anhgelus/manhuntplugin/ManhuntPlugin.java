package world.anhgelus.manhuntplugin;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import world.anhgelus.gamelibrary.game.Game;
import world.anhgelus.gamelibrary.game.commands.GameCommandManager;
import world.anhgelus.gamelibrary.game.engine.GameEngine;
import world.anhgelus.manhuntplugin.conditions.GConditions;
import world.anhgelus.manhuntplugin.conditions.SConditions;
import world.anhgelus.manhuntplugin.conditions.WConditions;

public final class ManhuntPlugin extends JavaPlugin {
    private static ManhuntPlugin INSTANCE;
    private static Game GAME;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        // Initialize the game and the game engine
        GAME = new Game(this, "Manhunt");
        final GameEngine engine = GAME.getEngine();
        engine.setStartConditions(new SConditions());
        engine.setGeneralConditions(new GConditions());
        engine.setWinConditions(new WConditions());

        final GameCommandManager manager = GAME.getCommandManager();

        final PluginCommand gameCommand = getCommand("game");
        if (gameCommand != null) {
            gameCommand.setExecutor(manager.registerGameCommands());
            gameCommand.setTabCompleter(manager.registerGameTabCompleter());
        }

        getLogger().info("ManhuntPlugin has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("ManhuntPlugin has been disabled!");
    }

    public static ManhuntPlugin getInstance() {
        return INSTANCE;
    }

    public static Game getGame() {
        return GAME;
    }
}
