package xyz.damt.command.complete.impl;

import xyz.damt.command.command.Command;
import xyz.damt.command.complete.TabComplete;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultTabCompleter implements TabComplete {

    @Override
    public List<String> get(Command command, List<Command> subCommands, String[] args) {
        final List<String> strings = subCommands.stream().map(Command::getName).collect(Collectors.toList());

        for (String string : strings) {
            final String[] stringArgs = string.split("\\s+");
            strings.remove(string);

            for (int i = args.length; i < stringArgs.length; i++) {
                strings.add(stringArgs[i]);
            }
        }

        return strings;
    }

}
