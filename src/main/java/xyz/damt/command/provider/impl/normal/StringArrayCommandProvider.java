package xyz.damt.command.provider.impl.normal;

import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

public class StringArrayCommandProvider implements CommandProvider<String[]> {

    @Override
    public String[] provide(String s) throws CommandProviderNullException {
        return s.split("\\s+");
    }

}
