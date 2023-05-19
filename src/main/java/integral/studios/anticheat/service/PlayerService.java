package integral.studios.anticheat.service;

import integral.studios.anticheat.Integral;
import integral.studios.anticheat.model.server.IntegralMethodProfiler;
import integral.studios.anticheat.model.server.IntegralPlayerList;
import lombok.RequiredArgsConstructor;
import net.minecraft.server.v1_8_R3.DedicatedServer;
import net.minecraft.server.v1_8_R3.MethodProfiler;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PlayerList;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@RequiredArgsConstructor
public class PlayerService {
    private PlayerList originalPlayerList;
    private MethodProfiler originalProfiler;

    private Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);

            Unsafe unsafe = (Unsafe) field.get(null);
            field.setAccessible(false);

            return unsafe;

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private IntegralPlayerList installPlayerListHook(MinecraftServer server, Unsafe unsafe) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        IntegralPlayerList playerList = (IntegralPlayerList) unsafe.allocateInstance(IntegralPlayerList.class);

        for (Field f : PlayerList.class.getDeclaredFields()) {
            if (Modifier.isStatic(f.getModifiers()))
                continue;

            f.setAccessible(true);
            Object o = f.get(originalPlayerList);
            f.set(playerList, o);
            f.setAccessible(false);
        }

        server.a(playerList);
        return playerList;
    }


    private void installMethodProfilerHook(MinecraftServer server, Unsafe unsafe, IntegralPlayerList pl) throws InstantiationException, NoSuchFieldException, IllegalAccessException {
        IntegralMethodProfiler profiler = (IntegralMethodProfiler) unsafe.allocateInstance(IntegralMethodProfiler.class);
        profiler.a = server.methodProfiler.a;
        profiler.playerList = pl;
        Field f = MinecraftServer.class.getDeclaredField("methodProfiler");

        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
        f.set(server, profiler);
    }

    private void uninstallMethodProfilerHook(MinecraftServer server) throws IllegalAccessException, NoSuchFieldException {
        Field f = MinecraftServer.class.getDeclaredField("methodProfiler");
        f.set(server, originalProfiler);

        originalProfiler = null;
    }

    public void register() {
        DedicatedServer server = (DedicatedServer) MinecraftServer.getServer();
        originalPlayerList = server.aP();
        originalProfiler = server.methodProfiler;

        Unsafe unsafe = getUnsafe();
        if (unsafe == null)
            return;

        try {
            IntegralPlayerList pl = installPlayerListHook(server, unsafe);
            installMethodProfilerHook(server, unsafe, pl);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        Integral.get().getServer().getConsoleSender().sendMessage("Successfully initialised PlayerList.");
    }

    public void unregister() {
        DedicatedServer server = (DedicatedServer) MinecraftServer.getServer();

        server.a(originalPlayerList);
        originalPlayerList = null;

        try {
            uninstallMethodProfilerHook(server);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        Integral.get().getServer().getConsoleSender().sendMessage("Successfully unregistered PlayerList.");
    }
}
