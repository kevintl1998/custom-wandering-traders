package me.t0c.cwt_v3.commands.newTrade;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.commands.Command;
import me.t0c.cwt_v3.commands.newTrade.inventory.NewTradeInventory;
import me.t0c.cwt_v3.utils.MessageUtils;
import me.t0c.cwt_v3.utils.StringParser;
import me.t0c.cwt_v3.utils.traders.CategoryGenerator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class NewTrade extends Command implements Listener {

    public NewTrade(Player player, String[] args) {
        super(player, args);

        CWT_v3.instance.getServer().getPluginManager().registerEvents(this,CWT_v3.instance);
    }

    public boolean runCommand() {
        //how it works:
        //cwt newtrade <maxTradesLower> <maxTradesUpper> <weight> <category>
        //then open a gui to put the trade items in

        if(args.length < 5) {
            MessageUtils.sendPlayerMessage(player,"Syntax: /cwt newTrade <maxTradesLower> <maxTradesUpper> <weight> <category>");
        } else

            //error check the args
            if(StringParser.POS_INTEGER.isStringType(args[1]) && StringParser.POS_INTEGER.isStringType(args[2]) && StringParser.POS_INTEGER.isStringType(args[3]) && StringParser.NAMESPACE.isStringType(args[4])) {

                // does category exist
                if(CategoryGenerator.getCategoryFromDisplayName(args[4]) != null) {

                    // parse them into their respective datatypes
                    int maxTradesLower = Integer.parseInt(args[1]);
                    int maxTradesUpper = Integer.parseInt(args[2]);
                    int weight = Integer.parseInt(args[3]);
                    String category = args[4];

                    // error check max and min maxtrades
                    if(maxTradesLower <= maxTradesUpper) {

                        // create the inventory
                        NewTradeInventory newTradeInventory = new NewTradeInventory(maxTradesLower, maxTradesUpper, weight, category);
                        player.openInventory(newTradeInventory.getInventory());

                    } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "maxTradesLower cannot be greater than maxTradesUpper"); }
                } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "Category does not exist. Use /cwt category new <name> <weight>"); }
            } else {
                MessageUtils.sendPlayerMessage(player,"Syntax: /cwt newTrade <maxTradesLower> <maxTradesUpper> <weight> <category>");
            }

        // create a trade here
        // make sure to update both the trades and the weightmap
        return true;
    }
}