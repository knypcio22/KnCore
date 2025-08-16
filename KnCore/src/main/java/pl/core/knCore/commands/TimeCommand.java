package pl.core.knCore.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class TimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.time")) {
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUżycie: /time <set|add> <wartość>");
            sender.sendMessage("§7Wartości: day, night, noon, midnight lub liczba");
            return true;
        }

        World world;
        if (sender instanceof Player) {
            world = ((Player) sender).getWorld();
        } else {
            world = sender.getServer().getWorlds().get(0);
        }

        String action = args[0].toLowerCase();
        String timeValue = args[1].toLowerCase();

        long time = parseTime(timeValue);
        if (time == -1) {
            sender.sendMessage("§cNieprawidłowa wartość czasu!");
            return true;
        }

        switch (action) {
            case "set" -> {
                world.setTime(time);
                sender.sendMessage(String.format("§aCzas został ustawiony na %s!", timeValue));
            }
            case "add" -> {
                world.setTime(world.getTime() + time);
                sender.sendMessage(String.format("§aDodano %s do czasu świata!", timeValue));
            }
            default -> {
                sender.sendMessage("§cUżyj 'set' lub 'add'!");
                return true;
            }
        }

        return true;
    }

    private long parseTime(String timeValue) {
        return switch (timeValue) {
            case "day", "sunrise" -> 0L;
            case "noon", "midday" -> 6000L;
            case "night", "sunset" -> 12000L;
            case "midnight" -> 18000L;
            default -> {
                try {
                    yield Long.parseLong(timeValue);
                } catch (NumberFormatException e) {
                    yield -1L;
                }
            }
        };
    }
}