package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

@Getter
@PacketWrapper(nmsClass = PacketPlayInFlying.PacketPlayInPosition.class)
public class PacketInPosition extends PacketInFlying {
    @Override
    public void handle(PlayerData playerData) {
        super.handle(playerData);
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.x = packetDataSerializer.readDouble();
        this.y = packetDataSerializer.readDouble();
        this.z = packetDataSerializer.readDouble();
        this.pos = true;
        super.read(packetDataSerializer);
    }
}
