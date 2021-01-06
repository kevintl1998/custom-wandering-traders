package me.t0c.cwt_v3.command;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.command.category.Category;
import me.t0c.cwt_v3.command.config.Config;
import me.t0c.cwt_v3.command.editTrades.EditTrades;
import me.t0c.cwt_v3.command.newTrade.NewTrade;
import me.t0c.cwt_v3.command.newTrader.NewTrader;
import me.t0c.cwt_v3.command.reload.Reload;

import org.bukkit.command.Command;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CWTCommand implements CommandExecutor, TabCompleter {

    public CWTCommand(JavaPlugin plugin) {

        PluginCommand command = plugin.getCommand("CWT");
        if(command != null) {
            command.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player)sender;

            if (args.length == 0) {
                return pluginInfo(player);
            } else {
                switch (args[0].toLowerCase()) {
                    case "edittrades":
                        return new EditTrades(player,args).runCommand();
                    case "newtrade":
                        return new NewTrade(player,args).runCommand();
                    case "newtrader":
                        return new NewTrader(player, args).runCommand();
                    case "config":
                        return new Config(player, args).runCommand();
                    case "category":
                        return new Category(player, args).runCommand();
                    case "reload":
                        //return false;
                        return new Reload(player, args).runCommand();
                }
            }
        }
        return false;
    }

    public boolean pluginInfo(Player player) {
        player.sendMessage("Custom Wandering Traders v2. Version " + CWT_v3.version);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> potential = new ArrayList<>();

        if(args.length == 1) potential = Arrays.asList("editTrades","newTrade","newTrader", "config", "category", "reload");
        else {

            switch(args[0].toLowerCase()) {
                case "edittrades": //cwt edittrades <category>
                    potential = editTradesTabComplete(args);
                    break;
                case "newtrade": //cwt newtrade <maxtradesmin> <maxtradesmax> <weight> <category>
                    potential = newTradeTabComplete(args);
                    break;
                case "newtrader": //cwt newtrader [location]
                    potential = newTraderTabComplete(args);
                    break;
                case "config": //cwt config [edit|list]  || cwt config edit <configOption> <newValue>
                    potential = configTabComplete(args);
                    break;
                case "category": //cwt category [new|list|edit]
                    potential = categoryTabComplete(args);
                    break;
                case "reload": //cwt reload [config|trades]
                    potential = reloadTabComplete(args);
                    break;
            }
        }

        List<String> result = new ArrayList<>();
        for(String s : potential) {
            if(s.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                result.add(s);
        }

        Collections.sort(result);
        return result;
    }

    private List<String> reloadTabComplete(String[] args) {
        if(args.length == 2) {
            return Arrays.asList("config", "trades");
        }
        return new ArrayList<>();
    }

    public List<String> editTradesTabComplete(String[] args) {

        if(args.length == 2) {
            if(CWT_v3.trades == null || CWT_v3.trades.getTrades() == null) return new ArrayList<>();
            return CWT_v3.trades.getCategoryNameList();
        }
        return new ArrayList<>();
    }

    public List<String> newTradeTabComplete(String[] args) {

        if(args.length == 5) {
            if(CWT_v3.trades == null || CWT_v3.trades.getTrades() == null) return new ArrayList<>();
            return CWT_v3.trades.getCategoryNameList();
        }
        return new ArrayList<>();
    }

    //TODO: make it return either coordinates or ~ ~ ~
    public List<String> newTraderTabComplete(String[] args) {
        return new ArrayList<>();
    }

    public List<String> configTabComplete(String[] args) {
        if (args.length == 2) {
            return Arrays.asList("edit", "list");
        } else if(args.length == 3 && args[1].toLowerCase().equals("list")) {
            return new ArrayList<>();
        } else if(args.length == 3 && args[1].toLowerCase().equals("edit")) {
            return Arrays.asList("appendDefaultOrCustom", "broadcastTraderSpawn", "combineDefaultAndCustom", "defaultCategoryMaxTrades", "defaultCategoryMinTrades", "defaultCategoryWeight", "defaultTradesAmount", "despawnTimer", "keepDefaultTrades", "leakTraderCoords", "mixedTraderMaxTrades", "mixedTraderMinTrades", "mixTraderCategories", "preventDuplicateTrades", "removeLlamasOnTraderDespawn");
        } else if(args.length == 4 && args[2].toLowerCase().equals("appenddefaultorcustom")) {
            return Arrays.asList("custom", "default");
        }
        return new ArrayList<>();
    }

    public List<String> categoryTabComplete(String[] args) {
        if(args.length == 2) {
            return Arrays.asList("edit", "list", "new");

        } else if(args.length == 3 && args[1].toLowerCase().equals("edit")) {
            if(CWT_v3.trades == null || CWT_v3.trades.getTrades() == null) return new ArrayList<>();
            return CWT_v3.trades.getCategoryNameList();

        } else if(args.length == 4 && args[1].toLowerCase().equals("edit")) {
            return Arrays.asList("maxTradeOptions", "minTradeOptions", "name", "weight", "remove");

        }
        return new ArrayList<>();
    }
}