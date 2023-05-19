package integral.studios.anticheat.util.gui.button;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ButtonBuilder {

    private final List<String> lore = new ArrayList<>();
    private ItemStack item;
    private ItemMeta meta;
    private Consumer<Player> clickHandler;

    public ButtonBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    public ButtonBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ButtonBuilder setItem(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
        if (item.hasItemMeta() && meta.hasLore()) {
            meta.getLore().forEach(this::lore);
        }
        return this;
    }

    public ButtonBuilder name(String string) {
        this.meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', string));
        return this;
    }

    public ButtonBuilder lore(String... strings) {
        for (String string : strings) {
            this.lore.add(ChatColor.translateAlternateColorCodes('&', string));
        }
        return this;
    }
    public ButtonBuilder lore(List<String> lore) {
        this.lore.addAll(lore);
        return this;
    }

    public ButtonBuilder dur(short dur) {
        this.item.setDurability(dur);
        return this;
    }

    public ButtonBuilder clicked(Consumer<Player> clickHandler) {
        this.clickHandler = clickHandler;
        return this;
    }

    public Button build() {
        meta.setLore(lore);
        item.setItemMeta(meta);
        return new Button() {
            @Override
            public ItemStack getItem(Player player) {
                return item;
            }

            @Override
            public boolean click(Player player, int slot, ClickType clickType, int hotbarButton) {
                if (clickHandler == null)
                    return true;

                clickHandler.accept(player);
                return true;
            }
        };
    }

}
