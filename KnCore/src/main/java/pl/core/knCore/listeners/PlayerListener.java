package pl.core.knCore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.core.knCore.KnCore;
import pl.core.knCore.managers.PlayerDataManager;

public class PlayerListener implements Listener {

    private final PlayerDataManager playerDataManager;

    public PlayerListener(PlayerDataManager playerDataManager) {
        this.playerDataManager = playerDataManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.PlayerData data = playerDataManager.getPlayerData(player);

        // Przywróć vanish mode jeśli był włączony
        if (data.isVanishMode()) {
            data.setVanishMode(true);
        }

        // Wyłącz AFK mode przy dołączeniu
        if (data.isAfkMode()) {
            data.setAfkMode(false);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.PlayerData data = playerDataManager.getPlayerData(player);

        // Ukryj wiadomość quit jeśli gracz jest w vanish
        if (data.isVanishMode()) {
            event.setQuitMessage(null);
        }

        // Wyłącz AFK mode przy wyjściu
        if (data.isAfkMode()) {
            data.setAfkMode(false);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.PlayerData data = playerDataManager.getPlayerData(player);

        // Sprawdź, czy gracz jest w AFK
        if (data.isAfkMode()) {
            // Sprawdź, czy faktycznie się poruszył (zmiana X/Y/Z)
            if (!event.getFrom().toVector().equals(event.getTo().toVector())) {
                String duration = data.getAfkDuration();
                data.setAfkMode(false);

                String message = KnCore.getInstance().getConfigManager()
                        .getMessage("afk.disabled", "player", player.getName())
                        .replace("{duration}", duration != null ? duration : "nieznany czas");

                Bukkit.broadcastMessage(message);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGH)
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        PlayerDataManager.PlayerData data = playerDataManager.getPlayerData(player);
        if (data.isGodMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        PlayerDataManager.PlayerData data = playerDataManager.getPlayerData(player);
        if (data.isGodMode()) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();
        PlayerDataManager.PlayerData data = playerDataManager.getPlayerData(player);

        // Wyłącz fly mode gdy gracz przechodzi do survival/adventure
        if (event.getNewGameMode() == GameMode.SURVIVAL ||
                event.getNewGameMode() == GameMode.ADVENTURE) {
            if (data.isFlyMode()) {
                // Pozwól zachować latanie jeśli gracz ma włączony fly mode
                player.setAllowFlight(true);
            }
        }
    }
}