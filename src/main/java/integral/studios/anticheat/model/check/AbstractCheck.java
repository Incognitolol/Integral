package integral.studios.anticheat.model.check;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.processor.exempt.ExemptType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import integral.studios.anticheat.util.chat.CC;
import org.atteo.classindex.IndexSubclasses;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@IndexSubclasses
@RequiredArgsConstructor
public abstract class AbstractCheck {

    protected final String name;
    protected final PlayerData playerData;
    protected int vl = 0;
    private final Set<UUID> alerts = new HashSet<>();

    private double buffer;

    protected void flag(String details) {
        this.vl++;
        String name = this.playerData.getPlayer().getDisplayName();

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("integral.alerts.view"))
                .forEach(p -> p.sendMessage(
                        CC.GOLD + CC.BOLD + "∫ " + CC.GRAY + "» " +
                                CC.GOLD + name + CC.YELLOW + " flagged check "
                                + CC.GOLD + this.name + CC.YELLOW + " (" + vl + ")" + " "
                                + CC.GRAY + "[" + details + "]"
                ));
    }

    public double increaseBuffer() {
        return buffer = Math.min(10000, buffer + 1);
    }

    public double decreaseBufferBy(final double amount) {
        return buffer = Math.max(0, buffer - amount);
    }

    public void resetBuffer() {
        buffer = 0;
    }

    protected boolean isExempt(final ExemptType exemptType) {
        return playerData.getExemptProcessor().isExempt(exemptType);
    }

    protected boolean isExempt(final ExemptType... exemptTypes) {
        return playerData.getExemptProcessor().isExempt(exemptTypes);
    }

    public boolean toggleAlerts(Player player) {
        if (!alerts.remove(player.getUniqueId())) {
            alerts.add(player.getUniqueId());
            return false;
        }
        return true;
    }

    public void multiplyBuffer(final double multiplier) {
        buffer *= multiplier;
    }
}
