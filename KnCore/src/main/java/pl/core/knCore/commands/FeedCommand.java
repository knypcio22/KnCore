package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class FeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.feed")) {
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

        target.setFoodLevel(20);
        target.setSaturation(20.0f);
        target.setExhaustion(0.0f);

        if (target.equals(sender)) {
            MessageUtil.sendMessage(sender, "feed.success");
        } else {
            MessageUtil.sendMessage(sender, "feed.other", "player", target.getName());
            MessageUtil.sendMessage(target, "feed.success");
        }

        return true;
    }
}