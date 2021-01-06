package me.t0c.cwt_v3.tradesStorage;

import lombok.Data;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

@Data
public class TradeItem {

    public TradeItem() { }

    public TradeItem(Material material, int amount, String nbt) {
        this.material = material;
        this.amount = amount;
        this.nbt = nbt;
    }

    private Material material;
    private int amount;
    private String nbt;

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(material, amount);
        net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        try{
            nmsStack.setTag(MojangsonParser.parse(nbt));
        } catch (Exception ignored) { }
        return CraftItemStack.asBukkitCopy(nmsStack);
    }

    public static TradeItem getTradeItem(ItemStack item) {
        net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        return new TradeItem(item.getType(), item.getAmount(), nmsStack.getOrCreateTag().toString());
    }

    public boolean equals(TradeItem tradeItem) {
        return (this.amount == tradeItem.amount) && (this.material.equals(tradeItem.material)) && (this.nbt.equals(tradeItem.nbt));
    }

    @Override
    public String toString() { return this.material.toString() + " " + this.amount; }

    @Override
    public int hashCode() { return super.hashCode(); }
}
