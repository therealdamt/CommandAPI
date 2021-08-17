package xyz.damt.example.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import xyz.damt.command.annotation.Command;
import xyz.damt.command.annotation.Permission;
import xyz.damt.command.annotation.Sender;

public class FlyCommand {

    /**
     * So this is a simple command example
     * There are 2 rules to every command:
     * <p>
     * 1- Must have a sender could be a player or a command sender
     * 2- Must have an array of strings
     *
     * @param player sender
     * @param args   arguments to execute with
     */

    @Command(value = "fly", description = "Fly Command", usage = "fly")
    @Permission(permission = "command.fly", message = "You may not use this command!")
    public void flyCommand(@Sender Player player) {
        player.setAllowFlight(!player.getAllowFlight());

        String isFlying = player.getAllowFlight() ? "&aenabled" : "&cdisabled";
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYour flying is now " + isFlying));
    }

}
