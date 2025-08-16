package pl.core.knCore.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.ConfigManager;

public class ServerListPing implements Listener {

    private final ConfigManager configManager;
    private final boolean placeholderAPIEnabled;

    public ServerListPing(KnCore plugin) {
        this.configManager = plugin.getConfigManager();
        this.placeholderAPIEnabled = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String line1 = configManager.getConfig().getString("motd.line1", "&aKnCore Server");
        String line2 = configManager.getConfig().getString("motd.line2", "&7Graczy: &a{online}&7/&a{max}");
        int slots = configManager.getConfig().getInt("motd.slots", Bukkit.getMaxPlayers());

        // Obsługa PlaceholderAPI
        if (placeholderAPIEnabled) {
            // Używamy null jako OfflinePlayer ponieważ w MOTD nie mamy konkretnego gracza
            line1 = PlaceholderAPI.setPlaceholders((OfflinePlayer) null, line1);
            line2 = PlaceholderAPI.setPlaceholders((OfflinePlayer) null, line2);
        }

        // Konwersja kolorów
        line1 = ChatColor.translateAlternateColorCodes('&', line1);
        line2 = ChatColor.translateAlternateColorCodes('&', line2);

        String motd = line1 + "\n" + line2;

        event.setMotd(motd);
        event.setMaxPlayers(slots);
    }
}