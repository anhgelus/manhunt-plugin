package world.anhgelus.manhuntplugin.command.manhunt;

import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.commands.GeneralCommand;
import world.anhgelus.gamelibrary.commands.Subcommand;
import world.anhgelus.gamelibrary.util.SenderHelper;

import java.util.List;

public class ManhuntCommand extends GeneralCommand {
    public ManhuntCommand(List<Subcommand> subcommands) {
        super(subcommands);
    }

    @Override
    protected boolean command(Player player, String[] args) {
        if (!onSubcommand(player, args)) {
            SenderHelper.sendWarning(player, "Unknown subcommand: " + args[0]);
        }
        return true;
    }
}
