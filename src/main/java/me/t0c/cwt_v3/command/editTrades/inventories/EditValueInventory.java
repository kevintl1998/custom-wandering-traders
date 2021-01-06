package me.t0c.cwt_v3.command.editTrades.inventories;

import me.t0c.cwt_v3.command.editTrades.TradeValues;
import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.tradestorage.MerchantTrade;
import me.t0c.cwt_v3.utils.StringParser;
import me.t0c.cwt_v3.utils.traders.CategoryGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Deprecated
public class EditValueInventory implements Listener {

    private final EditTradeInventory editTradeInventory;
    private final MerchantTrade merchantTrade;

    private final Inventory inventory;
    private final TradeValues tradeValues;

    public EditValueInventory(EditTradeInventory editTradeInventory, MerchantTrade merchantTrade, TradeValues tradeValues) {
        this.tradeValues = tradeValues;
        this.merchantTrade = merchantTrade;
        this.editTradeInventory = editTradeInventory;

        inventory = Bukkit.createInventory(null, InventoryType.ANVIL);
        initInventory(tradeValues);

        CWT_v3.instance.getServer().getPluginManager().registerEvents(this,CWT_v3.instance);
    }

    public void initInventory(TradeValues tradeValues) {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        ItemMeta itemMeta = item.getItemMeta();
        if(tradeValues.equals(TradeValues.MIN_TRADES)) {
            itemMeta.setDisplayName(Integer.toString(merchantTrade.getMinTrades()));

        } else if(tradeValues.equals(TradeValues.MAX_TRADES)) {
            itemMeta.setDisplayName(Integer.toString(merchantTrade.getMinTrades()));

        } else if(tradeValues.equals(TradeValues.WEIGHT)) {
            itemMeta.setDisplayName(Integer.toString(merchantTrade.getWeight()));

        } else if(tradeValues.equals(TradeValues.CATEGORY)) {
            itemMeta.setDisplayName(editTradeInventory.getEditTradesInventory().getCategory().toString());
        }
        item.setItemMeta(itemMeta);
        System.out.println("display name: " + itemMeta.getDisplayName());
        System.out.println("item type: " + item.getType().toString());
        System.out.println("item amount: " + item.getAmount());
        inventory.setItem(0,item);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(event.getClickedInventory() instanceof AnvilInventory) {
            if(event.getClickedInventory().equals(inventory)) {
                event.setCancelled(true);
                if(event.getSlot() == 2 && inventory.getItem(event.getSlot()) != null) {

                    String temp = event.getClickedInventory().getItem(2).getItemMeta().getDisplayName();;

                     if(tradeValues.equals(TradeValues.MIN_TRADES)) {
                         if(StringParser.INTEGER.isStringType(temp)) {
                             int val = Integer.parseInt(temp);
                             if(val <= merchantTrade.getMaxTrades()) {
                                 merchantTrade.setMinTrades(val);
                                 event.getWhoClicked().sendMessage("Max trades lower was changed successfully.");
                             }
                         }

                     } else if(tradeValues.equals(TradeValues.MAX_TRADES)) {
                         if(StringParser.INTEGER.isStringType(temp)) {
                            int val = Integer.parseInt(temp);
                            if(val >= merchantTrade.getMinTrades()) {
                                merchantTrade.setMaxTrades(val);
                                event.getWhoClicked().sendMessage("Max trades upper was changed successfully.");
                            }
                         }

                     } else if(tradeValues.equals(TradeValues.WEIGHT)) {
                         if(StringParser.INTEGER.isStringType(temp)) {
                             int val = Integer.parseInt(temp);
                             merchantTrade.setWeight(val);
                             event.getWhoClicked().sendMessage("Weight was changed successfully.");
                         }

                     } else if(tradeValues.equals(TradeValues.CATEGORY)) {
                         temp = temp.toLowerCase();
                         if(StringParser.INTEGER.isStringType(temp) && CategoryGenerator.getCategoryFromDisplayName(temp) != null) {
                             CWT_v3.trades.getTrades().get(CategoryGenerator.getCategoryFromDisplayName(temp)).getTraderTrades().add(merchantTrade);
                             CWT_v3.trades.getTrades().get(editTradeInventory.getEditTradesInventory().getCategory()).getTraderTrades().remove(merchantTrade);
                             event.getWhoClicked().sendMessage("Category was changed to " + temp + " successfully.");
                         }
                     }
                }
            }
        }
    }
    public Inventory getInventory() { return inventory; }
}