package integral.studios.anticheat.model.processor;

import integral.studios.anticheat.model.packet.IPacket;
import org.atteo.classindex.IndexSubclasses;

@IndexSubclasses
public interface IProcessor {
    /***
     * Updates the processor with information from packet
     * @param packet Packet to update with
     */
    void update(IPacket packet);
}
