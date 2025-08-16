package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.heal")) {
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

        double maxHealth = target.getAttribute(Attribute.MAX_HEALTH).getValue();
        target.setHealth(maxHealth);
        target.setFireTicks(0);
        target.getActivePotionEffects().clear();

        if (target.equals(sender)) {
            MessageUtil.sendMessage(sender, "heal.success");
        } else {
            MessageUtil.sendMessage(sender, "heal.other", "player", target.getName());
            MessageUtil.sendMessage(target, "heal.success");
        }

        return true;
    }
}
