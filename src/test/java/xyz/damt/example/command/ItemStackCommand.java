package xyz.damt.example.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.damt.command.annotation.Command;
import xyz.damt.command.annotation.Name;
import xyz.damt.command.annotation.Permission;
import xyz.damt.command.annotation.Sender;

public class ItemStackCommand {

    @Command(value = "kekw", async = true, usage = "/kekw itemStack", description = "Just makes an itemstack lol")
    @Permission(permission = "kekw.kekew", message = "no perm noob")
    public void itemStackCommand(@Sender Player player, @Name("itemStack") ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
        player.sendMessage(ChatColor.GREEN + "Gave ya your itemstack!");
    }

}
