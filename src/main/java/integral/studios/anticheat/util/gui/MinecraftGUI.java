package integral.studios.anticheat.util.gui;

import integral.studios.anticheat.Integral;
import integral.studios.anticheat.util.gui.button.Button;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import integral.studios.anticheat.util.gui.fill.GUIFiller;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public abstract class MinecraftGUI {

    @Getter
    private static final Map<UUID, MinecraftGUI> openGUIs = new HashMap<>();

    protected final Map<Integer, Button> buttons = new HashMap<>();
    protected final Player player;
    private String title;
    private final int size;
    private final InventoryType inventoryType;
    protected GUIFiller guiFiller;
    private boolean autoUpdate;

    public MinecraftGUI(String title, int size, Player player) {
        this.title = title;
        this.size = size;
        this.player = player;
        this.inventoryType = InventoryType.CHEST;
    }

    protected abstract void tick(Player player, long lastTickTime);
    protected boolean close(Player player, MinecraftGUI gui) {
        return false;
    }
    protected void onBottomClick(InventoryClickEvent event) {}

    public int getSlot(int x, int y) {
        return x + y * 9;
    }

    public void onClose(InventoryCloseEvent event) {
        boolean result = this.close((Player) event.getPlayer(), this);

        if (result) {
            this.open();
        } else {
            openGUIs.remove(event.getPlayer().getUniqueId());
        }
    }

    public void open() {
        final Inventory inventory = inventoryType == InventoryType.CHEST ?
                Bukkit.createInventory(null, size, ChatColor.translateAlternateColorCodes('&', this.title))
                :
                Bukkit.createInventory(null, inventoryType, ChatColor.translateAlternateColorCodes('&', this.title));

        this.tick(player, System.currentTimeMillis());
        this.update(inventory);
        this.player.openInventory(inventory);
        openGUIs.put(this.player.getUniqueId(), this);
    }

    public void update(Inventory inventory) {
        inventory.clear();
        if (guiFiller != null)
            guiFiller.fill(this, this.player, this.size, this.buttons);
        getButtons().forEach(((integer, button) -> inventory.setItem(integer, button.getItem(this.player))));
    }

    public void update() {
        final Inventory inventory = this.player.getOpenInventory().getTopInventory();
        Inventory newInv;
        try {
            newInv = Bukkit.createInventory(null, inventory.getSize());
        } catch (Exception exception) {
            Integral.get().getLogger().info("Exception in GUI: " + getTitle());
            exception.printStackTrace();
            return;
        }
        getButtons().forEach((integer, button) -> newInv.setItem(integer, button.getItem(player)));

        inventory.setContents(newInv.getContents());
    }

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new GUIListener(), Integral.get());
        new GUITask().runTaskTimerAsynchronously(Integral.get(), 1L, 1L);
    }

}
