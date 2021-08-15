package xyz.damt.command.command;

import lombok.SneakyThrows;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;

public class CommandRegistery {

    private final JavaPlugin javaPlugin;
    private CommandMap commandMap;

    /**
     * The command registery that registers
     * commands into bukkit's api
     *
     * @param javaPlugin plugin to register into
     */

    public CommandRegistery(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.init();
    }

    /**
     * Sets the command map of which will be used further
     * to register the commands
     */

    @SneakyThrows
    private final void init() {
        Field field = javaPlugin.getServer().getClass().getDeclaredField("commandMap");

        field.setAccessible(true);
        this.commandMap = (CommandMap) field.get(javaPlugin.getServer());
    }

    /**
     * Registers a command and inputs it
     * into the command map
     *
     * @param command command to input
     */

    public final void registerCommand(Command command) {
        commandMap.register(command.getName(), command.getCommandExecutor());
    }

    /**
     * Registers multiple commands at once
     * and inputs all into the command map
     *
     * @param commands commands to register
     */

    public final void registerCommands(Command... commands) {
        for (Command command : commands) {
            commandMap.register(command.getName(), command.getCommandExecutor());
        }
    }

}
