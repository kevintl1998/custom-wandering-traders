package me.t0c.cwt_v3.tradestorage;

import lombok.Data;
import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.config.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Trades {

    public Trades() { }

    public Trades(Map<Category, CategoryTrades> trades) {
        this.trades = trades;
    }

    private Map<Category, CategoryTrades> trades = new HashMap<>();

    public boolean updateTrades(Category category, MerchantTrade merchantTrade) {
        if(trades.containsKey(category)) {
            // add the trade to the category
            trades.get(category).getTraderTrades().add(merchantTrade);
            CWT_v3.tradeFiles.saveConfig(category);
            return true;
        } else { return false; }
    }

    public int getAllTradesWeight() {
        int totalWeight = 0;

        for(Category category : trades.keySet()) {
            totalWeight += trades.get(category).getCategoryWeight();
        }
        return totalWeight;
    }

    public int getTotalWeightWithDefault() {
        return getAllTradesWeight() + Config.getDefaultCategoryWeight();
    }

    public List<String> getCategoryNameList() {
        List<String> categoryNames = new ArrayList<>();
        if(trades == null) return categoryNames;

        for(Category category : trades.keySet()) {
            categoryNames.add(category.toString());
        }
        return categoryNames;
    }

    public boolean areAllCategoriesEmpty() {
        for(Category category : trades.keySet()) {
            if(trades.get(category).getTraderTrades() != null && trades.get(category).getTraderTrades().size() > 0) {
                return false;
            }
        }
        return true;
    }
}
