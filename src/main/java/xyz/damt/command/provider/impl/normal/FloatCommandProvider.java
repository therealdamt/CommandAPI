package xyz.damt.command.provider.impl.normal;

import org.bukkit.ChatColor;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

public class FloatCommandProvider implements CommandProvider<Float> {

    @Override
    public Float provide(String s) throws CommandProviderNullException {
        float value;

        try {
            value = Float.parseFloat(s);
        } catch (NumberFormatException exception) {
            throw new CommandProviderNullException(ChatColor.RED + "Please input a valid float value!");
        }

        return value;
    }

}
