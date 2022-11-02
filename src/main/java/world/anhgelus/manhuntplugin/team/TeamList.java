package world.anhgelus.manhuntplugin.team;

import org.bukkit.ChatColor;
import world.anhgelus.gamelibrary.team.Team;

public enum TeamList {
    RUNNER("Runner", ChatColor.GREEN),
    HUNTER("Hunter", ChatColor.RED);

    public final String name;
    public final ChatColor color;
    public Team team;

    TeamList(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }
}
