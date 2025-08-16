package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.KnCore;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.PlayerDataManager;
import pl.core.knCore.utils.MessageUtil;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.vanish")) {
            return true;
        }

        Player target;

        if (args.length == 0) {
            if (!MessageUtil.isPlayer(sender)) {
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendMessage(sender, "player-not-found", "player", args[0]);
                return true;
            }
        }

        PlayerDataManager.PlayerData data = KnCore.getInstance()
                .getPlayerDataManager().getPlayerData(target);

        boolean newVanishState = !data.isVanishMode();
        data.setVanishMode(newVanishState);

        setVanishMode(target, newVanishState);

        String messageKey = newVanishState ? "vanish.enabled" : "vanish.disabled";

        if (target.equals(sender)) {
            MessageUtil.sendMessage(sender, messageKey);
        } else {
            MessageUtil.sendMessage(sender, messageKey);
            MessageUtil.sendMessage(target, messageKey);
        }

        return true;
    }

    private void setVanishMode(Player player, boolean vanish) {
        if (vanish) {
            // Ukryj gracza przed wszystkimi (oprócz adminów z uprawnieniem)
            for (Player online : player.getServer().getOnlinePlayers()) {
                if (!online.hasPermission("core.vanish")) {
                    online.hidePlayer(KnCore.getInstance(), player);
                }
            }
        } else {
            // Pokaż gracza wszystkim
            for (Player online : player.getServer().getOnlinePlayers()) {
                online.showPlayer(KnCore.getInstance(), player);
            }
        }
    }
}