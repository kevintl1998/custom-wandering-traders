package me.t0c.cwt_v3.commands.editTrades.inventories;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.tradesStorage.Category;
import me.t0c.cwt_v3.tradesStorage.MerchantTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EditTradesInventory implements Listener {

    private final Category category;

    private Inventory inventory;
    private int page;

    public EditTradesInventory(Category category) {
        this.page = 0;
        this.category = category;
        this.inventory = Bukkit.createInventory(null,54,"Edit Trades for " + CWT_v3.trades.getTrades().get(category).getDisplayName());
        updateInventory();

        CWT_v3.instance.getServer().getPluginManager().registerEvents(this,CWT_v3.instance);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (inventory.equals(event.getClickedInventory())) {
            if(!event.getClick().equals(ClickType.DOUBLE_CLICK)) {
                event.setCancelled(true);

                if (event.getSlot() == 45) {
                    displayPreviousPage();

                } else if (event.getSlot() == 53) {
                    displayNextPage();

                } else if ((event.getSlot() + 9) % 9 == 0 && inventory.getItem(event.getSlot()) != null) {

                    removeTrade((event.getSlot() + 9) / 9 + (page * 5) - 1);
                    updateInventory();

                } else if ((event.getSlot() + 9) % 9 == 8 && inventory.getItem(event.getSlot()) != null) {
                    EditTradeInventory editTradeInventory = new EditTradeInventory(this, CWT_v3.trades.getTrades().get(category).getTraderTrades().get((event.getSlot() + 9) / 9 + (page * 5) - 1));
                    event.getWhoClicked().openInventory(editTradeInventory.getInventory());
                }
            }
        }
    }

    private void displayPreviousPage() {
        if(page > 0) {
            --page;
            updateInventory();
        }
    }

    private void displayNextPage() {
        if((page + 1) * 5 < CWT_v3.trades.getTrades().get(category).getTraderTrades().size()) {
            ++page;
            updateInventory();
        }
    }

    private void removeTrade(int index) {
        if(CWT_v3.trades.getTrades().get(category).getTraderTrades().size() >= index + 1) {
            CWT_v3.trades.getTrades().get(category).getTraderTrades().remove(index);
        }
        CWT_v3.tradeFiles.saveConfig(category);
    }

    public void updateInventory() {
        // empty the inventory
        for(int i = 0; i < 54; ++i) {
            inventory.setItem(i,null);
        }

        List<MerchantTrade> tradesList = CWT_v3.trades.getTrades().get(category).getTraderTrades();

        int tradesListSize = tradesList.size();

        ItemStack remove = new ItemStack(Material.RED_STAINED_GLASS_PANE,1);
        ItemMeta removeMeta = remove.getItemMeta();
        removeMeta.setDisplayName("Remove Trade");
        remove.setItemMeta(removeMeta);

        ItemStack edit = new ItemStack(Material.BLUE_STAINED_GLASS_PANE,1);
        ItemMeta editMeta = edit.getItemMeta();
        editMeta.setDisplayName("Edit Trade");
        edit.setItemMeta(editMeta);

        ItemStack displayInfo = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta displayInfoMeta = displayInfo.getItemMeta();

        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta paperMeta = paper.getItemMeta();

        ItemStack filler = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE,1);
        ItemMeta fillerMeta = filler.getItemMeta();
        fillerMeta.setDisplayName(" ");

        // update trade slots
        for(int i = 0; i < 5 && page * 5 + i < tradesListSize; i++) {

            //delete/edit slots
            inventory.setItem((i * 9), remove);
            inventory.setItem((i * 9) + 8, edit);
            //trade slots

            inventory.setItem((i * 9) + 1, tradesList.get((page * 5) + i).getInputs().get(0).toItemStack());
            if(tradesList.get(i).getInputs().size() > 1)
                inventory.setItem((i * 9) + 2, tradesList.get((page * 5) + i).getInputs().get(1).toItemStack());
            inventory.setItem((i * 9) + 7, tradesList.get((page * 5) + i).getResult().toItemStack());

            //info slots
            displayInfoMeta.setDisplayName("Min Trades: " + tradesList.get((page * 5) + i).getMinTrades());
            displayInfo.setItemMeta(displayInfoMeta);
            inventory.setItem((i * 9) + 3, displayInfo);

            displayInfoMeta.setDisplayName("Max Trades: " + tradesList.get((page * 5) + i).getMaxTrades());
            displayInfo.setItemMeta(displayInfoMeta);
            inventory.setItem((i * 9)+ 4, displayInfo);

            displayInfoMeta.setDisplayName("Weight: " + tradesList.get((page * 5) + i).getWeight());
            displayInfo.setItemMeta(displayInfoMeta);
            inventory.setItem((i * 9) + 5, displayInfo);

            displayInfoMeta.setDisplayName("Category: " + CWT_v3.trades.getTrades().get(category).getDisplayName());
            displayInfo.setItemMeta(displayInfoMeta);
            inventory.setItem((i * 9) + 6, displayInfo);
        }

        // set filler items
        filler.setItemMeta(fillerMeta);
        for(int i = 45; i < 53; i++) {
            inventory.setItem(i,filler);
        }

        // update next/previous page slots
        if(page > 0) {
            paperMeta.setDisplayName("Previous Page");
            paper.setItemMeta(paperMeta);
            inventory.setItem(45, paper);
        }

        if ((page + 1) * 5 < tradesListSize) {
            paperMeta.setDisplayName("Next Page");
            paper.setItemMeta(paperMeta);
            inventory.setItem(53, paper);
        }

        // update page number
        paperMeta.setDisplayName("page " + (page + 1));
        paper.setItemMeta(paperMeta);
        inventory.setItem(49, paper);



    }

    public Inventory getInventory() { return inventory; }

    public Category getCategory() { return category; }
}