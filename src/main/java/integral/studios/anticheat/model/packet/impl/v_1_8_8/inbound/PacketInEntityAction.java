package integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound;

import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import lombok.Getter;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import net.minecraft.server.v1_8_R3.PacketPlayInEntityAction;

@Getter
@PacketWrapper(nmsClass = PacketPlayInEntityAction.class)
public class PacketInEntityAction implements IPacket {
    private int entityId;
    private PacketPlayInEntityAction.EnumPlayerAction action;
    private int jumpBoost;

    @Override
    public void handle(PlayerData playerData) {
        // TODO: Add implementation for handling this packet
    }

    @Override
    public void read(IWrappedPacketDataSerializer packetDataSerializer) {
        this.entityId = packetDataSerializer.readVarInt();
        this.action = packetDataSerializer.readEnum(PacketPlayInEntityAction.EnumPlayerAction.class);
        this.jumpBoost = packetDataSerializer.readVarInt();
    }
}