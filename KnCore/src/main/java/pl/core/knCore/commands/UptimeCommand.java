package pl.core.knCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.core.knCore.KnCore;
import pl.core.knCore.utils.MessageUtil;

public class UptimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.uptime")) {
            return true;
        }

        String uptime = KnCore.getInstance().getUptimeManager().getFormattedUptime();

        MessageUtil.sendMessage(sender, "uptime.result", "uptime", uptime);

        // Dodatkowe informacje
        int onlinePlayers = sender.getServer().getOnlinePlayers().size();
        int maxPlayers = sender.getServer().getMaxPlayers();

        sender.sendMessage(String.format("§7Graczy online: §e%d§7/§e%d", onlinePlayers, maxPlayers));
        sender.sendMessage(String.format("§7Wersja serwera: §e%s",
                sender.getServer().getVersion()));

        return true;
    }
}