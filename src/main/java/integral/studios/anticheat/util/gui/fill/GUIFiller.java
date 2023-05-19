package integral.studios.anticheat.util.gui.fill;

import integral.studios.anticheat.util.gui.MinecraftGUI;
import integral.studios.anticheat.util.gui.button.Button;
import org.bukkit.entity.Player;

import java.util.Map;

public interface GUIFiller {

    void fill(MinecraftGUI gui, Player player, int size, Map<Integer, Button> buttons);

}
