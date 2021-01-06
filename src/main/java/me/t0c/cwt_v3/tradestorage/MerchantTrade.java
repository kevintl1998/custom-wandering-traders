package me.t0c.cwt_v3.tradestorage;

import lombok.Data;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class MerchantTrade {

    public MerchantTrade() {  }

    public MerchantTrade(List<TradeItem> inputs, TradeItem result, int minTrades, int maxTrades, int weight) {
        this.inputs = inputs;
        this.result = result;
        this.maxTrades = maxTrades;
        this.minTrades = minTrades;
        this.weight = weight;
    }

    public MerchantTrade(int minTrades, int maxTrades, int weight) {
        this.minTrades = minTrades;
        this.maxTrades = maxTrades;
        this.weight = weight;
    }

    private List<TradeItem> inputs;
    private TradeItem result;

    private int minTrades;
    private int maxTrades;

    private int weight;

    public MerchantRecipe toMerchantRecipe () {
        MerchantRecipe recipe = new MerchantRecipe(result.toItemStack(), determineMaxUses());
        List<ItemStack> inputs = new ArrayList<>();
        for(TradeItem tradeItem : this.inputs) {
            inputs.add(tradeItem.toItemStack());
        }
        recipe.setIngredients(inputs);
        return recipe;
    }

    public int determineMaxUses() {
        if(minTrades >= maxTrades) {
            return maxTrades;
        } else {
            return (new Random().nextInt(maxTrades - minTrades) + minTrades);
        }
    }

    public boolean isInList(List<MerchantRecipe> recipeList) {
        if(recipeList == null || recipeList.size() == 0) return false;
        for(MerchantRecipe recipe : recipeList) {
            if(recipe.getMaxUses() >= getMinTrades() && recipe.getMaxUses() <= getMaxTrades()) {
                if(TradeItem.getTradeItem(recipe.getResult()).equals(result)) {
                    if (recipe.getIngredients().size() == 1 && inputs.size() == 1) {
                        if (TradeItem.getTradeItem(recipe.getIngredients().get(0)).equals(inputs.get(0))) {
                            return true;
                        }
                    } else if (recipe.getIngredients().size() == 2 && inputs.size() == 2) {
                        if (TradeItem.getTradeItem(recipe.getIngredients().get(0)).equals(inputs.get(0))) {
                            if (TradeItem.getTradeItem(recipe.getIngredients().get(1)).equals(inputs.get(1))) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
