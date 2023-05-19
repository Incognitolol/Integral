package integral.studios.anticheat.util.gui;

import org.bukkit.scheduler.BukkitRunnable;

public class GUITask extends BukkitRunnable {

    private long lastTickTime = System.currentTimeMillis();

    @Override
    public void run() {
        MinecraftGUI.getOpenGUIs().forEach((uuid, minecraftGUI) -> {
            if (minecraftGUI.isAutoUpdate()) {

                minecraftGUI.buttons.clear();
                minecraftGUI.tick(minecraftGUI.getPlayer(), lastTickTime);
                minecraftGUI.update();

                lastTickTime = System.currentTimeMillis();
            }
        });
    }

}
