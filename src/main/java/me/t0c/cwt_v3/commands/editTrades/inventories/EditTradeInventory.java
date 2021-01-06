package me.t0c.cwt_v3.commands.editTrades.inventories;

import me.t0c.cwt_v3.commands.editTrades.enumerations.TradeValues;
import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.tradesStorage.MerchantTrade;
import me.t0c.cwt_v3.tradesStorage.TradeItem;
import me.t0c.cwt_v3.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class EditTradeInventory implements Listener {

    private final EditTradesInventory editTradesInventory;

    private final MerchantTrade merchantTrade;

    private Inventory inventory;

    public EditTradeInventory(EditTradesInventory editTradesInventory, MerchantTrade merchantTrade) {
        this.editTradesInventory = editTradesInventory;
        this.merchantTrade = merchantTrade;
        inventory = Bukkit.createInventory(null, 9, "Select an element to edit.");
        initInventory();

        CWT_v3.instance.getServer().getPluginManager().registerEvents(this,CWT_v3.instance);
    }

    private void initInventory() {
        ItemStack cancel = new ItemStack(Material.RED_STAINED_GLASS_PANE,1);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName("Cancel Edit");
        cancel.setItemMeta(cancelMeta);
        inventory.setItem(0, cancel);

        ItemStack confirm = new ItemStack(Material.LIME_STAINED_GLASS_PANE,1);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName("Confirm Edits");
        confirm.setItemMeta(confirmMeta);
        inventory.setItem(8, confirm);

        ItemStack i1 = merchantTrade.getInputs().get(0).toItemStack();
        inventory.setItem(1, i1);

        if(merchantTrade.getInputs().size() > 1) {
            ItemStack i2 = merchantTrade.getInputs().get(1).toItemStack();
            inventory.setItem(2 ,i2);
        }

        ItemStack r = merchantTrade.getResult().toItemStack();
        inventory.setItem(7, r);

        ItemStack editPane = new ItemStack(Material.BLACK_STAINED_GLASS_PANE,1);
        ItemMeta editPaneMeta = editPane.getItemMeta();

        editPaneMeta.setDisplayName("Min Trades: " + merchantTrade.getMinTrades());
        editPane.setItemMeta(editPaneMeta);
        inventory.setItem(3, editPane);

        editPaneMeta.setDisplayName("Max Trades: " + merchantTrade.getMaxTrades());
        editPane.setItemMeta(editPaneMeta);
        inventory.setItem(4, editPane);

        editPaneMeta.setDisplayName("Weight: " + merchantTrade.getWeight());
        editPane.setItemMeta(editPaneMeta);
        inventory.setItem(5, editPane);

        editPaneMeta.setDisplayName("Category: " + CWT_v3.trades.getTrades().get(editTradesInventory.getCategory()).getDisplayName());
        editPane.setItemMeta(editPaneMeta);
        inventory.setItem(6, editPane);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(inventory.equals(event.getClickedInventory())) {
            event.setCancelled(true);
            switch(event.getSlot()) {
                case 0:
                    cancelEdit((Player) event.getWhoClicked());
                    break;
                case 1:
                case 2:
                case 7:
                    event.setCancelled(false);
                    break;
                case 3:
                    //editTradeValue(TradeValues.MIN_TRADES, (Player) event.getWhoClicked());
                case 4:
                    //editTradeValue(TradeValues.MAX_TRADES, (Player) event.getWhoClicked());
                case 5:
                    //editTradeValue(TradeValues.WEIGHT, (Player) event.getWhoClicked());
                case 6:
                    //editTradeValue(TradeValues.CATEGORY, (Player) event.getWhoClicked());
                    break;
                case 8:
                    saveEdit((Player) event.getWhoClicked());
                    break;
                default:
                    event.getWhoClicked().sendMessage("How did you click that slot???");
            }
        }
    }

    private void saveEdit(Player player) {
        //check that the items are correct
        if((inventory.getItem(1) != null || inventory.getItem(2) != null) && inventory.getItem(7) != null) {
            List<TradeItem> ingredients = new ArrayList<>();
            if(inventory.getItem(1) != null)
                ingredients.add(TradeItem.getTradeItem(inventory.getItem(1)));
            if(inventory.getItem(2) != null)
                ingredients.add(TradeItem.getTradeItem(inventory.getItem(2)));

            merchantTrade.setInputs(ingredients);
            merchantTrade.setResult(TradeItem.getTradeItem(inventory.getItem(7)));

            player.closeInventory();
            editTradesInventory.updateInventory();
            player.openInventory(editTradesInventory.getInventory());

            CWT_v3.tradeFiles.saveConfig(this.getEditTradesInventory().getCategory());

        } else {
            MessageUtils.sendPlayerMessage(player, "Cannot save trade. Put at least one item into the input slots and one item in the result slot."); }
    }

    @Deprecated
    private void editTradeValue(TradeValues tradeValues, Player player) {

        //min trades
        if(tradeValues.equals(TradeValues.MIN_TRADES)) {
            EditValueInventory editValueInventory = new EditValueInventory(this, merchantTrade, TradeValues.MIN_TRADES);
            player.openInventory(editValueInventory.getInventory());

            //max trades
        } else if(tradeValues.equals(TradeValues.MAX_TRADES)) {
            EditValueInventory editValueInventory = new EditValueInventory(this, merchantTrade,TradeValues.MAX_TRADES);
            player.openInventory(editValueInventory.getInventory());
            //weight
        } else if(tradeValues.equals(TradeValues.WEIGHT)) {
            EditValueInventory editValueInventory =  new EditValueInventory(this, merchantTrade,TradeValues.WEIGHT);
            player.openInventory(editValueInventory.getInventory());
            //category
        } else if(tradeValues.equals(TradeValues.CATEGORY)) {
            EditValueInventory editValueInventory = new EditValueInventory(this, merchantTrade,TradeValues.CATEGORY);
            player.openInventory(editValueInventory.getInventory());
        }
    }

    private void cancelEdit(Player player) {
        CWT_v3.tradeFiles.saveConfig(editTradesInventory.getCategory());
        editTradesInventory.updateInventory();
        player.openInventory(editTradesInventory.getInventory());
    }

    public Inventory getInventory() { return inventory; }
    public EditTradesInventory getEditTradesInventory() { return editTradesInventory; }
}