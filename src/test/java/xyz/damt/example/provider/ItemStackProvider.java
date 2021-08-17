package xyz.damt.example.provider;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

public class ItemStackProvider implements CommandProvider<ItemStack> {

    @Override
    public ItemStack provide(String s) throws CommandProviderNullException {

        if (!s.equalsIgnoreCase("itemStack"))
            throw new CommandProviderNullException(ChatColor.RED + "You need to type 'itemStack' to get this provider!");

        return new ItemStack(Material.DIRT);
    }

}
