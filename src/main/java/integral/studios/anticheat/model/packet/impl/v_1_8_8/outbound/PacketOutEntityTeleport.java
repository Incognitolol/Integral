package integral.studios.anticheat.model.packet.impl.v_1_8_8.outbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;

@Getter
@PacketWrapper(nmsClass = PacketPlayOutEntityTeleport.class)
public class PacketOutEntityTeleport implements IPacket {
    private int entityId;
    private double x;
    private double y;
    private double z;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    @Override
    public void handle(PlayerData playerData) {
        // TODO: packet in handler
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.entityId = packetDataSerializer.readVarInt();
        this.x = packetDataSerializer.readDouble();
        this.y = packetDataSerializer.readDouble();
        this.z = packetDataSerializer.readDouble();
        this.yaw = packetDataSerializer.readByte();
        this.pitch = packetDataSerializer.readByte();
        this.onGround = packetDataSerializer.readBoolean();
    }
}