package xyz.damt.command;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.command.Command;
import xyz.damt.command.command.CommandFinder;
import xyz.damt.command.command.CommandRegistery;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CommandHandler {

    @Getter
    private static CommandHandler instance;

    private final Map<String, Command> commandMap = new HashMap<>();
    private final JavaPlugin javaPlugin;
    private final CommandRegistery commandRegistery;

    /**
     * Command Handler that handles the whole
     * registery process of the commands
     *
     * @param javaPlugin {@link JavaPlugin}
     */

    public CommandHandler(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.commandRegistery = new CommandRegistery(javaPlugin);
    }

    /**
     * Gets a command based on it's name
     *
     * @param name {@link String}
     * @return {@link }
     */

    public final Command getCommand(String name) {
        return commandMap.get(name);
    }

    /**
     * Registers a command based on it's name
     *
     * @param object class of the command
     * @param name   name of the command
     * @return {@link CommandHandler}
     */

    public final CommandHandler register(Object object, String name) {
        commandMap.put(name, new CommandFinder(object).getCommand(name));
        return this;
    }

    /**
     * Registers a whole class/finds all commands in a class
     * and registers all
     *
     * @param objects classes to find and register the commands from
     * @return {@link CommandHandler}
     */

    public final CommandHandler register(Object... objects) {
        for (Object object : objects) {
            CommandFinder commandFinder = new CommandFinder(object);
            commandFinder.getCommands().forEach(command -> commandMap.put(command.getName(), command));
        }
        return this;
    }

    /**
     * Inputs and registers all commands
     * That are added to the list
     *
     * @return {@link CommandHandler}
     */

    public final CommandHandler registerCommands() {
        commandMap.values().forEach(command -> commandRegistery.registerCommand(command));
        return this;
    }

}
