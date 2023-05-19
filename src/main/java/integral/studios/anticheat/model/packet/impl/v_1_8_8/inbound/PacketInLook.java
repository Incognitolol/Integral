package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

@Getter
@PacketWrapper(nmsClass = PacketPlayInFlying.PacketPlayInLook.class)
public class PacketInLook extends PacketInFlying {
    @Override
    public void handle(PlayerData playerData) {
        super.handle(playerData);
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.yaw = packetDataSerializer.readFloat();
        this.pitch = packetDataSerializer.readFloat();
        this.rot = true;
        super.read(packetDataSerializer);
    }
}
