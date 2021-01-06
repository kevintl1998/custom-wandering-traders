package me.t0c.cwt_v3.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class MessageUtils {

    public static void sendPlayerMessage(Player player, String message) {
        player.sendMessage(ChatColor.AQUA + "[CWT]: " + ChatColor.RESET + message);
    }

    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage(ChatColor.AQUA + "[CWT]: " + ChatColor.RESET + message);
    }

}
