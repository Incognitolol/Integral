package integral.studios.anticheat.model.packet.transaction;

import lombok.Getter;
import lombok.Setter;
import integral.studios.anticheat.model.packet.IPacket;

import java.util.LinkedList;

@Getter
@Setter
public class TransactionInformation {
    public enum TransactionTickStatus {
        TICK_START,
        TICK_END
    }

    private TransactionTickStatus status;

    private final LinkedList<IPacket> outboundPackets = new LinkedList<>();

    public TransactionInformation() {
        this.status = TransactionTickStatus.TICK_START;
    }
}
