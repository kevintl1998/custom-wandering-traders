package me.t0c.cwt_v3.bukkitRunnables;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class BukkitRunnableCollection {

    private static List<BukkitRunnable> toRunOnClose = new ArrayList<>();

    public static void runAll(){
        for (int i = toRunOnClose.size() - 1; i > -1; i--) {
            toRunOnClose.get(i).run();
        }
        toRunOnClose.clear();
    }

    public static void remove(BukkitRunnable runnable) {
        toRunOnClose.remove(runnable);
    }
    public static void add(BukkitRunnable runnable){
        toRunOnClose.add(runnable);
    }
}
