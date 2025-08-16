package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.core.knCore.KnCore;

public class TpsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("core.tps")) {
            sender.sendMessage("§cNie masz uprawnień do tej komendy!");
            return true;
        }

        try {
            // Pobierz TPS z serwera Paper/Spigot
            double[] tps = Bukkit.getServer().getTPS();

            String tps1m = formatTps(tps[0]);
            String tps5m = formatTps(tps[1]);
            String tps15m = formatTps(tps[2]);

            String message = KnCore.getInstance().getConfigManager()
                    .getMessage("tps.result")
                    .replace("{tps1}", tps1m)
                    .replace("{tps5}", tps5m)
                    .replace("{tps15}", tps15m);

            sender.sendMessage(message);

            // Dodatkowe informacje
            Runtime runtime = Runtime.getRuntime();
            long maxMemory = runtime.maxMemory() / 1024 / 1024;
            long totalMemory = runtime.totalMemory() / 1024 / 1024;
            long freeMemory = runtime.freeMemory() / 1024 / 1024;
            long usedMemory = totalMemory - freeMemory;

            sender.sendMessage(String.format("§7Pamięć: §e%dMB§7/§e%dMB §7(Max: §e%dMB§7)",
                    usedMemory, totalMemory, maxMemory));

        } catch (Exception e) {
            sender.sendMessage("§cBłąd podczas pobierania informacji o TPS!");
            KnCore.getInstance().getLogger().warning("Błąd TPS: " + e.getMessage());
        }

        return true;
    }

    private String formatTps(double tps) {
        String color;
        if (tps >= 18.0) {
            color = "§a"; // Zielony - dobry TPS
        } else if (tps >= 15.0) {
            color = "§e"; // Żółty - średni TPS
        } else {
            color = "§c"; // Czerwony - słaby TPS
        }

        return color + String.format("%.2f", Math.min(tps, 20.0));
    }
}