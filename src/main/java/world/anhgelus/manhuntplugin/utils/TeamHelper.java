package world.anhgelus.manhuntplugin.utils;

import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.team.Team;
import world.anhgelus.gamelibrary.team.TeamManager;
import world.anhgelus.manhuntplugin.player.ManhuntPlayer;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;

import java.util.Objects;

public class TeamHelper {
    /**
     * Get the team of a player
     * @param player the player
     * @param team the team
     */
    public static void addToTeam(Player player, Team team) {
        final ManhuntPlayer manhuntPlayer = ManhuntPlayerManager.getPlayer(player);

        if (TeamManager.hasTeam(player)) {
            Objects.requireNonNull(TeamManager.getTeam(player)).removePlayer(player);
        }

        team.addPlayer(player);
        manhuntPlayer.setTeam(team);
    }

    /**
     * Remove the team of a player
     * @param player the player
     */
    public static void removeFromTeam(Player player) {
        final ManhuntPlayer manhuntPlayer = ManhuntPlayerManager.getPlayer(player);

        if (!TeamManager.hasTeam(player)) {
            throw new IllegalStateException("The player is not in the team");
        }

        Objects.requireNonNull(TeamManager.getTeam(player)).removePlayer(player);
        manhuntPlayer.setTeam(null);
    }
}
