package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.core.knCore.KnCore;
import pl.core.knCore.utils.MessageUtil;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.broadcast")) {
            return true;
        }

        if (args.length == 0) {
            MessageUtil.sendMessage(sender, "broadcast.usage");
            return true;
        }

        // Połącz wszystkie argumenty w jedną wiadomość
        String message = String.join(" ", args);

        // Zamień kody kolorów
        message = message.replace("&", "§");

        // Formatuj wiadomość zgodnie z szablonem
        String formattedMessage = KnCore.getInstance().getConfigManager()
                .getMessage("broadcast.format", "message", message);

        // Wyślij do wszystkich graczy
        Bukkit.broadcastMessage(formattedMessage);

        // Poinformuj nadawcę
        if (!(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("§aWykonano broadcast: " + message);
        }

        return true;
    }
}