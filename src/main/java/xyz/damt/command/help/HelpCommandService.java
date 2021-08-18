package xyz.damt.command.help;

import xyz.damt.command.command.Command;

import java.util.List;

public interface HelpCommandService {

    List<String> get(Command command, List<Command> subCommands);

}
