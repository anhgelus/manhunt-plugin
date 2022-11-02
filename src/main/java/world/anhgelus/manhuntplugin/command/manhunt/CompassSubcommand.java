package world.anhgelus.manhuntplugin.command.manhunt;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.commands.Permission;
import world.anhgelus.gamelibrary.commands.Subcommand;
import world.anhgelus.gamelibrary.util.SenderHelper;
import world.anhgelus.manhuntplugin.ManhuntPlugin;
import world.anhgelus.manhuntplugin.conditions.SConditions;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;
import world.anhgelus.manhuntplugin.utils.MaterialHelper;

import java.util.List;

public class CompassSubcommand extends Subcommand {

    public CompassSubcommand() {
        super("compass", "Manage the compass", new Permission("manhunt.compass"));
    }

    @Override
    public boolean onCommand(Player player, String[] args) {
        compassSub(player, args);
        return true;
    }

    @Override
    public List<String> getTabCompleter(Player player, String[] args) {
        return MaterialHelper.generatePossibilitiesForTab();
    }

    public static void compassSub(Player player, String[] args) {
        if (args.length == 1) {
            SenderHelper.sendInfo(player, "/manhunt compass|tracker set|get|track");
        }
        String sub = args[1];
        switch (sub) {
            case "set" -> {
                final Material type = player.getInventory().getItemInMainHand().getType();
                if (type == Material.AIR) {
                    SenderHelper.sendError(player, "You must hold an item in your hand!");
                    break;
                }
                ManhuntPlugin.setCompass(type);
            }
            case "get" -> SenderHelper.sendInfo(player, "The compass type is " + ManhuntPlugin.getCompass().name());
            case "track" -> {
                if (args.length == 2) {
                    SenderHelper.sendInfo(player, "/manhunt compass|tracker track <player>");
                    break;
                }
                final Player target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    SenderHelper.sendError(player, "The player " + args[2] + " is not online");
                    break;
                }
                SConditions.updateCompassTarget(ManhuntPlayerManager.getPlayer(player), ManhuntPlayerManager.getPlayer(target));
                SenderHelper.sendInfo(player, "You are now tracking " + target.getName());
            }
            default -> SenderHelper.sendInfo(player, "/manhunt compass|tracker set|get|track");
        }
    }
}
