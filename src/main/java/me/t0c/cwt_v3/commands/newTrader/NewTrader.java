package me.t0c.cwt_v3.commands.newTrader;

import me.t0c.cwt_v3.commands.Command;
import me.t0c.cwt_v3.utils.MessageUtils;
import me.t0c.cwt_v3.utils.StringParser;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class NewTrader extends Command {

    public NewTrader(Player player, String[] args) {
        super(player, args);

    }

    public boolean runCommand() {
        //summon a new trader here

        if(args.length == 1) {

            createTrader(player.getLocation());
            return true;

        } else if(args.length == 4) {

            if(isValidLocationFromString(player.getWorld(), args[1], args[2], args[3])) {
                //TODO: possibly make this command more versatile
                //ex: /cwt newtrader [category|mixCategories] [location]
                //ex: /cwt newtrader testCategory ~ ~ ~
                //ex: /cwt newtrader mixCategories
                createTrader(new Location(player.getWorld(), Integer.parseInt(args[1]),Integer.parseInt(args[2]),Integer.parseInt(args[3])));
            }
            return true;
        }
        MessageUtils.sendPlayerMessage(player,"/cwt newTrader [location]");
        return true;
    }

    public boolean isValidLocation(Location location) {
        if(location.getY() >= 0 && location.getY() <= 255) {
            if (location.getX() >= -30000000 && location.getX() <= 30000000) {
                return location.getZ() >= -30000000 && location.getZ() <= 30000000;
            }
        }
        return false;
    }
    public boolean isValidLocationFromString(World world, String x, String y, String z) {
        if(StringParser.DOUBLE.isStringType(x) && StringParser.DOUBLE.isStringType(y) && StringParser.DOUBLE.isStringType(z)) {
            return isValidLocation(new Location(world, Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(z)));
        }
        return false;
    }

    public static void createTrader(Location location /* String category */) {

        location.getWorld().spawnEntity(location, EntityType.WANDERING_TRADER);
    }
}