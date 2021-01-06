package me.t0c.cwt_v3;

import me.t0c.cwt_v3.bukkitrunnables.BukkitRunnableCollection;
import me.t0c.cwt_v3.command.CWTCommand;
import me.t0c.cwt_v3.config.Config;
import me.t0c.cwt_v3.config.tradeFilesHandler.TradeFiles;
import me.t0c.cwt_v3.listeners.TraderLlamaListener;
import me.t0c.cwt_v3.listeners.WanderingTraderListener;
import me.t0c.cwt_v3.tradestorage.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class CWT_v3 extends JavaPlugin {

    public static final String version = "3.0.0";
    public static final String tradesFolder = "trades";

    public static TradeFiles tradeFiles;

    public static Trades trades;

    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        // config
        this.saveDefaultConfig();
        Config.loadConfig(this);

        // trades
        setTrades();

        // commands
        new CWTCommand(this);

        //listeners
        new WanderingTraderListener(this);
        new TraderLlamaListener(this);

    }

    @Override
    public void onDisable() {
        BukkitRunnableCollection.runAll();
    }

    public static void setTrades() {
        tradeFiles = new TradeFiles(instance, tradesFolder);
        trades = new Trades(new HashMap<>());
        trades.setTrades(tradeFiles.loadConfig());
    }

}
