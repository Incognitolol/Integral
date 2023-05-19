package integral.studios.anticheat.service;

import lombok.RequiredArgsConstructor;
import integral.studios.anticheat.model.PlayerData;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class PlayerDataService {
    private final Map<UUID, PlayerData> dataMap = new HashMap<>();

    public PlayerData registerPlayer(Player player) {
        PlayerData playerData = new PlayerData(player);
        dataMap.put(player.getUniqueId(), playerData);
        return playerData;
    }

    public PlayerData getPlayer(Player player) {
        return dataMap.get(player.getUniqueId());
    }

    public void unregisterPlayer(Player player) {
        dataMap.remove(player.getUniqueId());
    }
}
