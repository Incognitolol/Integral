package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import integral.studios.anticheat.model.PlayerData;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;

@Getter
@PacketWrapper(nmsClass = PacketPlayInFlying.class)
public class PacketInFlying implements IPacket {
    protected double x, y, z;
    protected float yaw, pitch;
    protected boolean ground, rot, pos;

    @Override
    public void handle(PlayerData playerData) {
        playerData.getPositionProcessor().update(this);
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.ground = packetDataSerializer.readUnsignedByte() != 0;
    }
}
