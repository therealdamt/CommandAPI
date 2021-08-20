package xyz.damt.example.completer;

import xyz.damt.command.command.Command;
import xyz.damt.command.complete.TabComplete;

import java.util.List;
import java.util.stream.Collectors;

public class TabCompleter implements TabComplete {

    @Override
    public List<String> get(Command command, List<Command> subCommands, String[] args) {
        return subCommands.stream().map(subCommand -> subCommand.getName() + "\n").collect(Collectors.toList());
    }

}
