package xyz.damt.command.provider;

import xyz.damt.command.command.CommandParameter;
import xyz.damt.command.exception.CommandProviderNullException;

import java.util.List;

public interface CommandProvider<T> {

    T provide(String s) throws CommandProviderNullException;

    List<String> suggestions(CommandParameter commandParameter);

}
