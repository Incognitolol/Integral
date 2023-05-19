package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.packet.IPacket;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;

@Getter
@PacketWrapper(nmsClass = PacketPlayInUseEntity.class)
public class PacketInUseEntity implements IPacket {
    private int entityId;
    private PacketPlayInUseEntity.EnumEntityUseAction useAction;

    @Override
    public void handle(PlayerData playerData) {
        // TODO: Add implementation for handling this packet
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.entityId = packetDataSerializer.readVarInt();
        this.useAction = packetDataSerializer.readEnum(PacketPlayInUseEntity.EnumEntityUseAction.class);
    }
}
