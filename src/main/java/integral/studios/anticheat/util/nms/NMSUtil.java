package integral.studios.anticheat.util.nms;

import org.bukkit.Bukkit;

public class NMSUtil {
    public static String NMS_VERSION_SUFFIX = Bukkit.getServer().getClass().getPackage().getName()
            .replace(".", ",").split(",")[3];
}
