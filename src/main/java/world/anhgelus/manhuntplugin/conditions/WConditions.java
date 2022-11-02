package world.anhgelus.manhuntplugin.conditions;

import world.anhgelus.gamelibrary.game.Game;
import world.anhgelus.gamelibrary.game.engine.conditions.WinConditions;
import world.anhgelus.gamelibrary.util.SenderHelper;
import world.anhgelus.manhuntplugin.player.ManhuntPlayer;
import world.anhgelus.manhuntplugin.player.ManhuntPlayerManager;

public class WConditions implements WinConditions {
    @Override
    public void onWin(Game game) {
        final ManhuntPlayer bestDeath = ManhuntPlayerManager.bestDeath();
        final ManhuntPlayer bestKiller = ManhuntPlayerManager.bestKiller();

        SenderHelper.broadcastInfo("The best killer is " + bestKiller.player.getName() + " with " + bestKiller.getKill() + " kills!");
        SenderHelper.broadcastInfo("The best death is " + bestDeath.player.getName() + " with " + bestDeath.getDeath() + " deaths!");
    }
}
