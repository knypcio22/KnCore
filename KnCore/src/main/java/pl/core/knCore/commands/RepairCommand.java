package pl.core.knCore.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import pl.core.knCore.utils.MessageUtil;

public class RepairCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.repair")) {
            return true;
        }

        if (!MessageUtil.isPlayer(sender)) {
            return true;
        }

        Player player = (Player) sender;
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() == Material.AIR) {
            MessageUtil.sendMessage(sender, "repair.nothing");
            return true;
        }

        if (!isRepairable(item)) {
            MessageUtil.sendMessage(sender, "repair.cannot");
            return true;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable damageable) {
            if (damageable.getDamage() == 0) {
                sender.sendMessage("§cTen przedmiot nie jest uszkodzony!");
                return true;
            }

            damageable.setDamage(0);
            item.setItemMeta(meta);
            MessageUtil.sendMessage(sender, "repair.success");
        } else {
            MessageUtil.sendMessage(sender, "repair.cannot");
        }

        return true;
    }

    private boolean isRepairable(ItemStack item) {
        return item.getType().getMaxDurability() > 0;
    }
}