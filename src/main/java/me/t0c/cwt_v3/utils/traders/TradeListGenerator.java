package me.t0c.cwt_v3.utils.traders;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.config.Config;
import me.t0c.cwt_v3.tradestorage.Category;
import me.t0c.cwt_v3.tradestorage.CategoryTrades;
import me.t0c.cwt_v3.tradestorage.MerchantTrade;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TradeListGenerator {

    public TradeListGenerator(WanderingTrader trader) {
        this.trader = trader;
    }

    private WanderingTrader trader;

    public List<MerchantRecipe> generateTrades() {

        if (Config.getKeepDefaultTrades()) {
            if(CWT_v3.trades == null || CWT_v3.trades.getTrades().size() == 0) {
                return null;
            }

            if (Config.getCombineDefaultAndCustom()) {
                if (Config.getAppendDefaultOrCustom().equals("default")) {
                    if (Config.getMixTraderCategories()) {
                        return appendTrades(getMixedRandomTradeList(getMixedTraderTradeCount()), getDefaultTradesToKeep(trader.getRecipes(), Config.getDefaultTradesAmount()));
                    } else {
                        return appendTrades(getRandomTradeList(new CategoryGenerator().getRandomCategory()), getDefaultTradesToKeep(trader.getRecipes(), Config.getDefaultTradesAmount()));
                    }
                } else {
                    if (Config.getMixTraderCategories()) {
                        return appendTrades(getDefaultTradesToKeep(trader.getRecipes(), Config.getDefaultTradesAmount()), getMixedRandomTradeList(getMixedTraderTradeCount()));
                    } else {
                        return appendTrades(getDefaultTradesToKeep(trader.getRecipes(), Config.getDefaultTradesAmount()), getRandomTradeList(new CategoryGenerator().getRandomCategory()));
                    }
                }
            } else {
                if (Config.getMixTraderCategories()) {
                    return getMixedRandomTradeListWithDefault(trader.getRecipes(), getMixedTraderTradeCount());
                } else {
                    return getRandomTradeListWithDefault(trader.getRecipes());
                }
            }
        } else if (Config.getMixTraderCategories()) {
            return getMixedRandomTradeList(getMixedTraderTradeCount());
        } else {
            return getRandomTradeList(new CategoryGenerator().getRandomCategory());
        }
    }


    /* Functions that utilize the default trades */

    private List<MerchantRecipe> appendTrades(List<MerchantRecipe> recipeList, List<MerchantRecipe> toAppend) {
        if(recipeList == null && toAppend == null) return null;
        List<MerchantRecipe> recipes = new ArrayList<>();
        if(recipeList != null) recipes.addAll(recipeList);
        if(toAppend != null) recipes.addAll(toAppend);

        return recipes;
    }

    private int getDefaultAmount() {
        if (Config.getDefaultCategoryMinTrades() >= Config.getDefaultCategoryMaxTrades())
            return Config.getDefaultCategoryMaxTrades();
        else
            return new Random().nextInt(Config.getDefaultCategoryMaxTrades() - Config.getDefaultCategoryMinTrades()) + Config.getDefaultCategoryMinTrades();
    }

    private MerchantRecipe getRandomDefaultTrade(List<MerchantRecipe> recipeList) {
        return recipeList.get(new Random().nextInt(recipeList.size()));
    }

    private List<MerchantRecipe> getMixedRandomTradeListWithDefault(final List<MerchantRecipe> defaultRecipeList, int amount) {

        List<MerchantRecipe> defaultRecipes = new ArrayList<>(defaultRecipeList);
        List<MerchantRecipe> tradeList = new ArrayList<>();
        int totalAmount = CategoryTrades.getTotalTradeCount() + defaultRecipes.size();

        System.out.println("total amount: " + totalAmount);
        System.out.println("amount: " + amount);

        if (amount > totalAmount)
            amount = CategoryTrades.getTotalTradeCount();

        if (amount == totalAmount) {
            for (Category category : CWT_v3.trades.getTrades().keySet()) {
                for (MerchantTrade merchantTrade : CWT_v3.trades.getTrades().get(category).getTraderTrades()) {
                    tradeList.add(merchantTrade.toMerchantRecipe());
                }
            }
            tradeList.addAll(defaultRecipes);
            return tradeList;
        }

        for (int i = 0; i < totalAmount; i++) {
            Category category = new CategoryGenerator().getRandomCategoryWithDefault();
            if (category == null) {
                tradeList.add(getRandomDefaultTrade(defaultRecipes));
            } else {
                MerchantTrade recipe = getRandomTrade(category);
                if(Config.getPreventDuplicateTrades()) {
                    if (recipe.isInList(tradeList)) {
                        i--;
                    } else { tradeList.add(recipe.toMerchantRecipe()); }
                } else { tradeList.add(recipe.toMerchantRecipe()); }
            }
        }
        return tradeList;
    }

    private List<MerchantRecipe> getRandomTradeListWithDefault(List<MerchantRecipe> defaultRecipeList) {

        Category category = new CategoryGenerator().getRandomCategoryWithDefault();

        if (category == null) {
            return getDefaultTradesToKeep(defaultRecipeList, getDefaultAmount());
        }

        return getRandomTradeList(category);
    }

    private List<MerchantRecipe> getDefaultTradesToKeep(final List<MerchantRecipe> defaultRecipeList, int amount) {

        List<MerchantRecipe> defaultRecipes = new ArrayList<>(defaultRecipeList);
        List<MerchantRecipe> recipeList = new ArrayList<>();

        if(amount >= 6) return defaultRecipes;

        for (int i = 0; i < amount; i++) {
            int index = new Random().nextInt(amount - i);
            MerchantRecipe recipe = defaultRecipes.get(index);
            if(Config.getPreventDuplicateTrades()) {
                if(recipeList.contains(recipe)) {
                    i--;
                } else { recipeList.add(recipe); }
            } else { recipeList.add(recipe); }
        }
        return recipeList;
    }

    /* Functions for mixed categories */

    private List<MerchantRecipe> getMixedRandomTradeList(int amount) {
        if(CWT_v3.trades.areAllCategoriesEmpty()) return null;

        List<MerchantRecipe> tradeList = new ArrayList<>();

        if (amount > CategoryTrades.getTotalTradeCount() && Config.getPreventDuplicateTrades())
            amount = CategoryTrades.getTotalTradeCount();

        for (int i = 0; i < amount; i++) {
            MerchantTrade trade = getRandomTrade(new CategoryGenerator().getRandomCategory());
            if (Config.getPreventDuplicateTrades()) {

                if (tradeList.contains(trade.toMerchantRecipe())) {
                    i--;
                } else {
                    tradeList.add(trade.toMerchantRecipe());
                }

            } else {
                tradeList.add(trade.toMerchantRecipe());
            }
        }
        return tradeList;
    }

    private int getMixedTraderTradeCount() {
        return new Random().nextInt(Config.getMixedTraderMaxTrades() - Config.getMixedTraderMinTrades()) + Config.getMixedTraderMinTrades();
    }

    /* Functions that return random trades from a specific category using the weight values */

    private List<MerchantRecipe> getRandomTradeList(Category category) {
        if(category == null) return null;

        int amount = category.getMaxTradeOptions();
        List<MerchantRecipe> tradeList = new ArrayList<>();

        if (amount >= CWT_v3.trades.getTrades().get(category).getTraderTrades().size() && Config.getPreventDuplicateTrades())
            return CWT_v3.trades.getTrades().get(category).getTraderTradesAsMerchantRecipies();

        for (int i = 0; i < amount; i++) {
            MerchantRecipe recipe = getRandomTrade(category).toMerchantRecipe();
            if (Config.getPreventDuplicateTrades()) {

                if (tradeList.contains(recipe)) {
                    i--;
                } else {
                    tradeList.add(recipe);
                }

            } else {
                tradeList.add(recipe);
            }
        }
        return tradeList;
    }

    private MerchantTrade getRandomTrade(Category category) {
        if (category == null) return null;
        // get total weight
        return CWT_v3.trades.getTrades().get(category).getRandomTrade();
    }
}
