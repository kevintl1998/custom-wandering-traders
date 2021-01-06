package me.t0c.cwt_v3.listeners;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.bukkitRunnables.DespawnTimer;
import me.t0c.cwt_v3.config.Config;
import me.t0c.cwt_v3.utils.MessageUtils;
import me.t0c.cwt_v3.utils.traders.TradeListGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class WanderingTraderListener implements Listener {

    public WanderingTraderListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTraderSpawn(CreatureSpawnEvent event) {
        if(event.getEntity().getType().equals(EntityType.WANDERING_TRADER)) {
            if(event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL) || event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.CUSTOM)) {

                WanderingTrader trader = (WanderingTrader) event.getEntity();

                List<MerchantRecipe> recipes = new TradeListGenerator(trader).generateTrades();
                if(recipes != null) trader.setRecipes(recipes);
                else CWT_v3.instance.getLogger().info("No custom trades were found.");

                if(Config.getDespawnTimer() != 0) {
                    new DespawnTimer(event.getEntity()).runTaskLater(CWT_v3.instance, 20L * 60 * Config.getDespawnTimer());
                }
                if(Config.getBroadcastTraderSpawn() && event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
                    if(Config.getLeakTraderCoords())
                        Bukkit.broadcastMessage(ChatColor.AQUA + "[CWT]: " + ChatColor.RESET + " A new wandering trader has spawned at " + ChatColor.AQUA +
                                (int)event.getEntity().getLocation().getX() + " " +
                                (int)event.getEntity().getLocation().getY() + " " +
                                (int)event.getEntity().getLocation().getZ());

                    else { MessageUtils.broadcastMessage("A new wandering trader has spawned!"); }
                }
            }
        }
    }
}