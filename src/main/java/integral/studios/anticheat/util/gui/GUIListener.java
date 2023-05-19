package integral.studios.anticheat.util.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class GUIListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        if (MinecraftGUI.getOpenGUIs().containsKey(player.getUniqueId())) {
            MinecraftGUI gui = MinecraftGUI.getOpenGUIs().get(player.getUniqueId());

            if (player.getOpenInventory().getTopInventory().equals(inventory)) {
                gui.getButtons().forEach((integer, button) -> {
                    if(item != null) {
                        if(item.getType().equals(Material.SKULL_ITEM) && button.getItem(player).getType().equals(Material.SKULL_ITEM)) {

                            if(isHeadsSame(item, button.getItem(player))) {
                                if (button.autoCancelEvent()) {
                                    event.setCancelled(true);
                                }
                                button.click(event);
                            }
                        } else {
                            if (item.equals(button.getItem(player))) {
                                if (button.autoCancelEvent()) {
                                    event.setCancelled(true);
                                }
                                button.click(event);
                            }
                        }
                    }
                });
            } else {
                gui.onBottomClick(event);
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        MinecraftGUI gui = MinecraftGUI.getOpenGUIs().get(player.getUniqueId());

        if (gui != null) {
            MinecraftGUI.getOpenGUIs().remove(player.getUniqueId());
            gui.onClose(event);
        }
    }
    private boolean isHeadsSame(ItemStack head1, ItemStack head2) {
        if(head1.hasItemMeta() && head2.hasItemMeta()) {
            ItemMeta metaHead1 = head1.getItemMeta();
            ItemMeta metaHead2 = head2.getItemMeta();

            SkullMeta skullMetahead2 = (SkullMeta) head2.getItemMeta();
            SkullMeta skullMetahead1 = (SkullMeta) head1.getItemMeta();

            if(metaHead1.hasLore() && metaHead2.hasLore()) {
                List<String> head1Lore = metaHead1.getLore();
                List<String> head2Lore = metaHead2.getLore();

                if(metaHead1.hasDisplayName() && metaHead2.hasDisplayName()) {
                    String head1DisplayName = metaHead1.getDisplayName();
                    String head2DisplayName = metaHead2.getDisplayName();

                    if(skullMetahead1.hasOwner() && skullMetahead2.hasOwner()) {
                        String head1Owner = skullMetahead1.getOwner();
                        String head2Owner = skullMetahead2.getOwner();

                        if(head1Lore.equals(head2Lore) && head1DisplayName.equalsIgnoreCase(head2DisplayName) && head1Owner.equalsIgnoreCase(head2Owner)) {
                            return true;
                        }
                    }
                }
            }
        }
        return head1.isSimilar(head2);
    }


}
