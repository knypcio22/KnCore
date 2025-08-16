package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.ping")) {
            return true;
        }

        Player target;

        if (args.length == 0) {
            if (!MessageUtil.isPlayer(sender)) {
                sender.sendMessage("§cKonsola nie ma opóźnienia!");
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

        int ping = target.getPing();

        if (target.equals(sender)) {
            MessageUtil.sendMessage(sender, "ping.self", "ping", String.valueOf(ping));
        } else {
            MessageUtil.sendMessage(sender, "ping.other", "player", target.getName());
            MessageUtil.sendMessage(sender, "ping.other", "ping", String.valueOf(ping));
        }

        return true;
    }
}