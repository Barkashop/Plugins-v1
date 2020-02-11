package gamer.bmagic.crafting;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;

public class ItemStackBuilder {
    /**
     * @author Plo457
     * frick your terrible code
     */
    private ItemStack stack;
    private ItemMeta stackMeta;

    public ItemStackBuilder(Material stack) {
        this.stack = new ItemStack(stack);
        this.stackMeta = this.stack.getItemMeta();
    }

    public ItemStackBuilder(ItemStack stack) {
        this.stack = stack;
        this.stackMeta = this.stack.getItemMeta();
    }

    public ItemStack build() {
        this.stack.setItemMeta(this.stackMeta);
        return this.stack;
    }

    public ItemStackBuilder setDisplayName(String s) {

        this.stackMeta.setDisplayName(s);
        return this;
    }

    public ItemStackBuilder addLore(String s) {
        List<String> lore = new ArrayList<String>();
        if (this.stackMeta.hasLore()) {
            lore = this.stackMeta.getLore();
        }
        lore.add(s);
        this.stackMeta.setLore(lore);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        this.stackMeta.setLore(lore);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        this.stackMeta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemStackBuilder setArmorColor(Color color) {
        LeatherArmorMeta meta = (LeatherArmorMeta) this.stackMeta;
        meta.setColor(color);
        return this;
    }

    public ItemStackBuilder setAuthor(String name) {
        BookMeta meta = (BookMeta) this.stackMeta;
        meta.setAuthor(name);
        return this;
    }

    public ItemStackBuilder setTitle(String name) {
        BookMeta meta = (BookMeta) this.stackMeta;
        meta.setTitle(name);
        return this;
    }

    public ItemStackBuilder setPage(int page, String text) {
        BookMeta meta = (BookMeta) this.stackMeta;
        meta.setPage(page, text);
        return this;
    }

    public ItemStackBuilder addPotionEffect(PotionEffect effect) {
        PotionMeta meta = (PotionMeta) this.stackMeta;
        meta.addCustomEffect(effect, true);
        return this;
    }


    public ItemStackBuilder setDamage(int dmg) {
        short dm = (short) dmg;
        ((Damageable) this.stack.getItemMeta()).setDamage(dm);
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.stack.setAmount(amount);
        return this;
    }
}
