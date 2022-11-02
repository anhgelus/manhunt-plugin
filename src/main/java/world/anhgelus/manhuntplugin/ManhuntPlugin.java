package world.anhgelus.manhuntplugin;

import org.bukkit.Material;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import world.anhgelus.gamelibrary.commands.Subcommand;
import world.anhgelus.gamelibrary.game.Game;
import world.anhgelus.gamelibrary.game.commands.GameCommandManager;
import world.anhgelus.gamelibrary.game.engine.GameEngine;
import world.anhgelus.gamelibrary.team.TeamManager;
import world.anhgelus.gamelibrary.util.config.ConfigAPI;
import world.anhgelus.manhuntplugin.command.manhunt.GetCompassSubcommand;
import world.anhgelus.manhuntplugin.command.manhunt.ManhuntCommand;
import world.anhgelus.manhuntplugin.command.manhunt.TeamSubcommand;
import world.anhgelus.manhuntplugin.command.manhunt.TrackerSubcommand;
import world.anhgelus.manhuntplugin.conditions.GConditions;
import world.anhgelus.manhuntplugin.conditions.SConditions;
import world.anhgelus.manhuntplugin.conditions.WConditions;
import world.anhgelus.manhuntplugin.team.TeamList;

import java.util.List;

public final class ManhuntPlugin extends JavaPlugin {
    private static ManhuntPlugin INSTANCE;
    private static Game GAME;
    private static ConfigAPI CONFIG_API;
    private static Material COMPASS;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        CONFIG_API = new ConfigAPI(this);
        COMPASS = Material.valueOf(CONFIG_API.getConfig("config").get().getString("compass-item"));

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

        // Initialize the teams
        TeamList.RUNNER.team = TeamManager.createTeam(TeamList.RUNNER.name, "RUN", TeamList.RUNNER.color);
        TeamList.HUNTER.team = TeamManager.createTeam(TeamList.HUNTER.name, "HUNT", TeamList.HUNTER.color);

        // Register the commands
        final List<Subcommand> manhuntSubcommands = List.of(
                new TeamSubcommand(), new TrackerSubcommand(), new GetCompassSubcommand()
        );
        final PluginCommand manhuntCommand = getCommand("manhunt");
        if (manhuntCommand != null) {
            final ManhuntCommand manhuntCommandExecutor = new ManhuntCommand(manhuntSubcommands);
            manhuntCommand.setExecutor(manhuntCommandExecutor);
            manhuntCommand.setTabCompleter(manhuntCommandExecutor.getGenericTabCompleter());
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

    public static ConfigAPI getConfigAPI() {
        return CONFIG_API;
    }

    public static Material getCompass() {
        return COMPASS;
    }

    public static void setCompass(Material COMPASS) {
        ManhuntPlugin.COMPASS = COMPASS;
    }
}
