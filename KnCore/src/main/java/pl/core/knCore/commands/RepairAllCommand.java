package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import pl.core.knCore.utils.MessageUtil;

public class RepairAllCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.repairall")) {
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

        int repairedCount = 0;

        // Napraw przedmioty w ekwipunku
        for (ItemStack item : target.getInventory().getContents()) {
            if (repairItem(item)) {
                repairedCount++;
            }
        }

        // Napraw przedmioty na graczu (zbroja)
        for (ItemStack armor : target.getInventory().getArmorContents()) {
            if (repairItem(armor)) {
                repairedCount++;
            }
        }

        // Napraw przedmiot w drugiej ręce
        if (repairItem(target.getInventory().getItemInOffHand())) {
            repairedCount++;
        }

        if (repairedCount == 0) {
            MessageUtil.sendMessage(sender, "repairall.nothing");
        } else {
            MessageUtil.sendMessage(sender, "repairall.success", "count", String.valueOf(repairedCount));
            if (!target.equals(sender)) {
                MessageUtil.sendMessage(target, "repairall.success", "count", String.valueOf(repairedCount));
            }
        }

        return true;
    }

    private boolean repairItem(ItemStack item) {
        if (item == null || item.getType().getMaxDurability() == 0) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable damageable && damageable.getDamage() > 0) {
            damageable.setDamage(0);
            item.setItemMeta(meta);
            return true;
        }

        return false;
    }
}