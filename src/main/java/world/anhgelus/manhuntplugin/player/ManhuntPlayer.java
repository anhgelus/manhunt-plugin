package world.anhgelus.manhuntplugin.player;

import org.bukkit.entity.Player;
import world.anhgelus.gamelibrary.team.Team;

public class ManhuntPlayer {
    public final Player player;
    private boolean isRunner = false;
    private Team team;
    private int death;
    private int kill;

    private ManhuntPlayer compassTarget;

    public ManhuntPlayer(Player player) {
        this.player = player;
    }

    public boolean isRunner() {
        return isRunner;
    }

    public void setRunner(boolean runner) {
        isRunner = runner;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public void addOneDeath() {
        death++;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public void addOneKill() {
        kill++;
    }

    public ManhuntPlayer getCompassTarget() {
        return compassTarget;
    }

    public void setCompassTarget(ManhuntPlayer compassTarget) {
        this.compassTarget = compassTarget;
    }
}
