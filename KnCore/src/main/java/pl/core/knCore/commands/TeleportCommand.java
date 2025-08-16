package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class TeleportCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.teleport")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUżycie: /tp <gracz> [cel]");
            return true;
        }

        if (args.length == 1) {
            // /tp <gracz> - teleportuj się do gracza
            if (!MessageUtil.isPlayer(sender)) {
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                MessageUtil.sendMessage(sender, "player-not-found", "player", args[0]);
                return true;
            }

            Player player = (Player) sender;
            player.teleport(target.getLocation());
            sender.sendMessage(String.format("§aTeleportowano do gracza %s!", target.getName()));

        } else {
            // /tp <gracz1> <gracz2> - teleportuj gracza1 do gracza2
            Player player1 = Bukkit.getPlayer(args[0]);
            Player player2 = Bukkit.getPlayer(args[1]);

            if (player1 == null) {
                MessageUtil.sendMessage(sender, "player-not-found", "player", args[0]);
                return true;
            }

            if (player2 == null) {
                MessageUtil.sendMessage(sender, "player-not-found", "player", args[1]);
                return true;
            }

            player1.teleport(player2.getLocation());
            sender.sendMessage(String.format("§aTeleportowano gracza %s do %s!",
                    player1.getName(), player2.getName()));
            player1.sendMessage(String.format("§aZostałeś teleportowany do gracza %s!",
                    player2.getName()));
        }

        return true;
    }
}