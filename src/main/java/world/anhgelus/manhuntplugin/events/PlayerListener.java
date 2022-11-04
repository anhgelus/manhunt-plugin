package world.anhgelus.manhuntplugin.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import world.anhgelus.gamelibrary.util.SenderHelper;
import world.anhgelus.gamelibrary.util.config.Config;
import world.anhgelus.gamelibrary.util.config.ConfigAPI;
import world.anhgelus.manhuntplugin.ManhuntPlugin;
import world.anhgelus.manhuntplugin.conditions.SConditions;
import world.anhgelus.manhuntplugin.player.ManhuntPlayer;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;
import world.anhgelus.manhuntplugin.team.TeamList;

import java.util.List;
import java.util.Objects;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ManhuntPlayerManager.registerPlayer(e.getPlayer());
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof final Player player)) {
            return;
        }

        if (player.getHealth() - e.getFinalDamage() > 0) {
            return;
        }
        e.setCancelled(true);
        player.setInvulnerable(true);
        final ManhuntPlayer dead = ManhuntPlayerManager.getPlayer(player);
        dead.addOneDeath();

        if (dead.getTeam() == null) {
            ManhuntPlugin.getInstance().getLogger().warning("The player " + player.getName() + " has no team!");
            player.setInvulnerable(false);
            return;
        }

        // if the player is a hunter, return
        if (!Objects.equals(dead.getTeam().getName(), TeamList.RUNNER.name)) {
            player.setHealth(0);
            return;
        }

        dead.setTeam(null);
        final Player deadPlayer = dead.player;
        SenderHelper.sendInfo(deadPlayer, "You are now a spectator!");
        deadPlayer.setGameMode(GameMode.SPECTATOR);

        TeamList.RUNNER.team.removePlayer(deadPlayer);

        if (!(e.getDamager() instanceof final Player killer)) {
            SenderHelper.broadcastWarning(deadPlayer.getName() + " has died!");
            return;
        }
        final ManhuntPlayer hunter = ManhuntPlayerManager.getPlayer(killer);
        hunter.addOneKill();

        SenderHelper.broadcastWarning(deadPlayer.getName() + " has died! He was killed by " + hunter.player.getName());

        if (TeamList.RUNNER.team.getPlayers().isEmpty()) {
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

        final List<Player> players = TeamList.RUNNER.team.getPlayers();
        if (players.isEmpty()) {
            return;
        }
        if (players.size() == 1) {
            SConditions.updateCompassTarget(ManhuntPlayerManager.getPlayer(e.getPlayer()), ManhuntPlayerManager.getPlayer(players.get(0)));
            return;
        }
        final Inventory gui = GUIListener.generateGUI();
        if (gui == null) {
            return;
        }
        e.getPlayer().openInventory(gui);
    }
}
