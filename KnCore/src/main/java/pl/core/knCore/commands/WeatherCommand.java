package pl.core.knCore.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class WeatherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.weather")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUżycie: /weather <clear|rain|thunder>");
            return true;
        }

        World world;
        if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
        } else {
            world = sender.getServer().getWorlds().get(0);
        }

        String weatherType = args[0].toLowerCase();

        switch (weatherType) {
            case "clear", "sun", "sunny" -> {
                world.setStorm(false);
                world.setThundering(false);
                world.setWeatherDuration(6000);
                sender.sendMessage("§aPogoda została zmieniona na słoneczną!");
            }
            case "rain", "rainy" -> {
                world.setStorm(true);
                world.setThundering(false);
                world.setWeatherDuration(6000);
                sender.sendMessage("§aPogoda została zmieniona na deszczową!");
            }
            case "thunder", "storm", "thunderstorm" -> {
                world.setStorm(true);
                world.setThundering(true);
                world.setWeatherDuration(6000);
                sender.sendMessage("§aPogoda została zmieniona na burzową!");
            }
            default -> {
                sender.sendMessage("§cNieprawidłowy typ pogody! Użyj: clear, rain, thunder");
                return true;
            }
        }

        return true;
    }
}
