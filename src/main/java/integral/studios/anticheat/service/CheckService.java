package integral.studios.anticheat.service;

import integral.studios.anticheat.model.PlayerData;
import integral.studios.anticheat.model.check.AbstractCheck;
import integral.studios.anticheat.model.check.PacketCheck;
import integral.studios.anticheat.model.packet.transaction.TransactionInformation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import integral.studios.anticheat.Integral;
import integral.studios.anticheat.model.packet.IPacket;
import integral.studios.anticheat.model.packet.impl.v_1_8_8.inbound.PacketInTransaction;
import org.atteo.classindex.ClassIndex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
@RequiredArgsConstructor
public class CheckService {
    public static final Map<Class<? extends IPacket>, List<Method>> PACKET_CHECKS = new HashMap<>();

    static {
        for (Class<? extends AbstractCheck> c : ClassIndex.getSubclasses(AbstractCheck.class, Integral.class.getClassLoader())) {
            for (Method m : c.getDeclaredMethods()) {
                if (!m.isAnnotationPresent(PacketCheck.class))
                    continue;

                Class<?> paramClass = m.getParameterTypes()[0];
                for (Class<? extends IPacket> packet : ClassIndex.getSubclasses(IPacket.class, Integral.class.getClassLoader())) {
                    if (paramClass.isAssignableFrom(packet))
                        PACKET_CHECKS.computeIfAbsent(packet, k -> new ArrayList<>()).add(m);
                }
            }
        }
    }

    public static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(1);

    private final PlayerData playerData;
    private final LinkedList<TransactionInformation> transactionQueue = new LinkedList<>();

    private void handleTransaction() {
        TransactionInformation head = transactionQueue.peekFirst();

        if (head == null)
            return; // kick player?

        switch (head.getStatus()) {
            case TICK_START:
                head.setStatus(TransactionInformation.TransactionTickStatus.TICK_END);
                break;

            case TICK_END:
                head.getOutboundPackets().forEach(p -> p.handle(playerData));
                transactionQueue.removeFirst();
                break;
        }
    }

    private void runChecks(IPacket packet) {
        List<Method> methods = PACKET_CHECKS.get(packet.getClass());

        if (methods == null)
            return;

        methods.forEach(m -> {
            try {
                m.invoke(playerData.getChecks().get(m.getDeclaringClass()), packet);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    public void handleIncoming(IPacket packet) {
        if (packet instanceof PacketInTransaction) {
            handleTransaction();
            return;
        }

        packet.handle(playerData);
        runChecks(packet);
    }

    public void handleOutgoing(IPacket packet) {
        TransactionInformation head = transactionQueue.peek();

        if (head != null)
            head.getOutboundPackets().add(packet);
    }
}
