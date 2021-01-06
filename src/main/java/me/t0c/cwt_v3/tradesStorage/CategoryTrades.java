package me.t0c.cwt_v3.tradesStorage;

import lombok.Data;
import me.t0c.cwt_v3.CWT_v3;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class CategoryTrades {

    public CategoryTrades() { }

    public CategoryTrades(int categoryWeight, String displayName, int minTradeOptions, int maxTradeOptions) {
        this.categoryWeight = categoryWeight;
        this.displayName = displayName;
        this.minTradeOptions = minTradeOptions;
        this.maxTradeOptions = maxTradeOptions;
    }

    private List<MerchantTrade> traderTrades;

    private String displayName;

    private int categoryWeight;
    private int minTradeOptions;
    private int maxTradeOptions;

    public int getTotalWeight() {
        int totalWeight = 0;
        for(MerchantTrade trade : traderTrades) {
            totalWeight += trade.getWeight();
        }
        return totalWeight;
    }

    public static int getTotalTradeCount() {
        int total = 0;
        for(Category category : CWT_v3.trades.getTrades().keySet()) {
            total += CWT_v3.trades.getTrades().get(category).getTraderTrades().size();
        }
        return total;
    }

    public List<MerchantRecipe> getTraderTradesAsMerchantRecipies() {
        List<MerchantRecipe> recipeList = new ArrayList<>();
        for(MerchantTrade merchantTrade : traderTrades) {
            recipeList.add(merchantTrade.toMerchantRecipe());
        }
        return recipeList;
    }

    public MerchantTrade getRandomTrade() {
        if(getTotalWeight() <= 0) return null;
        int randValue = new Random().nextInt(getTotalWeight());
        int value = -1;

        for(MerchantTrade merchantTrade : traderTrades) {
            value += merchantTrade.getWeight();
            if(value >= randValue) {
                return merchantTrade;
            }
        }
        CWT_v3.instance.getLogger().warning("Error getting random trade");
        return null;
    }
}
