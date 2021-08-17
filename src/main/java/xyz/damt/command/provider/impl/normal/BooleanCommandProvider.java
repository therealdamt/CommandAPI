package xyz.damt.command.provider.impl.normal;

import org.bukkit.ChatColor;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

public class BooleanCommandProvider implements CommandProvider<Boolean> {

    @Override
    public Boolean provide(String s) throws CommandProviderNullException {
        if (s.equalsIgnoreCase("true"))
            return true;

        if (s.equalsIgnoreCase("false"))
            return false;

        throw new CommandProviderNullException(ChatColor.RED + "Please input a valid boolean!");
    }


}
