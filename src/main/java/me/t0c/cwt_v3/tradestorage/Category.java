package me.t0c.cwt_v3.tradestorage;

import lombok.Data;
import me.t0c.cwt_v3.CWT_v3;

import java.util.Random;

@Data
public class Category {

    public Category(String fileName) {
        this.fileName = fileName;
    }

    String fileName;

    public int getMaxTradeOptions() {
        if(CWT_v3.trades.getTrades().get(this).getMinTradeOptions() >= CWT_v3.trades.getTrades().get(this).getMaxTradeOptions())
            return CWT_v3.trades.getTrades().get(this).getMaxTradeOptions();
        return new Random().nextInt(CWT_v3.trades.getTrades().get(this).getMaxTradeOptions() - CWT_v3.trades.getTrades().get(this).getMinTradeOptions()) + CWT_v3.trades.getTrades().get(this).getMinTradeOptions();
    }

    public boolean toSetMinTradeOptions(int value) {
        if(value > CWT_v3.trades.getTrades().get(this).getMaxTradeOptions()) {
            return false;
        }
        CWT_v3.trades.getTrades().get(this).setMinTradeOptions(value);
        return true;
    }

    public boolean toSetMaxTradeOptions(int value) {
        if(CWT_v3.trades.getTrades().get(this).getMinTradeOptions() > value) {
            return false;
        }
        CWT_v3.trades.getTrades().get(this).setMaxTradeOptions(value);
        return true;
    }

    public void setFileName(String fileName) { this.fileName = fileName.toLowerCase(); }

    @Override
    public String toString() { return CWT_v3.trades.getTrades().get(this).getDisplayName(); }
}