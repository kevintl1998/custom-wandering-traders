package me.t0c.cwt_v3.commands;

import org.bukkit.entity.Player;

public abstract class Command {
    protected Command(Player player, String[] args) {
        this.player = player;
        this.args = args;
    }

    protected final Player player;
    protected final String[] args;

    public abstract boolean runCommand();

}
