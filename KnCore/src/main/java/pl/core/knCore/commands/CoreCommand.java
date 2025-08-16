package pl.core.knCore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.ConfigManager;
import pl.core.knCore.utils.MessageUtil;


public class CoreCommand implements CommandExecutor {
    private ConfigManager configManager;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.admin")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUżycie: /core <reload|info>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                try {
                    KnCore.getInstance().getConfigManager().loadConfig();
                    MessageUtil.sendMessage(sender, "core.reloaded");
                } catch (Exception e) {
                    sender.sendMessage("§cBłąd podczas przeładowywania: " + e.getMessage());
                    KnCore.getInstance().getLogger().severe("Błąd przeładowania: " + e.getMessage());
                }
            }

            case "info" -> {
                String version = KnCore.getInstance().getDescription().getVersion();
                String author = String.join(", ", KnCore.getInstance().getDescription().getAuthors());
                if (author.isEmpty()) author = "Nieznany";

                String message = KnCore.getInstance().getConfigManager()
                        .getMessage("core.info", "version", version)
                        .replace("{author}", author);

                sender.sendMessage(message);
                sender.sendMessage("§7Załadowane komendy: §bfeed, heal, fly, god, speed, gm, tp, time, weather, vanish, repair, repairall, afk, ping, tps, uptime, broadcast");
                sender.sendMessage("§7Strona: §bhttps://github.com/yourrepo/MinecraftCore");
            }

            default -> sender.sendMessage("§cNieznana opcja! Użyj: reload, info");
        }

        return true;
    }
}