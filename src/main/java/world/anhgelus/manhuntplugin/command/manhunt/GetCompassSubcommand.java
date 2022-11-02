package world.anhgelus.manhuntplugin.command.manhunt;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import world.anhgelus.gamelibrary.commands.Permission;
import world.anhgelus.gamelibrary.commands.Subcommand;
import world.anhgelus.manhuntplugin.ManhuntPlugin;

import java.util.List;

public class GetCompassSubcommand extends Subcommand {
    public GetCompassSubcommand() {
        super("getcompass", "Get the compass", new Permission(""));
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        player.getInventory().addItem(new ItemStack(ManhuntPlugin.getCompass()));
        return true;
    }

    @Override
    public List<String> getTabCompleter(Player player, String[] args) {
        return null;
    }
}
