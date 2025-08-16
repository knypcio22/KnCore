package pl.core.knCore.managers;

import java.time.Duration;
import java.time.LocalDateTime;

public class UptimeManager {

    private final LocalDateTime startTime;

    public UptimeManager() {
        this.startTime = LocalDateTime.now();
    }

    public String getFormattedUptime() {
        Duration uptime = Duration.between(startTime, LocalDateTime.now());

        long days = uptime.toDays();
        long hours = uptime.toHours() % 24;
        long minutes = uptime.toMinutes() % 60;
        long seconds = uptime.getSeconds() % 60;

        StringBuilder sb = new StringBuilder();

        if (days > 0) {
            sb.append(days).append("d ");
        }
        if (hours > 0 || days > 0) {
            sb.append(hours).append("h ");
        }
        if (minutes > 0 || hours > 0 || days > 0) {
            sb.append(minutes).append("m ");
        }
        sb.append(seconds).append("s");

        return sb.toString();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Duration getUptime() {
        return Duration.between(startTime, LocalDateTime.now());
    }
}
