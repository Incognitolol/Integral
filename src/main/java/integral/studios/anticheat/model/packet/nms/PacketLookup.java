package integral.studios.anticheat.model.packet.nms;

import integral.studios.anticheat.Integral;
import integral.studios.anticheat.model.packet.IPacket;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import integral.studios.anticheat.model.packet.impl.PacketWrapper;
import org.atteo.classindex.ClassIndex;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class PacketLookup {
    public static Class<? extends IWrappedPacketDataSerializer> SERIALIZER_CACHE = null;
    private static final Map<Class<?>, Class<? extends IPacket>> PACKETS = new HashMap<>();

    public static IPacket getPacket(Object nmsPacket) {
        try {
            if (PACKETS.containsKey(nmsPacket.getClass()))
                return PACKETS.get(nmsPacket.getClass()).newInstance();

            Optional<Class<? extends IPacket>> wrappedClass = StreamSupport.stream(ClassIndex.getSubclasses(IPacket.class, Integral.class.getClassLoader()).spliterator(), false)
                    .filter(c -> c.isAnnotationPresent(PacketWrapper.class))
                    .filter(c -> c.getAnnotation(PacketWrapper.class).nmsClass() == nmsPacket.getClass())
                    .findFirst();

            if (!wrappedClass.isPresent())
                return null;

            PACKETS.put(nmsPacket.getClass(), wrappedClass.get());
            return wrappedClass.get().newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SneakyThrows
    public static IPacket readNmsPacket(Object packet) {
        if (SERIALIZER_CACHE == null)
            SERIALIZER_CACHE = IWrappedPacketDataSerializer.getVersionSerializer();

        IPacket iPacket = PacketLookup.getPacket(packet);

        if (iPacket == null)
            return null;

        IWrappedPacketDataSerializer s = SERIALIZER_CACHE.newInstance();
        s.setBuffer(Unpooled.buffer());
        s.serializePacket(packet);

        iPacket.read(s);
        return iPacket;
    }
}
