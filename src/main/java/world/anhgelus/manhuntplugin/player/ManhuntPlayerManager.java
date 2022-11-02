package world.anhgelus.manhuntplugin.player;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ManhuntPlayerManager {
    private static List<ManhuntPlayer> PLAYERS = new ArrayList<>();

    public static List<ManhuntPlayer> getPLAYERS() {
        return PLAYERS;
    }

    public static void registerPlayer(ManhuntPlayer player) {
        PLAYERS.add(player);
    }

    public static void registerPlayer(Player player) {
        PLAYERS.add(new ManhuntPlayer(player));
    }

    public static void unregisterPlayer(ManhuntPlayer player) {
        PLAYERS.remove(player);
    }

    public static void unregisterPlayer(Player player) {
        PLAYERS.remove(new ManhuntPlayer(player));
    }

    public static boolean isRegistered (ManhuntPlayer player) {
        return PLAYERS.contains(player);
    }

    public static boolean isRegistered (Player player) {
        return PLAYERS.contains(new ManhuntPlayer(player));
    }

    public static ManhuntPlayer bestKiller() {
        if (PLAYERS.isEmpty()) {
            throw new NoSuchElementException("No players registered!");
        }
        ManhuntPlayer bestKiller = null;
        for (ManhuntPlayer player: PLAYERS) {
            if (bestKiller == null || player.getKill() > bestKiller.getKill()) {
                bestKiller = player;
            }
        }
        return bestKiller;
    }

    public static ManhuntPlayer bestDeath() {
        if (PLAYERS.isEmpty()) {
            throw new NoSuchElementException("No players registered!");
        }
        ManhuntPlayer bestDeath = null;
        for (ManhuntPlayer player: PLAYERS) {
            if (bestDeath == null || player.getDeath() > bestDeath.getDeath()) {
                bestDeath = player;
            }
        }
        return bestDeath;
    }

    private static void add(ManhuntPlayer player) {
        if (!PLAYERS.contains(player)) {
            PLAYERS.add(player);
        }
    }
}
