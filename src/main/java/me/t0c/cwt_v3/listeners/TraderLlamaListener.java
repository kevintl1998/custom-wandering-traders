package me.t0c.cwt_v3.listeners;

import me.t0c.cwt_v3.CWT_v3;
import me.t0c.cwt_v3.bukkitrunnables.DespawnTimer;
import me.t0c.cwt_v3.config.Config;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class TraderLlamaListener implements Listener {

    public TraderLlamaListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onTraderLlamaSpawn(CreatureSpawnEvent event) {
        if(event.getEntityType().equals(EntityType.TRADER_LLAMA)) {
            if(Config.getRemoveLlamasOnTraderDespawn() && Config.getDespawnTimer() != 0) {
                new DespawnTimer(event.getEntity()).runTaskLater(CWT_v3.instance, 20L * 60 * Config.getDespawnTimer());
            }
        }
    }
}