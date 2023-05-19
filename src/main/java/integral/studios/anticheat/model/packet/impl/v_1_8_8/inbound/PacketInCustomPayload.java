package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;

@Getter
@PacketWrapper(nmsClass = PacketPlayInCustomPayload.class)
public class PacketInCustomPayload implements IPacket {
    private String tag;
    private String data;

    @Override
    public void handle(PlayerData playerData) {
        // TODO: packet in handler
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.tag = packetDataSerializer.readString(20);
        this.data = new String(packetDataSerializer.readByteArray());
    }
}
