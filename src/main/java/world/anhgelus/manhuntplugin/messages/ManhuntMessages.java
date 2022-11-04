package world.anhgelus.manhuntplugin.messages;

import org.bukkit.configuration.file.FileConfiguration;
import world.anhgelus.gamelibrary.messages.Message;
import world.anhgelus.gamelibrary.util.config.Config;

public class ManhuntMessages {
    public static Message getMessage(Config messageConfig) {
        final FileConfiguration config = messageConfig.get();
        final Message message = new Message("manhunt");
        setMessage(message, config, "start");
        setMessage(message, config, "before-start");
        //events
        setMessage(message, config, "runner-died-to-runner");
        setMessage(message, config, "runner-died-to-all");
        setMessage(message, config, "runner-killed-to-all");
        // commands
        setMessage(message, config, "compass-get");
        setMessage(message, config, "tracking");
        setMessage(message, config, "team-get");
        setMessage(message, config, "no-team");
        setMessage(message, config, "player-added-to-team-to-all");
        setMessage(message, config, "player-added-to-team-to-player");
        setMessage(message, config, "player-removed-from-team-to-all");
        setMessage(message, config, "player-removed-from-team-to-player");
        // error
        setMessage(message, config, "error-team-has-no-player");
        setMessage(message, config, "error-compass-set-air");
        setMessage(message, config, "error-player-offline");
        setMessage(message, config, "error-player-not-in-team");
        setMessage(message, config, "error-team-not-found");
        setMessage(message, config, "error-team-not-exist");
        setMessage(message, config, "error-player-not-found");
        return message;
    }

    private static void setMessage(Message message, FileConfiguration config, String path) {
        message.setMessage(path, config.getString(path));
    }
}
