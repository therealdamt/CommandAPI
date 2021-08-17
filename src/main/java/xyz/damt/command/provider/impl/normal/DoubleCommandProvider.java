package xyz.damt.command.provider.impl.normal;

import org.bukkit.ChatColor;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

public class DoubleCommandProvider implements CommandProvider<Double> {

    @Override
    public Double provide(String s) throws CommandProviderNullException {
        double value;

        try {
            value = Double.parseDouble(s);
        } catch (NumberFormatException exception) {
            throw new CommandProviderNullException(ChatColor.RED + "Please input a valid doublie value!");
        }

        return value;
    }

}
