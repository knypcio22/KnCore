package pl.core.knCore.managers;

import org.bukkit.entity.Player;
import pl.core.knCore.KnCore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataManager {

    private final Map<UUID, PlayerData> playerData = new HashMap<>();

    public PlayerData getPlayerData(Player player) {
        return playerData.computeIfAbsent(player.getUniqueId(), k -> new PlayerData());
    }

    public void removePlayerData(UUID uuid) {
        playerData.remove(uuid);
    }

    public void cleanup() {
        playerData.clear();
    }

    public static class PlayerData {
        private boolean godMode = false;
        private boolean flyMode = false;
        private boolean vanishMode = false;
        private boolean afkMode = false;
        private String afkReason = null;
        private long afkTime = 0;

        public boolean isGodMode() {
            return godMode;
        }

        public void setGodMode(boolean godMode) {
            this.godMode = godMode;
        }

        public boolean isFlyMode() {
            return flyMode;
        }

        public void setFlyMode(boolean flyMode) {
            this.flyMode = flyMode;
        }

        public boolean isVanishMode() {
            return vanishMode;
        }

        public void setVanishMode(boolean vanishMode) {
            this.vanishMode = vanishMode;
        }

        public boolean isAfkMode() {
            return afkMode;
        }

        public void setAfkMode(boolean afkMode) {
            this.afkMode = afkMode;
            if (afkMode) {
                this.afkTime = System.currentTimeMillis();
            } else {
                this.afkTime = 0;
                this.afkReason = null;
            }
        }

        public String getAfkReason() {
            return afkReason;
        }

        public void setAfkReason(String afkReason) {
            this.afkReason = afkReason;
        }

        public long getAfkTime() {
            return afkTime;
        }

        public String getAfkDuration() {
            if (!afkMode || afkTime == 0) return null;

            long duration = System.currentTimeMillis() - afkTime;
            long seconds = duration / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;

            if (hours > 0) {
                return hours + "h " + (minutes % 60) + "m";
            } else if (minutes > 0) {
                return minutes + "m " + (seconds % 60) + "s";
            } else {
                return seconds + "s";
            }
        }
    }

    private void setVanishMode(Player player, boolean vanish) {
        if (vanish) {
            // Ukryj gracza przed wszystkimi
            for (Player online : player.getServer().getOnlinePlayers()) {
                if (!online.hasPermission("core.vanish")) {
                    online.hidePlayer(KnCore.getInstance(), player);
                }
            }
        } else {
            // Pokaż gracza wszystkim
            for (Player online : player.getServer().getOnlinePlayers()) {
                online.showPlayer(KnCore.getInstance(), player);
            }
        }
    }
}
