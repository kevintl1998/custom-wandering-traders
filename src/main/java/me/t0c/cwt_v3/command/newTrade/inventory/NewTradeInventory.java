package me.t0c.cwt_v3.command.newTrade.inventory;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.tradestorage.Category;
import me.t0c.cwt_v3.tradestorage.MerchantTrade;
import me.t0c.cwt_v3.tradestorage.TradeItem;
import me.t0c.cwt_v3.utils.traders.CategoryGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class NewTradeInventory implements Listener {

    public NewTradeInventory(int maxTradesLower, int maxTradesUpper, int weight, String category) {

        CWT_v3.instance.getServer().getPluginManager().registerEvents(this, CWT_v3.instance);

        this.merchantTrade = new MerchantTrade(maxTradesLower, maxTradesUpper, weight);
        this.category = CategoryGenerator.getCategoryFromDisplayName(category);
        this.inventory = Bukkit.createInventory(null, 9, "Create a trade.");

        createNewTradeInventory(inventory,maxTradesLower,maxTradesUpper,weight, this.category);

        inUI = true;
    }

    private Inventory inventory;
    private MerchantTrade merchantTrade;
    private Category category;
    private boolean inUI;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(inventory.equals(event.getClickedInventory())) {
            Inventory inventory = event.getClickedInventory();
            if(event.getSlot() == 0) {
                // cancel trade
                event.getWhoClicked().closeInventory();
                // give the items back to the player that they put in
                if(event.getInventory().getItem(1) != null)
                    event.getWhoClicked().getInventory().addItem(inventory.getItem(1));
                if(event.getInventory().getItem(2) != null)
                    event.getWhoClicked().getInventory().addItem(inventory.getItem(2));
                if(event.getInventory().getItem(7) != null)
                    event.getWhoClicked().getInventory().addItem(inventory.getItem(7));


                // confirm trade
            } else if(event.getSlot() == 8) {
                // error check
                if((inventory.getItem(1) != null || inventory.getItem(2) != null) && inventory.getItem(7) != null) {
                    event.setCancelled(true);

                    // if valid trade, save the trade
                    List<TradeItem> tradeItems = new ArrayList<>();

                    if(inventory.getItem(1) != null)
                        tradeItems.add(TradeItem.getTradeItem(inventory.getItem(1)));

                    if(inventory.getItem(2) != null)
                        tradeItems.add(TradeItem.getTradeItem(inventory.getItem(2)));

                    merchantTrade.setInputs(tradeItems);
                    merchantTrade.setResult(TradeItem.getTradeItem(inventory.getItem(7)));
                    CWT_v3.trades.updateTrades(category, merchantTrade);

                    //then remove inventory from newTradeInventorySet
                    inUI = false;
                    event.getWhoClicked().closeInventory();
                    event.getWhoClicked().sendMessage("Successfully added trade.");

                } else {
                    // msg player "not valid trade" and do nothing
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage("That is not a valid trade.");

                }

                // if they click on any filler items
            } else if(event.getSlot() >= 3 && event.getSlot() <= 6) {
                // cancel event
                event.setCancelled(true);
            }
        }
    }

    // if the player tries to close the inventory then it closes the trade
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(inUI && inventory.equals(event.getInventory())) {
            event.getPlayer().sendMessage("Trade creation cancelled.");
        }
    }

    public void createNewTradeInventory(Inventory inventory, int minTrades, int maxTrades, int weight, Category category) {
        ItemStack item = new ItemStack(Material.RED_STAINED_GLASS_PANE,1);
        ItemMeta meta = item.getItemMeta();

        // Cancel trade item
        meta.setDisplayName("Cancel Trade");
        item.setItemMeta(meta);
        inventory.setItem(0,item);

        // Confirm trade item
        item.setType(Material.LIME_STAINED_GLASS_PANE);
        meta.setDisplayName("Confirm Trade");
        item.setItemMeta(meta);
        inventory.setItem(8,item);

        // max trades lower item
        item.setType(Material.BLACK_STAINED_GLASS_PANE);
        meta.setDisplayName("Min Trades: " + minTrades);
        item.setItemMeta(meta);
        inventory.setItem(3,item);

        // max trades upper item
        meta.setDisplayName("Max Trades: " + maxTrades);
        item.setItemMeta(meta);
        inventory.setItem(4,item);

        // weight item
        meta.setDisplayName("Weight: " + weight);
        item.setItemMeta(meta);
        inventory.setItem(5,item);

        // category item
        meta.setDisplayName("Category: " + CWT_v3.trades.getTrades().get(category).getDisplayName());
        item.setItemMeta(meta);
        inventory.setItem(6,item);
    }

    public Inventory getInventory() { return inventory; }
}