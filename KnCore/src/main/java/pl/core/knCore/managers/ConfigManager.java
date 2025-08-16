package pl.core.knCore.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.core.knCore.KnCore;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final KnCore plugin;
    private FileConfiguration config;
    private File configFile;

    public ConfigManager(KnCore plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            plugin.saveDefaultConfig();
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        setDefaults();
    }

    private void setDefaults() {
        config.addDefault("messages.no-permission", "&cNie masz uprawnień do tej komendy!");
        config.addDefault("messages.player-not-found", "&cNie znaleziono gracza: {player}");
        config.addDefault("messages.console-not-allowed", "&cTa komenda może być używana tylko przez graczy!");
        config.addDefault("messages.feed.success", "&aWysycenie zostało przywrócone!");
        config.addDefault("messages.feed.other", "&aWysycenie gracza {player} zostało przywrócone!");
        config.addDefault("messages.heal.success", "&aZdrowie zostało przywrócone!");
        config.addDefault("messages.heal.other", "&aZdrowie gracza {player} zostało przywrócone!");
        config.addDefault("messages.fly.enabled", "&aLatanie zostało włączone!");
        config.addDefault("messages.fly.disabled", "&cLatanie zostało wyłączone!");
        config.addDefault("messages.god.enabled", "&aTryb nieśmiertelności został włączony!");
        config.addDefault("messages.god.disabled", "&cTryb nieśmiertelności został wyłączony!");
        config.addDefault("messages.vanish.enabled", "&aTryb niewidzialności został włączony!");
        config.addDefault("messages.vanish.disabled", "&cTryb niewidzialności został wyłączony!");
        config.addDefault("messages.repair.success", "&aPrzedmiot został naprawiony!");
        config.addDefault("messages.repair.nothing", "&cNie masz przedmiotu do naprawy!");
        config.addDefault("messages.repair.cannot", "&cTen przedmiot nie może być naprawiony!");
        config.addDefault("messages.repairall.success", "&aNaprawiono {count} przedmiotów!");
        config.addDefault("messages.repairall.nothing", "&cNie masz przedmiotów do naprawy!");
        config.addDefault("messages.afk.enabled", "&7{player} jest teraz AFK");
        config.addDefault("messages.afk.enabled-reason", "&7{player} jest teraz AFK: {reason}");
        config.addDefault("messages.afk.disabled", "&7{player} wrócił z AFK ({duration})");
        config.addDefault("messages.core.reloaded", "&aPlugin został przeładowany!");
        config.addDefault("messages.core.info", "&6MinecraftCore &7v{version} &eby &b{author}");
        config.addDefault("messages.ping.self", "&aTwoje opóźnienie: &e{ping}ms");
        config.addDefault("messages.ping.other", "&aOpóźnienie gracza &b{player}&a: &e{ping}ms");
        config.addDefault("messages.tps.result", "&aTPS: &e{tps1} &7(1m), &e{tps5} &7(5m), &e{tps15} &7(15m)");
        config.addDefault("messages.uptime.result", "&aSerwer działa od: &e{uptime}");
        config.addDefault("messages.broadcast.format", "&c[OGŁOSZENIE] &f{message}");
        config.addDefault("messages.broadcast.usage", "&cUżycie: /broadcast <wiadomość>");
        config.addDefault("motd.line1", "&a✦ &fWitaj na &aKnCore Server &f✦");
        config.addDefault("motd.line2", "&7➤ &eGraczy online: &a{online}&7/&a{max}");
        config.addDefault("motd.slots", 100);
        config.options().copyDefaults(true);
        saveConfig();
    }

    public String getMessage(String path) {
        return config.getString("messages." + path, "&cBrak wiadomości: " + path)
                .replace("&", "§");
    }

    public String getMessage(String path, String placeholder, String value) {
        return getMessage(path).replace("{" + placeholder + "}", value);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Nie można zapisać config.yml: " + e.getMessage());
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }
}