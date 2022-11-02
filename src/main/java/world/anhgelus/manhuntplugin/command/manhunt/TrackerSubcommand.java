package world.anhgelus.manhuntplugin.command.manhunt;

import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.commands.Permission;
import world.anhgelus.gamelibrary.commands.Subcommand;
import world.anhgelus.manhuntplugin.utils.MaterialHelper;

import java.util.List;

public class TrackerSubcommand extends Subcommand {
    public TrackerSubcommand() {
        super("tracker", "Manage the tracker", new Permission("manhunt.compass"));
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        CompassSubcommand.compassSub(player, args);
        return true;
    }

    @Override
    public List<String> getTabCompleter(Player player, String[] args) {
        return MaterialHelper.generatePossibilitiesForTab();
    }
}
