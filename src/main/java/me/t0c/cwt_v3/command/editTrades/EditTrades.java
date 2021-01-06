package me.t0c.cwt_v3.command.editTrades;

import me.t0c.cwt_v3.command.Command;
import me.t0c.cwt_v3.command.editTrades.inventories.EditTradesInventory;
import me.t0c.cwt_v3.utils.MessageUtils;
import me.t0c.cwt_v3.utils.traders.CategoryGenerator;
import org.bukkit.entity.Player;

public class EditTrades extends Command {

    public EditTrades(Player player, String[] args) {
        super(player, args);
    }

    public boolean runCommand() {
        // remove or edit trades here
        // if removing a trade, check if it is the last one in the category and if it is then remove that category

        if(args.length == 2) {
            //test if category exists
            // new createEditTradesInventory
            if(CategoryGenerator.getCategoryFromDisplayName(args[1]) != null) {
                EditTradesInventory editTradesInventory = new EditTradesInventory(CategoryGenerator.getCategoryFromDisplayName(args[1]));
                player.openInventory(editTradesInventory.getInventory());
            } else {
                MessageUtils.sendPlayerMessage(player,"That Category does not exist or does not have any trades in it.");
            }
            return true;

        }
        MessageUtils.sendPlayerMessage(player,"Syntax: /cwt edittrades [category name]");
        return true;
    }
}