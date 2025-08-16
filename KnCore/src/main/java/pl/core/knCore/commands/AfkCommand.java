package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.PlayerDataManager;
import pl.core.knCore.utils.MessageUtil;

public class AfkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.afk")) {
            return true;
        }

        if (!MessageUtil.isPlayer(sender)) {
            return true;
        }

        Player player = (Player) sender;
        PlayerDataManager.PlayerData data = KnCore.getInstance()
                .getPlayerDataManager().getPlayerData(player);

        boolean newAfkState = !data.isAfkMode();

        if (newAfkState) {
            // Włączanie AFK
            String reason = null;
            if (args.length > 0) {
                reason = String.join(" ", args);
                data.setAfkReason(reason);
            }

            data.setAfkMode(true);

            // Wyślij wiadomość do wszystkich
            String message;
            if (reason != null) {
                message = KnCore.getInstance().getConfigManager()
                        .getMessage("afk.enabled-reason", "player", player.getName())
                        .replace("{reason}", reason);
            } else {
                message = KnCore.getInstance().getConfigManager()
                        .getMessage("afk.enabled", "player", player.getName());
            }

            Bukkit.broadcastMessage(message);

        } else {
            // Wyłączanie AFK
            String duration = data.getAfkDuration();
            data.setAfkMode(false);

            String message = KnCore.getInstance().getConfigManager()
                    .getMessage("afk.disabled", "player", player.getName())
                    .replace("{duration}", duration != null ? duration : "nieznany czas");

            Bukkit.broadcastMessage(message);
        }

        return true;
    }
}