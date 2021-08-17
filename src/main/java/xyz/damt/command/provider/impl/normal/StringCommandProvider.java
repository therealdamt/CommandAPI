package xyz.damt.command.provider.impl.normal;

import org.bukkit.ChatColor;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

public class StringCommandProvider implements CommandProvider<String> {

    @Override
    public String provide(String s) throws CommandProviderNullException {

        if (s == null)
            throw new CommandProviderNullException(ChatColor.RED + "The string put is null!");

        return s;
    }

}
