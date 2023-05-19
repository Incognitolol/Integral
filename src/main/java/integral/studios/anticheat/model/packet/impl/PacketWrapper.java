package integral.studios.anticheat.model.packet.impl;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PacketWrapper {
    Class<?> nmsClass();
}
