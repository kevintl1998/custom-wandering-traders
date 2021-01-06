package me.t0c.cwt_v3.command.category;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.command.Command;
import me.t0c.cwt_v3.utils.MessageUtils;
import me.t0c.cwt_v3.utils.StringParser;
import me.t0c.cwt_v3.utils.traders.CategoryGenerator;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;


public class Category extends Command {

    public Category(Player player, String[] args) {
        super(player, args);
    }

    public boolean runCommand() {
        //how it works:
        //cwt category <edit|list|new>
        if(args.length == 1) {
            MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category <edit|list|new>");
        } else

        if(args[1].toLowerCase().equals("edit")) {
            return editCategory();
        } else if (args[1].toLowerCase().equals("list")) {
            return listCategories();
        } else if(args[1].toLowerCase().equals("new")) {
            return newCategory();
        } else { MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category <edit|list|new>"); }
        
        return true;
    }

    /* NEW CATEGORY */
    
    public boolean newCategory() {
        //cwt category new <name> <weight> <maxTradeOptionsMin> <maxTradeOptionsMax>
        if(args.length == 6) {
            if (StringParser.NAMESPACE.isStringType(args[2])) {
                if (StringParser.POS_NONZERO_INTEGER.isStringType(args[3])) {
                    if (StringParser.POS_NONZERO_INTEGER.isStringType(args[4])) {
                        if(StringParser.POS_INTEGER.isStringType(args[5])) {
                            if(Integer.parseInt(args[4]) <= Integer.parseInt(args[5])) {
                                if (CategoryGenerator.createCategory(args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5])))
                                    MessageUtils.sendPlayerMessage(player, "Category created with the name " + ChatColor.GREEN + args[2].toLowerCase() + ChatColor.RESET + " and a weight of " + ChatColor.GREEN + args[3]);
                                else
                                    MessageUtils.sendPlayerMessage(player, ChatColor.RED + "That category already exists.");
                            } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "minTradeOptionsMin must be less than or equal to minTradeOptionsMax"); }
                        } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "minTradeOptionsMax must be a positive non-zero integer."); }
                    } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "minTradeOptionsMin must be a positive non-zero integer."); }
                } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "weight must be a positive non-zero integer."); }
            } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + args[2] + " is not a valid category name. "); }
        } else { MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category new <name> <weight> <maxTradeOptionsMin> <maxTradeOptionsMax>"); }

        return true;
    }

    /* LIST CATEGORIES*/

    public boolean listCategories() {
        MessageUtils.sendPlayerMessage(player,"Categories:");
        for(me.t0c.cwt_v3.tradestorage.Category category : CWT_v3.trades.getTrades().keySet()) {
            player.sendMessage("Name: " + ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() + ChatColor.RESET + " | weight: " + ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getCategoryWeight() + ChatColor.RESET + " | Trades: " + ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getTraderTrades().size());
        }
        return true;
    }

    /* EDIT CATEGORY */

    public boolean editCategory() {
        //cwt category edit <name> <changeMaxTradeOptions|changeMinTradeOptions|changeName|changeWeight|remove>

        if(args.length == 2 || args.length == 3) {
            MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category edit <name> <maxTradeOptions|minTradeOptions|name|weight|remove> <new value>");
            return true;
        }

        me.t0c.cwt_v3.tradestorage.Category category = CategoryGenerator.getCategoryFromDisplayName(args[2]);

        if(category != null) {

            switch (args[3].toLowerCase()) {
                case "remove":
                    editRemove(args, category);
                    break;
                case "name":
                    editName(args, category);
                    break;
                case "weight":
                    editWeight(args, category);
                    break;
                case "mintradeoptions":
                    editMinTradeOptions(args, category);
                    break;
                case "maxtradeoptions":
                    editMaxTradeOptions(args, category);
                    break;
                default:
                    MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category edit <name> <maxTradeOptions|minTradeOptions|name|weight|remove> <new value>");
            }
        } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + args[2] + " is not a valid category name."); }
        return true;
    }

    private void editRemove(String[] args, me.t0c.cwt_v3.tradestorage.Category category) {
        if (args.length == 4) {
            if (CWT_v3.trades.getTrades().get(category).getTraderTrades().size() == 0) {
                CWT_v3.trades.getTrades().remove(category);
                MessageUtils.sendPlayerMessage(player, "The category " + ChatColor.GREEN + args[2] + ChatColor.RESET + " was successfully removed.");
                CWT_v3.tradeFiles.saveConfig(category);

            } else {
                MessageUtils.sendPlayerMessage(player, "Cannot delete this category because it is not empty");
            }
        } else {
            MessageUtils.sendPlayerMessage(player, "Syntax: /cwt category remove <name>");
        }
    }

    private void editName(String[] args, me.t0c.cwt_v3.tradestorage.Category category) {
        if (args.length == 4) {

            MessageUtils.sendPlayerMessage(player, "The category " +
                    ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() +
                    ChatColor.RESET + " has a display name of " +
                    ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() +
                    ChatColor.RESET + ".");

        } else if (args.length == 5) {
            if (StringParser.NAMESPACE.isStringType(args[4]) && CategoryGenerator.getCategoryFromDisplayName(args[4]) == null) {

                String oldValue = CWT_v3.trades.getTrades().get(category).getDisplayName();

                //sets the display name
                CWT_v3.trades.getTrades().get(category).setDisplayName(args[4]);
                CWT_v3.tradeFiles.saveConfig(category);

                MessageUtils.sendPlayerMessage(player, "Successfully changed " +
                        ChatColor.GREEN + oldValue +
                        ChatColor.RESET + "'s name to " +
                        ChatColor.GREEN + args[4]);

            } else {
                MessageUtils.sendPlayerMessage(player, "The new name is not valid");
            }

        } else {
            MessageUtils.sendPlayerMessage(player, "Syntax: /cwt category edit <name> name <new value>");
        }
    }

    private void editWeight(String[] args, me.t0c.cwt_v3.tradestorage.Category category) {
        if (args.length == 4) {

            MessageUtils.sendPlayerMessage(player, ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() +
                    ChatColor.RESET + "'s weight is " +
                    ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getCategoryWeight() +
                    ChatColor.RESET + ".");


        } else if (args.length == 5) {
            if (StringParser.POS_NONZERO_INTEGER.isStringType(args[4])) {

                String oldValue = CWT_v3.trades.getTrades().get(category).getDisplayName();

                CWT_v3.trades.getTrades().get(category).setCategoryWeight(Integer.parseInt(args[4]));
                CWT_v3.tradeFiles.saveConfig(category);

                MessageUtils.sendPlayerMessage(player, "Successfully changed " +
                        ChatColor.GREEN + oldValue +
                        ChatColor.RESET + "'s weight to " +
                        ChatColor.GREEN + args[4]);

            } else {
                MessageUtils.sendPlayerMessage(player, "Weight must be a positive integer.");
            }

        } else {
            MessageUtils.sendPlayerMessage(player, "Syntax: /cwt category edit <name> weight <new value>");
        }
    }

    private void editMinTradeOptions(String[] args, me.t0c.cwt_v3.tradestorage.Category category) {
        if (args.length == 4) {

            MessageUtils.sendPlayerMessage(player, ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() +
                    ChatColor.RESET + "'s minTradeOptions is " +
                    ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getMinTradeOptions() +
                    ChatColor.RESET + ".");

        } else if(args.length == 5) {
            if(StringParser.POS_INTEGER.isStringType(args[4])) {

                String oldValue = CWT_v3.trades.getTrades().get(category).getDisplayName();

                if (category.toSetMinTradeOptions(Integer.parseInt(args[4]))) {

                    CWT_v3.tradeFiles.saveConfig(category);

                    MessageUtils.sendPlayerMessage(player, "Successfully changed minTradeOptions for " +
                            ChatColor.GREEN + oldValue +
                            ChatColor.RESET + " to " +
                            ChatColor.GREEN + args[4]);

                } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "minTradeOptions must be less than/equal to maxTradeOptions."); }
            } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "minTradeOptions must be a positive integer."); }
        } else { MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category edit <name> minTradeOptions <new value>"); }
    }

    private  void editMaxTradeOptions(String[] args, me.t0c.cwt_v3.tradestorage.Category category) {
        if(args.length == 4) {

            MessageUtils.sendPlayerMessage(player, ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() +
                    ChatColor.RESET + "'s maxTradeOptions is " +
                    ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getMaxTradeOptions() +
                    ChatColor.RESET + ".");

        } else if(args.length == 5) {
            if(StringParser.POS_INTEGER.isStringType(args[4]) && category.toSetMaxTradeOptions(Integer.parseInt(args[4])) ) {

                CWT_v3.tradeFiles.saveConfig(category);

                MessageUtils.sendPlayerMessage(player, "Successfully changed maxTradeOptions for " +
                        ChatColor.GREEN + CWT_v3.trades.getTrades().get(category).getDisplayName() +
                        ChatColor.RESET + " to " +
                        ChatColor.GREEN + args[4]);

            } else { MessageUtils.sendPlayerMessage(player, ChatColor.RED + "maxTradeOptions must be a positive integer or greater than/equal to minTradeOptions."); }
        } else { MessageUtils.sendPlayerMessage(player,"Syntax: /cwt category edit <name> maxTradeOptions <new value>"); }
    }
}