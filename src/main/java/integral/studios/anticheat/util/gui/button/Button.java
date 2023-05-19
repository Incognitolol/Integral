package integral.studios.anticheat.util.gui.button;

import integral.studios.anticheat.util.items.ItemBuilder;
import lombok.Data;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

@Data
public abstract class Button {

    public abstract ItemStack getItem(Player player);

    public abstract boolean click(Player player, int slot, ClickType clickType, int hotbarButton);

    public boolean autoCancelEvent() {
        return true;
    }

    public void click(InventoryClickEvent event) {
        if (!((event.getWhoClicked()) instanceof Player)) return;

        boolean result = click((Player) event.getWhoClicked(), event.getSlot(), event.getClick(), event.getHotbarButton());

        if (result) event.setCancelled(true);
    }

    public static Button createPlaceholder(ItemStack itemStack) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return itemStack;
            }

            @Override
            public boolean click(Player player, int slot, ClickType clickType, int hotbarButton) {
                return true;
            }
        };
    }

    public static Button createPlaceholder(String name, Material material, short subId) {
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return new ItemBuilder(material, subId).setDisplayName(name).build();
            }

            @Override
            public boolean click(Player player, int slot, ClickType clickType, int hotbarButton) {
                return true;
            }
        };
    }

    public static Button createPlaceholder(Material material, short subId) {
        return createPlaceholder("", material, subId);
    }

    public static Button createPlaceholder() {
        return createPlaceholder(Material.STAINED_GLASS_PANE, DyeColor.GRAY.getWoolData());
    }

}
