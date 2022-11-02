package world.anhgelus.manhuntplugin.command.manhunt;

import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.commands.GeneralTabCompleter;
import world.anhgelus.gamelibrary.commands.Subcommand;

import java.util.List;

public class ManhuntTabCompleter extends GeneralTabCompleter {
    public ManhuntTabCompleter(List<Subcommand> subcommands) {
        super(subcommands);
    }

    @Override
    protected List<String> complete(Player player, Subcommand subcommand, String[] args) {
        return onSubcommand(player, subcommand, args);
    }
}
