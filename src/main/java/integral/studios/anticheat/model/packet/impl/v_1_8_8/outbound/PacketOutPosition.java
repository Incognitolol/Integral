package integral.studios.anticheat.model.packet.impl.v_1_8_8.outbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutPosition;

@Getter
@PacketWrapper(nmsClass = PacketPlayOutPosition.class)
public class PacketOutPosition implements IPacket {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    @Override
    public void handle(PlayerData playerData) {
        playerData.getPositionProcessor().handleTeleport(this);
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.x = packetDataSerializer.readDouble();
        this.y = packetDataSerializer.readDouble();
        this.z = packetDataSerializer.readDouble();
        this.yaw = packetDataSerializer.readFloat();
        this.pitch = packetDataSerializer.readFloat();
    }
}
