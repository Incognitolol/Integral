package integral.studios.anticheat.util.gui.fill.impl;

import integral.studios.anticheat.util.gui.MinecraftGUI;
import integral.studios.anticheat.util.gui.button.Button;
import integral.studios.anticheat.util.gui.fill.GUIFiller;
import org.bukkit.entity.Player;

import java.util.Map;

public class FillGUIFiller implements GUIFiller {

    @Override
    public void fill(MinecraftGUI gui, Player player, int size, Map<Integer, Button> buttons) {
        for (int i = 0; i < size; i++) {
            buttons.putIfAbsent(i, Button.createPlaceholder());
        }
    }

}
