package integral.studios.anticheat.util.items;

import integral.studios.anticheat.util.chat.CC;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder implements Cloneable {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, short subID) {
        this.itemStack = new ItemStack(material, 1, subID);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int subID) {
        this.itemStack = new ItemStack(material, 1, (short) subID);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        this.itemMeta.setDisplayName(CC.translate(name));
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        this.itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Set the lore of the item.
     *
     * @param lore the new lore as list
     * @return the current instance
     */
    public ItemBuilder setLore(List<String> lore) {
        this.itemMeta.setLore(lore);
        return this;
    }

    /**
     * Add to the lore of the item.
     *
     * @param entries the lines to add to the existing lore
     * @return the current instance
     */
    public ItemBuilder addToLore(String... entries) {
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.addAll(Arrays.asList(entries));
        itemMeta.setLore(lore);
        return this;
    }

    /**
     * Add an {@link Enchantment} to this item
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return the current instance
     */
    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        this.itemMeta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Store an {@link Enchantment} in this item.
     * <p>
     * This is only usable on {@link EnchantmentStorageMeta}s (Enchanted books for example).
     *
     * @param enchantment the enchantment to store
     * @param level       the level of the enchantment
     * @return the current instance
     */
    public ItemBuilder storeEnchantment(Enchantment enchantment, int level) {
        if (this.itemMeta instanceof EnchantmentStorageMeta)
            ((EnchantmentStorageMeta) this.itemMeta).addStoredEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Change the amount of this item
     *
     * @param amount the new amount
     * @return the current instance
     */
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    /**
     * Change if this item is unbreakable or not.
     *
     * @param unbreakable the new unbreakable state of the item
     * @return the current instance
     */
    public ItemBuilder setUnbreakable(Boolean unbreakable) {
        this.itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Change the skull owner of this item.
     * <p>
     * This is only usable on {@link SkullMeta}s.
     *
     * @param owner the new skull owner
     * @return the current instance
     */
    public ItemBuilder setSkullOwner(String owner) {
        if (this.itemMeta instanceof SkullMeta)
            ((SkullMeta) this.itemMeta).setOwner(owner);
        return this;
    }

    /**
     * Change the armor color of this item.
     * <p>
     * This is only usable on {@link LeatherArmorMeta}s.
     *
     * @param color the new armor color of this item
     * @return the current instance
     */
    public ItemBuilder setArmorColor(Color color) {
        if (this.itemMeta instanceof LeatherArmorMeta)
            ((LeatherArmorMeta) this.itemMeta).setColor(color);
        return this;
    }

    /**
     * Change the data (sub-id/durability) of this item.
     *
     * @param data the new data of this item
     * @return the current instance
     */
    public ItemBuilder setData(short data) {
        this.itemStack.setDurability(data);
        return this;
    }

    /**
     * Change the data (sub-id/durability) of this item.
     *
     * @param data the new data of this item
     * @return the current instance
     */
    public ItemBuilder setData(int data) {
        this.itemStack.setDurability((short) data);
        return this;
    }

    /**
     * Change the book author of this item.
     * <p>
     * This is only usable on {@link BookMeta}s.
     *
     * @param author the new book author of this item
     * @return the current instance
     */
    public ItemBuilder setBookAuthor(String author) {
        if (this.itemMeta instanceof BookMeta)
            ((BookMeta) this.itemMeta).setAuthor(author);
        return this;
    }

    /**
     * Change the book title of this item.
     * <p>
     * This is only usable on {@link BookMeta}s.
     *
     * @param title the new book title of this item
     * @return the current instance
     */
    public ItemBuilder setBookTitle(String title) {
        if (this.itemMeta instanceof BookMeta)
            ((BookMeta) this.itemMeta).setTitle(title);
        return this;
    }

    /**
     * Change the book pages of this item.
     * <p>
     * This is only usable on {@link BookMeta}s.
     *
     * @param pages the new pages of this item
     * @return the current instance
     */
    public ItemBuilder setBookPages(String... pages) {
        if (this.itemMeta instanceof BookMeta)
            ((BookMeta) this.itemMeta).setPages(pages);
        return this;
    }

    /**
     * Change the book pages of this item.
     * <p>
     * This is only usable on {@link BookMeta}s.
     *
     * @param pages the new pages of this item
     * @return the current instance
     */
    public ItemBuilder setBookPages(List<String> pages) {
        if (this.itemMeta instanceof BookMeta)
            ((BookMeta) this.itemMeta).setPages(pages);
        return this;
    }

    /**
     * Add flags to this item.
     *
     * @param flags the flags to add to this item
     * @return the current instance
     */
    public ItemBuilder addFlag(ItemFlag... flags) {
        this.itemMeta.addItemFlags(flags);
        return this;
    }

    /**
     * Turn this item builder into a {@link ItemStack}.
     *
     * @return a clone of the item stack built
     */
    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return itemStack.clone();
    }

    /**
     * Create a clone of this item builder.
     *
     * @return a new {@link ItemBuilder} with the same values
     */
    public ItemBuilder clone() {
        return new ItemBuilder(build());
    }
}

