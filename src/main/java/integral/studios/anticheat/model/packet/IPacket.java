package integral.studios.anticheat.model.packet;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.packet.nms.IWrappedPacketDataSerializer;
import org.atteo.classindex.IndexSubclasses;

@IndexSubclasses
public interface IPacket {
    /***
     * Applies packet-unique updates to given PlayerData
     * @param playerData PlayerData object to update (corresponding to player receiving/sending the packet)
     */
    void handle(PlayerData playerData);

    /***
     * Reads packet data from serialized object
     * @param packetDataSerializer Serializer object for given NMS Packet
     */
    void read(IWrappedPacketDataSerializer packetDataSerializer);
}
