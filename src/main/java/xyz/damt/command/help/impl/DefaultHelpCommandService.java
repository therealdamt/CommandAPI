package xyz.damt.command.help.impl;

import org.bukkit.ChatColor;
import xyz.damt.command.command.Command;
import xyz.damt.command.help.HelpCommandService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultHelpCommandService implements HelpCommandService {

    private final String mainColor;
    private final String secondaryColor;

    public DefaultHelpCommandService(String mainColor, String secondaryColor) {
        this.mainColor = mainColor;
        this.secondaryColor = secondaryColor;
    }

    @Override
    public List<String> get(Command command, List<Command> subCommands) {
        final List<String> strings = new ArrayList<>();

        strings.add(mainColor + command.getName() + secondaryColor + "'s Commands");
        subCommands.forEach(command1 -> strings.add(mainColor + command1.getUsage() + secondaryColor + " - " + command1.getDescription()));

        return translate(strings);
    }

    private final List<String> translate(List<String> strings) {
        return strings.stream().map(string -> ChatColor.translateAlternateColorCodes('&', string)).collect(Collectors.toList());
    }

}
