package integral.studios.anticheat;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import integral.studios.anticheat.model.commands.IntegralCommands;
import integral.studios.anticheat.service.PlayerDataService;
import integral.studios.anticheat.util.registry.ServiceRegistry;
import integral.studios.anticheat.util.registry.ServiceRegistryImpl;
import integral.studios.anticheat.service.packet.PacketService;
import integral.studios.anticheat.service.PlayerService;
import integral.studios.anticheat.util.gui.MinecraftGUI;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Integral extends JavaPlugin {

    @Getter private static Integral instance;

    private final ServiceRegistry registry = new ServiceRegistryImpl();

    @Override
    public void onEnable() {
        instance = this;

        install(PlayerService.class, new PlayerService());
        install(PacketService.class, new PacketService());
        install(PlayerDataService.class, new PlayerDataService());
        install(PaperCommandManager.class, new PaperCommandManager(this));

        get(PlayerService.class).register();

        get(PaperCommandManager.class).enableUnstableAPI("help");

        get(PaperCommandManager.class).setFormat(MessageType.ERROR, ChatColor.RED, ChatColor.YELLOW, ChatColor.RED);
        get(PaperCommandManager.class).setFormat(MessageType.INFO, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.WHITE);
        get(PaperCommandManager.class).setFormat(MessageType.HELP, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.GOLD);
        get(PaperCommandManager.class).setFormat(MessageType.SYNTAX, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.WHITE);

        get(PaperCommandManager.class).registerCommand(new IntegralCommands());

        MinecraftGUI.init();
    }

    @Override
    public void onDisable() {
        get(PlayerService.class).unregister();
    }

    public static Integral get() {
        return instance;
    }

    public static <T> T install(Class<T> key, T service) {
        return instance.registry.put(key, service);
    }

    public static <T> T get(Class<T> tClass) {
        return instance.registry.get(tClass);
    }
}
