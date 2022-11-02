package world.anhgelus.manhuntplugin.conditions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import world.anhgelus.gamelibrary.game.Game;
import world.anhgelus.gamelibrary.game.engine.conditions.StartConditions;
import world.anhgelus.gamelibrary.util.SenderHelper;
import world.anhgelus.gamelibrary.util.config.Config;
import world.anhgelus.manhuntplugin.ManhuntPlugin;
import world.anhgelus.manhuntplugin.player.ManhuntPlayer;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;
import world.anhgelus.manhuntplugin.team.TeamList;

import java.util.List;

public class SConditions implements StartConditions {
    @Override
    public boolean onStart(Game game) {
        // Check if a team is empty
        for (TeamList teamList : TeamList.values()) {
            if (teamList.team.getPlayers().size() == 0) {
                SenderHelper.broadcastError("The team " + teamList.name + " has no players!");
                return false;
            }
        }

        final int time = getTimeBeforeStartingTheHunt();
        final int timeUpdate = getUpdateCompassTime();
        final List<Player> players = TeamList.HUNTER.team.getPlayers();
        players.forEach(player -> {
            SenderHelper.sendInfo(player, "The hunt will start in " + time + " seconds! Good luck!");
            player.setWalkSpeed(0f);
        });

        Bukkit.getScheduler().runTaskLaterAsynchronously(ManhuntPlugin.getInstance(), () -> {
            SenderHelper.broadcastInfo("The hunt has started!");
            players.forEach(player -> player.setWalkSpeed(1f));
        }, 20L * time);

        // update the compass location each x seconds
        Bukkit.getScheduler().runTaskTimerAsynchronously(ManhuntPlugin.getInstance(), () -> {
            players.forEach(player -> {
                final ManhuntPlayer manhuntPlayer = ManhuntPlayerManager.getPlayer(player);
                player.setCompassTarget(manhuntPlayer.getCompassTarget().player.getLocation());
                updateCompassTarget(manhuntPlayer, manhuntPlayer.getCompassTarget());
            });
        }, 20L * timeUpdate, 20L * timeUpdate);

        return true;
    }

    /**
     * Get the time before starting the hunt
     * @return The time before starting the hunt
     */
    public static int getTimeBeforeStartingTheHunt() {
        final Config config = ManhuntPlugin.getConfigAPI().getConfig("config");
        return config.get().getInt("time-before-hunters-start", 60);
    }

    /**
     * Get the time between each update
     * @return The time between each update
     */
    public static int getUpdateCompassTime() {
        final Config config = ManhuntPlugin.getConfigAPI().getConfig("config");
        return config.get().getInt("time-to-update-compass", 60);
    }

    /**
     * Update Compass Target
     * @param player Player to update
     * @param target Target
     */
    public static void updateCompassTarget(ManhuntPlayer player, ManhuntPlayer target) {
        player.setCompassTarget(target);
        player.player.setCompassTarget(target.player.getLocation());
    }
}
