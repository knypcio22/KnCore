package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.PlayerDataManager;
import pl.core.knCore.utils.MessageUtil;

public class GodCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.god")) {
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

        boolean newGodState = !data.isGodMode();
        data.setGodMode(newGodState);

        target.setInvulnerable(newGodState);

        String messageKey = newGodState ? "god.enabled" : "god.disabled";

        if (target.equals(sender)) {
            MessageUtil.sendMessage(sender, messageKey);
        } else {
            MessageUtil.sendMessage(sender, messageKey);
            MessageUtil.sendMessage(target, messageKey);
        }

        return true;
    }
}