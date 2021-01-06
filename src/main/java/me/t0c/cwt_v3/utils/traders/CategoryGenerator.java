package me.t0c.cwt_v3.utils.traders;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.tradestorage.Category;
import me.t0c.cwt_v3.tradestorage.CategoryTrades;

import java.util.ArrayList;
import java.util.Random;

public class CategoryGenerator {

    public CategoryGenerator() { }

    // Guaranteed to return an Category that has trades
    public Category getRandomCategory() {
        if(CWT_v3.trades.areAllCategoriesEmpty()) return null;

        int totalWeight = CWT_v3.trades.getAllTradesWeight();

        int randValue = new Random().nextInt(totalWeight);
        int value = -1;

        for(Category category : CWT_v3.trades.getTrades().keySet()) {
            value += CWT_v3.trades.getTrades().get(category).getCategoryWeight();
            if(value >= randValue && CWT_v3.trades.getTrades().get(category).getTraderTrades().size() > 0) {
                return category;
            }
        }

        return getRandomCategory();
    }

    public Category getRandomCategoryWithDefault() {

        if(CWT_v3.trades.areAllCategoriesEmpty()) return null;

        int totalWeight = CWT_v3.trades.getTotalWeightWithDefault();

        int randValue = new Random().nextInt(totalWeight);
        int value = -1;

        if(CWT_v3.trades.getTrades().size() == 0) {
            return null;
        }

        for(Category category : CWT_v3.trades.getTrades().keySet()) {
            value += CWT_v3.trades.getTrades().get(category).getCategoryWeight();
            if(value >= randValue && CWT_v3.trades.getTrades().get(category).getTraderTrades().size() > 0) {
                return category;
            }
        }

        if(randValue > value) {
            return null;
        }

        return getRandomCategory();
    }

    public static Category getCategoryFromDisplayName(String displayName) {
        for(Category category : CWT_v3.trades.getTrades().keySet()) {
            if(displayName.toLowerCase().equals(CWT_v3.trades.getTrades().get(category).getDisplayName().toLowerCase())) {
                return category;
            }
        }
        return null;
    }

    public static boolean createCategory(String name, int weight, int maxTradeOptionsMin, int maxTradeOptionsMax) {
        Category category = new Category(name.toLowerCase());
        if(CWT_v3.trades.getTrades().containsKey(category)) {
            return false;
        } else {
            CWT_v3.trades.getTrades().put(category, new CategoryTrades(weight, name, maxTradeOptionsMin, maxTradeOptionsMax));
            CWT_v3.trades.getTrades().get(category).setTraderTrades(new ArrayList<>());
            CWT_v3.tradeFiles.saveConfig(category);
            return true;
        }
    }

}
