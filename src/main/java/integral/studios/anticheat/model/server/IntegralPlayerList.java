package integral.studios.anticheat.model.server;

import com.mojang.authlib.GameProfile;
import integral.studios.anticheat.service.PlayerDataService;
import integral.studios.anticheat.Integral;
import integral.studios.anticheat.service.packet.PacketService;
import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.util.chat.CC;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.entity.Player;

public class IntegralPlayerList extends DedicatedPlayerList {

    public IntegralPlayerList(DedicatedServer dedicatedServer) {
        super(dedicatedServer);
    }

    @Override
    public EntityPlayer attemptLogin(LoginListener loginlistener, GameProfile gameprofile, String hostname) {
        EntityPlayer entityPlayer = super.attemptLogin(loginlistener, gameprofile, hostname);

        if (entityPlayer == null)
            return null;

        NetworkManager networkManager = loginlistener.networkManager;
        Player player = entityPlayer.getBukkitEntity();
        PlayerData playerData = Integral.get(PlayerDataService.class).registerPlayer(player);
        Integral.get(PacketService.class).inject(playerData, networkManager);

        return entityPlayer;
    }

    @Override
    public String disconnect(EntityPlayer entityplayer) {
        String res = super.disconnect(entityplayer);

        if (entityplayer == null)
            return res;

        Player player = entityplayer.getBukkitEntity();
        Integral.get(PacketService.class).eject(player);
        Integral.get(PlayerDataService.class).unregisterPlayer(player);

        return res;
    }

    @Override
    public void onPlayerJoin(EntityPlayer entityplayer, String joinMessage) {
        super.onPlayerJoin(entityplayer, joinMessage);

        if (entityplayer == null || !entityplayer.getBukkitEntity().hasPermission("integral.admin") || !entityplayer.getBukkitEntity().isOp())
            return;

        entityplayer.getBukkitEntity().sendMessage(CC.GOLD + "∫ " + CC.GRAY + "» " +
                CC.YELLOW + "Welcome back, " + CC.GOLD +
                entityplayer.getBukkitEntity().getDisplayName() + CC.YELLOW  + ".");
    }
}
