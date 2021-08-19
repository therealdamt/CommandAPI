package xyz.damt.command.complete;

import xyz.damt.command.command.Command;

import java.util.List;

public interface TabComplete {

    List<String> get(Command command, List<Command> subCommands, String[] args);

}
