package world.anhgelus.manhuntplugin.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import world.anhgelus.gamelibrary.team.Team;
import world.anhgelus.gamelibrary.util.SenderHelper;
import world.anhgelus.gamelibrary.util.config.Config;
import world.anhgelus.gamelibrary.util.config.ConfigAPI;
import world.anhgelus.manhuntplugin.ManhuntPlugin;
import world.anhgelus.manhuntplugin.player.ManhuntPlayer;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;
import world.anhgelus.manhuntplugin.team.TeamList;
import world.anhgelus.manhuntplugin.utils.MaterialHelper;

import java.util.List;
import java.util.Objects;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ManhuntPlayerManager.registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        final ManhuntPlayer dead = ManhuntPlayerManager.getPlayer(e.getEntity().getPlayer());
        dead.addOneDeath();
        final Player killer = e.getEntity().getKiller();
        ManhuntPlayerManager.getPlayer(killer).addOneKill();

        // if the player is a hunter, return
        if (!Objects.equals(dead.getTeam().getName(), TeamList.RUNNER.name)) {
            return;
        }

        dead.setTeam(null);
        final Player deadPlayer = dead.player;
        SenderHelper.sendInfo(deadPlayer, "You are now a spectator!");
        deadPlayer.setGameMode(GameMode.SPECTATOR);

        final Team runnerTeam = TeamList.RUNNER.team;
        runnerTeam.removePlayer(deadPlayer);

        if (runnerTeam.getPlayers().isEmpty()) {
            ManhuntPlugin.getGame().stop(killer);
        }
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR && e.getPlayer().getInventory().getItemInMainHand().getType() != ManhuntPlugin.getCompass()) {
            return;
        }

        final ConfigAPI configAPI = ManhuntPlugin.getConfigAPI();
        final Config config = configAPI.getConfig("config");
        final ItemStack nextItem = new ItemStack(MaterialHelper.getFromConfig(config, "gui.next-item"));

        final List<Player> players = TeamList.RUNNER.team.getPlayers();
        if (players.isEmpty()) {
            return;
        }
        final Inventory gui = GUIListener.generateGUI();
        if (gui == null) {
            return;
        }
        e.getPlayer().openInventory(gui);
    }
}
