package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayInTransaction;

@Getter
@PacketWrapper(nmsClass = PacketPlayInTransaction.class)
public class PacketInTransaction implements IPacket {
    private short id;

    @Override
    public void handle(PlayerData playerData) {
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        packetDataSerializer.readByte();
        this.id = packetDataSerializer.readShort();
        packetDataSerializer.readBoolean();
    }
}
