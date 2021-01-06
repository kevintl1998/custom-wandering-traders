package me.t0c.cwt_v3.command.config;

import me.t0c.cwt_v3.command.Command;
import me.t0c.cwt_v3.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Config extends Command {
    public Config(Player player, String[] args) {
        super(player, args);
    }


    public boolean runCommand() {
        if(args.length == 1) {
            // how to use this command
            MessageUtils.sendPlayerMessage(player,"Syntax: /cwt config [edit|list]");
            return true;

        } else if (args.length == 2 && args[1].equals("list")) {
            // list config values
            listAllConfigValues();
            return true;

        } else if (args.length == 4 && args[1].equals("edit")) {
            switch(args[2].toLowerCase()) {
                case "defaultcategorymintrades":
                    if(me.t0c.cwt_v3.config.Config.setDefaultCategoryMinTrades(args[3]))
                        MessageUtils.sendPlayerMessage(player, "defaultCategoryMinTrades was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "defaultCategoryMinTrades could not be set to " + ChatColor.GREEN + args[3]);
                    return true;

                case "defaultcategorymaxtrades":
                    if(me.t0c.cwt_v3.config.Config.setDefaultCategoryMaxTrades(args[3]))
                        MessageUtils.sendPlayerMessage(player, "defaultCategoryMaxTrades was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "defaultCategoryMaxTrades could not be set to " + ChatColor.GREEN + args[3]);
                    return true;

                case "mixedtradermintrades":
                    if(me.t0c.cwt_v3.config.Config.setMixedTraderMinTrades(args[3]))
                        MessageUtils.sendPlayerMessage(player,"mixedTraderMinTrades was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"mixedTraderMinTrades could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "mixedtradermaxtrades":
                    if(me.t0c.cwt_v3.config.Config.setMixedTraderMaxTrades(args[3]))
                        MessageUtils.sendPlayerMessage(player,"mixedTraderMaxTrades was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"mixedTraderMaxTrades could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "despawntimer":
                    if(me.t0c.cwt_v3.config.Config.setDespawnTimer(args[3]))
                        MessageUtils.sendPlayerMessage(player,"despawnTimer was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"despawnTimer could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "defaultcategoryweight":
                    if(me.t0c.cwt_v3.config.Config.setDefaultCategoryWeight(args[3]))
                        MessageUtils.sendPlayerMessage(player, "defaultCategoryWeight was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "defaultCategoryWeight could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "preventduplicatetrades":
                    if(me.t0c.cwt_v3.config.Config.setPreventDuplicateTrades(args[3]))
                        MessageUtils.sendPlayerMessage(player,"preventDuplicateTrades was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"preventDuplicateTrades could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "removellamasontraderdespawn":
                    if(me.t0c.cwt_v3.config.Config.setRemoveLlamasOnTraderDespawn(args[3]))
                        MessageUtils.sendPlayerMessage(player,"removeLlamasOnTraderDespawn was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"removeLlamasOnTraderDespawn could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "broadcasttraderspawn":
                    if(me.t0c.cwt_v3.config.Config.setBroadcastTraderSpawn(args[3]))
                        MessageUtils.sendPlayerMessage(player,"broadcastTraderSpawn was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"broadcastTraderSpawn could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "leaktradercoords":
                    if(me.t0c.cwt_v3.config.Config.setLeakTraderCoords(args[3]))
                        MessageUtils.sendPlayerMessage(player,"leakTraderCoords was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"leakTraderCoords could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "mixtradercategories":
                    if(me.t0c.cwt_v3.config.Config.setMixTraderCategories(args[3]))
                        MessageUtils.sendPlayerMessage(player,"mixTraderCategories was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player,"mixTraderCategories could not be set to " + ChatColor.RED + args[3]);
                    return true;

                case "keepdefaulttrades":
                    if(me.t0c.cwt_v3.config.Config.setKeepDefaultTrades(args[3]))
                        MessageUtils.sendPlayerMessage(player,"keepDefaultTrades was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "keepDefaultTrades could not be set to " + ChatColor.GREEN + args[3]);
                    return true;

                case "combinedefaultandcustom":
                    if(me.t0c.cwt_v3.config.Config.setCombineDefaultAndCustom(args[3]))
                        MessageUtils.sendPlayerMessage(player, "combineDefaultAndCustom was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "combineDefaultAndCustom could not be set to " + ChatColor.GREEN + args[3]);
                    return true;

                case "defaulttradesamount":
                    if(me.t0c.cwt_v3.config.Config.setDefaultTradesAmount(args[3]))
                        MessageUtils.sendPlayerMessage(player, "defaultTradesAmount was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "defaultTradesAmount could not be set to " + ChatColor.GREEN + args[3]);
                    return true;

                case "appenddefaultorcustom":
                    if(me.t0c.cwt_v3.config.Config.setAppendDefaultOrCustom(args[3]))
                        MessageUtils.sendPlayerMessage(player, "appendDefaultOrCustom was set to " + ChatColor.GREEN + args[3]);
                    else MessageUtils.sendPlayerMessage(player, "appendDefaultOrCustom could not be set to " + ChatColor.GREEN + args[3]);
                    return true;

                default:

                    MessageUtils.sendPlayerMessage(player,"Syntax: /cwt config edit <configOption> <newValue>");
                    return true;
            }

        } else if(args.length == 3) {
            switch(args[2].toLowerCase()){

                case "defaultcategorymintrades":
                    MessageUtils.sendPlayerMessage(player, "defaultCategoryMinTrades is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getDefaultCategoryMinTrades());
                    break;
                case "defaultcategorymaxtrades":
                    MessageUtils.sendPlayerMessage(player, "defaultCategoryMaxTrades is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getDefaultCategoryMaxTrades());
                    break;
                case "mixedtradermintrades":
                    MessageUtils.sendPlayerMessage(player, "mixedTraderMinTrades is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getMixedTraderMinTrades());
                    break;
                case "mixedtradermaxtrades":
                    MessageUtils.sendPlayerMessage(player, "mixedTraderMaxTrades is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getMixedTraderMaxTrades());
                    break;
                case "despawntimer":
                    MessageUtils.sendPlayerMessage(player, "despawnTimer is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getDespawnTimer());
                    break;
                case "defaultcategoryweight":
                    MessageUtils.sendPlayerMessage(player, "defaultCategoryWeight " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getDefaultCategoryWeight());

                    break;
                case "preventduplicatetrades":
                    MessageUtils.sendPlayerMessage(player, "preventDuplicateTrades is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getPreventDuplicateTrades());
                    break;
                case "removellamasontraderdespawn":
                    MessageUtils.sendPlayerMessage(player, "removeLlamasOnTraderDespawn is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getRemoveLlamasOnTraderDespawn());
                    break;
                case "broadcasttraderspawn":
                    MessageUtils.sendPlayerMessage(player, "broadcastTraderSpawn is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getBroadcastTraderSpawn());
                    break;
                case "leaktradercoords":
                    MessageUtils.sendPlayerMessage(player, "leakTraderCoords is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getLeakTraderCoords());
                    break;
                case "mixtradercategories":
                    MessageUtils.sendPlayerMessage(player, "mixTraderCategories is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getMixTraderCategories());
                    break;
                case "keepdefaulttrades":
                    MessageUtils.sendPlayerMessage(player, "keepDefaultTrades is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getKeepDefaultTrades());
                    break;
                case "combinedefaultandcustom":
                    MessageUtils.sendPlayerMessage(player, "combineDefaultAndCustom is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getCombineDefaultAndCustom());
                    break;
                case "defaulttradesamount":
                    MessageUtils.sendPlayerMessage(player, "defaultTradesAmount is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getDefaultTradesAmount());
                    break;
                case "appenddefaultorcustom":
                    MessageUtils.sendPlayerMessage(player, "appendDefaultOrCustom is set to " + ChatColor.GREEN +
                                                            me.t0c.cwt_v3.config.Config.getAppendDefaultOrCustom());
                    break;
                default:
                    MessageUtils.sendPlayerMessage(player,"Syntax: /cwt config edit <configOption> <newValue>");
            }
        } else { MessageUtils.sendPlayerMessage(player,"Syntax: /cwt config edit <configOption> <newValue>"); }
        return true;
    }

    private void listAllConfigValues() {
        MessageUtils.sendPlayerMessage(player,"Config Options: ");

        player.sendMessage("defaultCategoryMinTrades: " + ChatColor.GREEN +     me.t0c.cwt_v3.config.Config.getDefaultCategoryMinTrades());
        player.sendMessage("defaultCategoryMaxTrades: " + ChatColor.GREEN +     me.t0c.cwt_v3.config.Config.getDefaultCategoryMaxTrades());

        player.sendMessage("mixedTraderMinTrades: " + ChatColor.GREEN +         me.t0c.cwt_v3.config.Config.getMixedTraderMinTrades());
        player.sendMessage("mixedTraderMaxTrades" + ChatColor.GREEN +           me.t0c.cwt_v3.config.Config.getMixedTraderMaxTrades());

        player.sendMessage("despawnTimer: " + ChatColor.GREEN +                 me.t0c.cwt_v3.config.Config.getDespawnTimer());
        player.sendMessage("preventDuplicateTrades: " + ChatColor.GREEN +       me.t0c.cwt_v3.config.Config.getPreventDuplicateTrades());

        player.sendMessage("defaultCategoryWeight: " + ChatColor.GREEN +        me.t0c.cwt_v3.config.Config.getDefaultCategoryWeight());
        player.sendMessage("removeLlamasOnTraderDespawn: " + ChatColor.GREEN +  me.t0c.cwt_v3.config.Config.getRemoveLlamasOnTraderDespawn());

        player.sendMessage("broadcastTraderSpawn: " + ChatColor.GREEN +         me.t0c.cwt_v3.config.Config.getBroadcastTraderSpawn());
        player.sendMessage("leakTraderCoords: " + ChatColor.GREEN +             me.t0c.cwt_v3.config.Config.getLeakTraderCoords());

        player.sendMessage("mixTraderCategories: " + ChatColor.GREEN +          me.t0c.cwt_v3.config.Config.getMixTraderCategories());
        player.sendMessage("keepDefaultTrades: " + ChatColor.GREEN +            me.t0c.cwt_v3.config.Config.getKeepDefaultTrades());

        player.sendMessage("combineDefaultAndCustom: " + ChatColor.GREEN +      me.t0c.cwt_v3.config.Config.getCombineDefaultAndCustom());
        player.sendMessage("defaultTradesAmount: " + ChatColor.GREEN +          me.t0c.cwt_v3.config.Config.getDefaultTradesAmount());

        player.sendMessage("appendDefaultOrCustom: " + ChatColor.GREEN +         me.t0c.cwt_v3.config.Config.getAppendDefaultOrCustom());
    }
}