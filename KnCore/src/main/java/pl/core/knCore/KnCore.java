package pl.core.knCore;

import org.bukkit.plugin.java.JavaPlugin;
import pl.core.knCore.commands.*;
import pl.core.knCore.listeners.PlayerListener;
import pl.core.knCore.listeners.ServerListPing;
import pl.core.knCore.managers.ConfigManager;
import pl.core.knCore.managers.PlayerDataManager;
import pl.core.knCore.managers.UptimeManager;

import java.util.logging.Logger;

public final class KnCore extends JavaPlugin {

    private static KnCore instance;
    private ConfigManager configManager;
    private PlayerDataManager playerDataManager;
    private UptimeManager uptimeManager;
    private Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = getLogger();

        // Inicjalizacja managerów
        configManager = new ConfigManager(this);
        playerDataManager = new PlayerDataManager();
        uptimeManager = new UptimeManager();

        // Rejestracja komend
        registerCommands();

        // Rejestracja listenerów
        registerListeners();

        // Rejestracja placeholderapi
        registerPlaceholders();

        logger.info("KnCore plugin został pomyślnie załadowany!");
    }

    @Override
    public void onDisable() {
        if (playerDataManager != null) {
            playerDataManager.cleanup();
        }
        logger.info("KnCore plugin został wyłączony!");
    }

    private void registerCommands() {
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("speed").setExecutor(new SpeedCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("time").setExecutor(new TimeCommand());
        getCommand("weather").setExecutor(new WeatherCommand());
        getCommand("vanish").setExecutor(new VanishCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("repairall").setExecutor(new RepairAllCommand());
        getCommand("afk").setExecutor(new AfkCommand());
        getCommand("core").setExecutor(new CoreCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("tps").setExecutor(new TpsCommand());
        getCommand("uptime").setExecutor(new UptimeCommand());
        getCommand("broadcast").setExecutor(new BroadcastCommand());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new PlayerListener(playerDataManager), this);
        getServer().getPluginManager().registerEvents(new ServerListPing(this), this);
    }

    private void registerPlaceholders() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            logger.info("PlaceholderAPI został pomyślnie zintegrowany!");
        } else {
            logger.warning("PlaceholderAPI nie zostało znalezione. Placeholders nie będą dostępne.");
        }
    }

    public static KnCore getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public UptimeManager getUptimeManager() {
        return uptimeManager;
    }
}