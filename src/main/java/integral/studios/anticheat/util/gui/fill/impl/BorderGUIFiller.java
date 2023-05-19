package integral.studios.anticheat.util.gui.fill.impl;

import integral.studios.anticheat.util.gui.MinecraftGUI;
import integral.studios.anticheat.util.gui.button.Button;
import integral.studios.anticheat.util.items.ItemBuilder;
import integral.studios.anticheat.util.gui.fill.GUIFiller;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class BorderGUIFiller implements GUIFiller {

    ItemStack[] itemStacks;

    public BorderGUIFiller(ItemStack... itemStacks) {
        this.itemStacks = itemStacks;
    }

    public BorderGUIFiller() {
        this(new ItemBuilder(Material.STAINED_GLASS_PANE, DyeColor.SILVER.getWoolData()).setDisplayName("").build());
    }

    @Override
    public void fill(MinecraftGUI gui, Player player, int size, Map<Integer, Button> buttons) {
        int x = 0;
        for (int i = 0; i < size; i++) {
            if (i < 8 || i > size - 9 || i % 9 == 0) {
                if (x == itemStacks.length - 1) {
                    x = 0;
                }
                buttons.putIfAbsent(i, Button.createPlaceholder(itemStacks[x]));
                x++;
            }
        }
    }

}
