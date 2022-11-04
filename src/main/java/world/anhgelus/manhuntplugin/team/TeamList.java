package world.anhgelus.manhuntplugin.team;

import org.bukkit.ChatColor;
import world.anhgelus.gamelibrary.team.Team;

import java.util.UUID;

public enum TeamList {
    RUNNER("Runner", UUID.fromString("c6589b83-0dfb-4d21-bd40-34847d7ba301"), ChatColor.GREEN),
    HUNTER("Hunter", UUID.fromString("027019ec-1fad-4e02-ae25-e8b62f970f2a"), ChatColor.RED);

    public final String name;
    public final ChatColor color;
    public final UUID uuid;
    public Team team;

    TeamList(String name, UUID uuid, ChatColor color) {
        this.name = name;
        this.color = color;
        this.uuid = uuid;
    }
}
