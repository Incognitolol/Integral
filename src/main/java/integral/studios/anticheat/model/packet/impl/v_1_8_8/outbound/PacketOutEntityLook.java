package integral.studios.anticheat.model.packet.impl.v_1_8_8.outbound;

import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntity;

@Getter
@PacketWrapper(nmsClass = PacketPlayOutEntity.PacketPlayOutEntityLook.class)
public class PacketOutEntityLook implements IPacket {
    private int entityId;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    @Override
    public void handle(PlayerData playerData) {
        // TODO: Add implementation for handling this packet
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.entityId = packetDataSerializer.readVarInt();
        this.yaw = packetDataSerializer.readByte();
        this.pitch = packetDataSerializer.readByte();
        this.onGround = packetDataSerializer.readBoolean();
    }
}