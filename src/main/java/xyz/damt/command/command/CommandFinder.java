package xyz.damt.command.command;

import org.bukkit.entity.Player;
import xyz.damt.command.annotation.Permission;
import xyz.damt.command.annotation.Sender;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandFinder {

    private final Object object;
    private final List<Command> commands = new ArrayList<>();

    /**
     * Constructs a new instance of {@link CommandFinder}
     * And finds all commands inside of a class
     *
     * @param object {@link Object}
     */

    public CommandFinder(Object object) {
        this.object = object;

        this.init();
        this.edit();
    }

    /**
     * Gets one command based on it's name
     *
     * @param name name of the command
     * @return {@link Command}
     */

    public final Command getCommand(String name) {
        return commands.stream().filter(command -> command.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    /**
     * Gets all commands inside of the class
     *
     * @return {@link List<Command>}*
     */

    public final List<Command> getCommands() {
        return commands;
    }

    /**
     * Gets all the sub commands of a command
     *
     * @param name name of the command
     * @return {@link List<Command>}
     */

    public final List<Command> getCommands(String name) {
        return commands.stream().filter(command -> command.getName().split("\\s+")
                [0].equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    /**
     * Adds all sub commands to a command
     */

    private final void edit() {
        commands.forEach(command -> {
            String[] args = command.getName().split("\\s+");

            if (args.length > 0)
                return;

            List<Command> subCommands = getCommands(args[0]);
            command.getSubCommands().addAll(subCommands);
        });
    }

    /**
     * Creates and finds all sub commands
     */

    private final void init() {
        final Class<?> clazz = object.getClass();

        for (Method method : clazz.getMethods()) {
            method.setAccessible(true);

            xyz.damt.command.annotation.Command commandAnnotation = method.getAnnotation(xyz.damt.command.annotation.Command.class);

            if (commandAnnotation != null) {
                Permission permissionAnnotation = method.getAnnotation(Permission.class);
                Command command = new Command(commandAnnotation.value(), commandAnnotation.aliases(), method, commandAnnotation.description(), commandAnnotation.usage(), commandAnnotation.async());

                if (permissionAnnotation != null) {
                    command.setPermission(permissionAnnotation.permission());
                    command.setPermissionMessage(permissionAnnotation.message());
                }

                for (Parameter parameter : method.getParameters()) {
                    Sender senderAnnotation = parameter.getAnnotation(Sender.class);

                    if (senderAnnotation != null) {
                        command.setPlayer(parameter.getType().equals(Player.class));
                    }
                }

                commands.add(command);
            }
        }
    }
}
