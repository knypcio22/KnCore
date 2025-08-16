package pl.core.knCore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.core.knCore.utils.MessageUtil;

public class GamemodeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!MessageUtil.hasPermission(sender, "core.gamemode")) {
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§cUżycie: /gm <survival|creative|adventure|spectator> [gracz]");
            return true;
        }

        GameMode gameMode = parseGameMode(args[0]);
        if (gameMode == null) {
            sender.sendMessage("§cNieprawidłowy tryb gry! Dostępne: survival, creative, adventure, spectator");
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

        target.setGameMode(gameMode);

        String modeName = getGameModeName(gameMode);
        sender.sendMessage(String.format("§aTryb gry gracza %s został zmieniony na %s!",
                target.getName(), modeName));

        if (!target.equals(sender)) {
            target.sendMessage(String.format("§aTwój tryb gry został zmieniony na %s!", modeName));
        }

        return true;
    }

    private GameMode parseGameMode(String mode) {
        return switch (mode.toLowerCase()) {
            case "0", "s", "survival" -> GameMode.SURVIVAL;
            case "1", "c", "creative" -> GameMode.CREATIVE;
            case "2", "a", "adventure" -> GameMode.ADVENTURE;
            case "3", "sp", "spectator" -> GameMode.SPECTATOR;
            default -> null;
        };
    }

    private String getGameModeName(GameMode gameMode) {
        return switch (gameMode) {
            case SURVIVAL -> "Survival";
            case CREATIVE -> "Creative";
            case ADVENTURE -> "Adventure";
            case SPECTATOR -> "Spectator";
        };
    }
}