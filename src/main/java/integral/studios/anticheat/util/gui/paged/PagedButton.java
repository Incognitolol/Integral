package integral.studios.anticheat.util.gui.paged;

import integral.studios.anticheat.util.gui.button.Button;
import integral.studios.anticheat.util.items.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class PagedButton extends Button {

    private int mod;
    private PagedMinecraftGUI gui;

    public PagedButton(int mod, PagedMinecraftGUI gui) {
        this.mod = mod;
        this.gui = gui;
    }

    private boolean hasNext(Player player) {
        int pg = this.gui.getPage() + this.mod;
        return pg > 0 && this.gui.getPages(player) >= pg;
    }

    @Override
    public ItemStack getItem(Player player) {
        return new ItemBuilder(
                (gui.getPage() + mod > 0 && gui.getPages(gui.getPlayer()) >= gui.getPage() + mod) ? Material.FEATHER : Material.COAL)
                .setDisplayName(
                        (gui.getPage() + mod > 0 && gui.getPages(gui.getPlayer()) >= gui.getPage() + mod) ?
                                (mod > 0 ? ChatColor.GREEN + "Next Page" : ChatColor.YELLOW + "Previous Page") : ChatColor.GRAY + "Out of Pages!")
                .build();
    }

    @Override
    public boolean click(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (!hasNext(player)) {
            player.sendMessage(ChatColor.RED + "There isn't another page in that direction.");
            return true;
        }

        gui.setPage(gui.getPage() + mod);
        gui.update();
        return true;
    }

}
