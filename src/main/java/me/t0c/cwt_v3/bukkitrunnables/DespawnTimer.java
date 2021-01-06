package me.t0c.cwt_v3.bukkitrunnables;

import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class DespawnTimer extends BukkitRunnable {

    private final Entity entity;

    public DespawnTimer(Entity entity) {
        this.entity = entity;
        BukkitRunnableCollection.add(this);
    }

    @Override
    public void run() {
        // Marks the entity for removal
        entity.remove();
        BukkitRunnableCollection.remove(this);
    }
}
