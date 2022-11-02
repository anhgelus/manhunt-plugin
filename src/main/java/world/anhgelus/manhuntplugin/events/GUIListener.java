package world.anhgelus.manhuntplugin.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import world.anhgelus.manhuntplugin.ManhuntPlugin;
import world.anhgelus.manhuntplugin.conditions.SConditions;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;
import world.anhgelus.manhuntplugin.team.TeamList;
import world.anhgelus.manhuntplugin.utils.MaterialHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

public class GUIListener implements Listener {
    public static final Material NEXT_ITEM = MaterialHelper.getFromConfig(ManhuntPlugin.getConfigAPI().getConfig("config"), "gui.next-item");

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        final Inventory inv = e.getClickedInventory();
        if ((inv == null || inv.getSize() != 9)) {
            return;
        }
         if (!(e.getWhoClicked() instanceof final Player player)) {
             return;
         }

        e.setCancelled(true);

        final ItemStack clickedItem = inv.getItem(e.getSlot());
        if (clickedItem == null) {
            return;
        }
        if (clickedItem.getType() == NEXT_ITEM) {
            player.closeInventory();
            final int next = Integer.parseInt(clickedItem.getItemMeta().getDisplayName());
            final Inventory nextInv = generateGUI(next);
            if (nextInv == null) {
                return;
            }
            player.openInventory(nextInv);
            return;
        }

        final String name = Objects.requireNonNull(clickedItem.getItemMeta()).getDisplayName();
        final Player target = Bukkit.getPlayer(name);
        SConditions.updateCompassTarget(ManhuntPlayerManager.getPlayer(player), ManhuntPlayerManager.getPlayer(target));
    }

    /**
     * Generate the GUI of the compass
     * @param line the line of the GUI (0 = first line)
     * @return the inventory of the GUI
     */
    @Nullable
    public static Inventory generateGUI(int line) {
        final List<Player> players = TeamList.RUNNER.team.getPlayers();
        final Inventory gui = Bukkit.createInventory(null, 9, "Compass");
        if (players.isEmpty()) {
            return null;
        }
        final int itemsPerLine = gui.getSize();
        final int start = itemsPerLine  * line;
        for (int i = start; i <= players.size() % itemsPerLine + start; i++) {
            // generate the arrow when needed (last slot)
            if (i%itemsPerLine == itemsPerLine-1 && players.size() > itemsPerLine) {
                final ItemStack next = new ItemStack(NEXT_ITEM);
                final ItemMeta meta = next.getItemMeta();
                if (meta == null) {
                    continue;
                }
                meta.setDisplayName(String.valueOf(line + 1));
                next.setItemMeta(meta);
                gui.addItem(next);
            }
            // generate the skull
            final Player player = players.get(i);
            ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
            SkullMeta meta = (SkullMeta) item.getItemMeta();
            if (!meta.hasOwner()) {
                throw new IllegalStateException("SkullMeta has no owner");
            }
            meta.setOwningPlayer(player);
            meta.setDisplayName(player.getName());
            item.setItemMeta(meta);
            // set the skull
            gui.setItem(i, item);
        }

        return gui;
    }

    /**
     * Generate the GUI of the compass (first line)
     * @return the inventory of the GUI
     */
    @Nullable
    public static Inventory generateGUI() {
        return generateGUI(0);
    }
}
