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
        return compassSubTab(player, args);
    }

    /**
     * Execute the compass subcommand (/manhunt compass)
     * @param player The player who executed the command
     * @param args The arguments of the command
     */
    public static void compassSub(Player player, String[] args) {
        if (args.length < 1) {
            return;
        }
        if (args.length == 1) {
            SenderHelper.sendInfo(player, "/manhunt compass|tracker set|get|track");
            return;
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

    /**
     * Get the tab completer for the compass subcommand (/manhunt compass)
     * @param player The player who executed the command
     * @param args The arguments of the command
     * @return The list of possible completions
     */
    public static List<String> compassSubTab(Player player, String[] args) { // manhunt compass set|get|track <other>
        if (args.length == 2) {
            return List.of("set", "get", "track");
        }
        if (args.length != 3) {
            return null;
        }
        return switch (args[1]) {
            case "set" -> MaterialHelper.generatePossibilitiesForTab();
            case "track" -> Bukkit.getOnlinePlayers().stream().map(Player::getName).toList();
            default -> null;
        };
    }
}
