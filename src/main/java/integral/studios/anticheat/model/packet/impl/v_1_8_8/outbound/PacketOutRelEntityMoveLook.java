package integral.studios.anticheat.model.packet.impl.v_1_8_8.outbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;

@Getter
@PacketWrapper(nmsClass = PacketPlayOutEntity.PacketPlayOutRelEntityMoveLook.class)
public class PacketOutRelEntityMoveLook implements IPacket {
    private int entityId;
    private short deltaX;
    private short deltaY;
    private short deltaZ;
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
        this.deltaX = packetDataSerializer.readShort();
        this.deltaY = packetDataSerializer.readShort();
        this.deltaZ = packetDataSerializer.readShort();
        this.yaw = packetDataSerializer.readByte();
        this.pitch = packetDataSerializer.readByte();
        this.onGround = packetDataSerializer.readBoolean();
    }
}
