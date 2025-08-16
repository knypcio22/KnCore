    package pl.core.knCore.utils;

    import org.bukkit.command.CommandSender;
    import org.bukkit.entity.Player;
    import pl.core.knCore.KnCore;

    public class MessageUtil {

        private static final KnCore plugin = KnCore.getInstance();

        public static void sendMessage(CommandSender sender, String messageKey) {
            String message = plugin.getConfigManager().getMessage(messageKey);
            sender.sendMessage(message);
        }

        public static void sendMessage(CommandSender sender, String messageKey, String placeholder, String value) {
            String message = plugin.getConfigManager().getMessage(messageKey, placeholder, value);
            sender.sendMessage(message);
        }

        public static boolean hasPermission(CommandSender sender, String permission) {
            if (!sender.hasPermission(permission)) {
                sendMessage(sender, "no-permission");
                return false;
            }
            return true;
        }

        public static boolean isPlayer(CommandSender sender) {
            if (!(sender instanceof Player)) {
                sendMessage(sender, "console-not-allowed");
                return false;
            }
            return true;
        }
    }