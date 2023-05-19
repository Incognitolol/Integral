package integral.studios.anticheat.model.packet.impl.v_1_8_8.outbound;

import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityVelocity;

@Getter
@PacketWrapper(nmsClass = PacketPlayOutEntityVelocity.class)
public class PacketOutEntityVelocity implements IPacket {
    private int entityId;
    private double velocityX;
    private double velocityY;
    private double velocityZ;

    @Override
    public void handle(PlayerData playerData) {
        // TODO: Add implementation for handling this packet
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.entityId = packetDataSerializer.readVarInt();
        this.velocityX = Math.abs(packetDataSerializer.readShort() / 8000.0);
        this.velocityY = packetDataSerializer.readShort() / 8000.0;
        this.velocityZ = Math.abs(packetDataSerializer.readShort() / 8000.0);
    }
}