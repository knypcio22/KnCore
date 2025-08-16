package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class SpeedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.speed")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUżycie: /speed <1-10> [gracz]");
            return true;
        }

        int speed;
        try {
            speed = Integer.parseInt(args[0]);
            if (speed < 1 || speed > 10) {
                sender.sendMessage("§cPrędkość musi być między 1 a 10!");
                return true;
            }
        } catch (NumberFormatException e) {
            sender.sendMessage("§cPodaj prawidłową liczbę!");
            return true;
        }

        Player target;

        if (args.length == 1) {
            if (!MessageUtil.isPlayer(sender)) {
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                MessageUtil.sendMessage(sender, "player-not-found", "player", args[1]);
                return true;
            }
        }

        float speedValue = speed / 10.0f;

        if (target.isFlying()) {
            target.setFlySpeed(speedValue);
        } else {
            target.setWalkSpeed(speedValue);
        }

        sender.sendMessage(String.format("§aPrędkość gracza %s została ustawiona na %d!",
                target.getName(), speed));

        if (!target.equals(sender)) {
            target.sendMessage(String.format("§aTwoja prędkość została ustawiona na %d!", speed));
        }

        return true;
    }
}