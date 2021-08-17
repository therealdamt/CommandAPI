package xyz.damt.command.provider.impl.normal;

import org.bukkit.ChatColor;
import xyz.damt.command.command.CommandParameter;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

import java.util.Collections;
import java.util.List;

public class IntegerCommandProvider implements CommandProvider<Integer> {

    @Override
    public Integer provide(String s) throws CommandProviderNullException {
        int i;

        try {
            i = Integer.parseInt(s);
        } catch (NumberFormatException exception) {
            throw new CommandProviderNullException(ChatColor.RED + "The integer you provided is invalid!");
        }

        return i;
    }

    @Override
    public List<String> suggestions(CommandParameter commandParameter) {
        return Collections.emptyList();
    }

}
