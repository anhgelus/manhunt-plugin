package world.anhgelus.manhuntplugin.command.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.commands.Permission;
import world.anhgelus.gamelibrary.commands.Subcommand;
import world.anhgelus.gamelibrary.team.Team;
import world.anhgelus.gamelibrary.team.TeamManager;
import world.anhgelus.gamelibrary.util.SenderHelper;
import world.anhgelus.manhuntplugin.team.TeamList;
import world.anhgelus.manhuntplugin.utils.TeamHelper;

import javax.annotation.Nullable;
import java.util.List;

public class TeamSubcommand extends Subcommand {
    public TeamSubcommand() {
        super("team", "Manage team", new Permission("manhunt.team"));
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        if (args.length == 1) {
            if (TeamManager.hasTeam(player)) {
                SenderHelper.sendInfo(player, "You are in the " + TeamManager.getTeam(player) + " team");
            } else {
                SenderHelper.sendInfo(player, "You are not in a team");
            }
        }
        if (args.length == 2) {
            final String sub = args[1];
            switch (sub) {
                case "add" -> addSub(player, args);
                case "remove" -> removeSub(player, args);
                case "list" -> listSub(player);
                default -> SenderHelper.sendWarning(player, "Unknown subcommand: " + sub);
            }
        }
        return true;
    }

    @Override
    public List<String> getTabCompleter(Player player, String[] args) {
        if (args.length == 4) { // /manhunt team add <team> <player>
            final String sub = args[1];
            final List<String> players = Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            switch (sub) {
                case "add":
                case "remove":
                    return players;
                case "list":
                    return null;
            }
        } else if (args.length == 5 && !args[1].equals("list")) {
            final TeamList[] teams = TeamList.values();
            final String[] teamNames = new String[teams.length];
            for (int i = 0; i < teams.length; i++) {
                teamNames[i] = teams[i].name();
            }
            return List.of(teamNames);
        }
        return null;
    }

    /**
     * Send the list of all teams to the player
     * @param player The player
     */
    private void listSub(Player player) {
        SenderHelper.sendInfo(player, "Teams:");
        for (TeamList team : TeamList.values()) {
            SenderHelper.sendInfo(player, team.name + " (" + team.color + team.team.getPlayers().size() + " players)");
        }
    }

    private void addSub(Player player, String[] args) {
        if (args.length != 4) {
            SenderHelper.sendError(player, "Usage: /manhunt team add <player> <team>");
        }

        final Player target = Bukkit.getPlayer(args[2]);
        final Team team = teamAddOrRemove(player, target, args);
        if (team == null) {
            return;
        }
        assert target != null;

        TeamHelper.addToTeam(target, team);

        SenderHelper.sendInfo(player, "Player " + target.getName() + " added to the " + team.getName() + " team");
        SenderHelper.sendInfo(target, "You have been added to the " + team.getName() + " team");
    }

    private void removeSub(Player player, String[] args) {
        if (args.length != 4) {
            SenderHelper.sendError(player, "Usage: /manhunt team remove <player> <team>");
        }

        final Player target = Bukkit.getPlayer(args[2]);
        final Team team = teamAddOrRemove(player, target, args);
        if (team == null) {
            return;
        }
        assert target != null;

        // check if a player is in the team
        if (!TeamManager.hasTeam(target)) {
            SenderHelper.sendError(player, "Player is not in a team");
        }

        TeamHelper.removeFromTeam(target);

        SenderHelper.sendInfo(player, "Player " + target.getName() + " removed from the " + team.getName() + " team");
        SenderHelper.sendInfo(target, "You have been removed from the " + team.getName() + " team");
    }

    /**
     * Check if the player can add or remove a player from a team
     * @param player The player
     * @param target The target
     * @param args The arguments of the command
     * @return True if the player can add or remove a player from a team
     */
    @Nullable
    private Team teamAddOrRemove(Player player, Player target, String[] args) {
        // check if the player exist
        if (target == null) {
            SenderHelper.sendError(player, "Player not found");
            return null;
        }
        // check if the team exist
        final Team team = TeamManager.getTeam(args[3]);
        if (team == null) {
            SenderHelper.sendError(player, "Team not found");
            return null;
        }
        return team;
    }
}
