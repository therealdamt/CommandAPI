package xyz.damt.example.help;

import xyz.damt.command.command.Command;
import xyz.damt.command.help.HelpCommandService;

import java.util.List;
import java.util.stream.Collectors;

public class HelpService implements HelpCommandService {

    @Override
    public List<String> get(Command command, List<Command> subCommands) {
        return subCommands.stream().map(command1 -> command.getUsage() + ", ").collect(Collectors.toList());
    }

}
