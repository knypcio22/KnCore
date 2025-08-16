package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.PlayerDataManager;
import pl.core.knCore.utils.MessageUtil;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.fly")) {
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

        boolean newFlyState = !data.isFlyMode();
        data.setFlyMode(newFlyState);

        target.setAllowFlight(newFlyState);
        target.setFlying(newFlyState);

        String messageKey = newFlyState ? "fly.enabled" : "fly.disabled";

        if (target.equals(sender)) {
            MessageUtil.sendMessage(sender, messageKey);
        } else {
            MessageUtil.sendMessage(sender, messageKey);
            MessageUtil.sendMessage(target, messageKey);
        }

        return true;
    }
}