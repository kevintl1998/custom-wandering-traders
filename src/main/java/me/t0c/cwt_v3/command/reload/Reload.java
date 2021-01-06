package me.t0c.cwt_v3.command.reload;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.command.Command;
import me.t0c.cwt_v3.config.Config;
import me.t0c.cwt_v3.utils.MessageUtils;
import org.bukkit.entity.Player;

public class Reload extends Command {
    public Reload(Player player, String[] args) {
        super(player, args);
    }

    public boolean runCommand() {
        if(args.length == 1)  {
            CWT_v3.setTrades();
            Config.loadConfig(CWT_v3.instance);
            MessageUtils.sendPlayerMessage(player, "Reloaded config file and trades folder.");

        } else if(args.length == 2) {
            if(args[1].toLowerCase().equals("trades")) {
                CWT_v3.setTrades();
                MessageUtils.sendPlayerMessage(player, "Reloaded trades folder");

            } else if(args[1].toLowerCase().equals("config")) {
                Config.loadConfig(CWT_v3.instance);
                MessageUtils.sendPlayerMessage(player, "Reloaded config.");

            }
        } else {
            MessageUtils.sendPlayerMessage(player, "/cwt reload [config|trades]");
        }
        return true;
    }

}
