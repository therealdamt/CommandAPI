package xyz.damt.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.command.CommandHandler;
import xyz.damt.command.complete.TabComplete;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.executor.CommandExecutor;
import xyz.damt.command.help.HelpCommandService;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class Command {

    private final String name, usage, description;
    private final String[] aliases;
    private final boolean async;
    private final Method method;
    private final Object object;

    private final CommandExecutor commandExecutor;
    private final HelpCommandService helpCommandService;
    private final TabComplete tabComplete;

    private final List<Command> subCommands = new ArrayList<>();
    private final List<CommandParameter> commandParameters = new ArrayList<>();

    private boolean player;
    private boolean subCommand;
    private String permission, permissionMessage;

    /**
     * Constructs a new instance of {@link Command}
     *
     * @param name        name of the command
     * @param aliases     aliases of the command
     * @param method      method of the command
     * @param description description of the command
     * @param async       if the command should be ran async or not
     */

    public Command(Object object, String name, String[] aliases, Method method, String description, String usage, HelpCommandService helpCommandService, TabComplete tabComplete, boolean async) {
        this.object = object;

        this.name = name;
        this.aliases = aliases;
        this.method = method;
        this.description = description;
        this.async = async;
        this.usage = usage;

        this.helpCommandService = helpCommandService;
        this.tabComplete = tabComplete;
        this.commandExecutor = new CommandExecutor(this);
    }

    /**
     * Gets a sub command
     *
     * @param args arguments
     * @return {@link Command}
     */

    public final Command getSubCommand(String[] args) {
        for (Command command : subCommands) {
            String[] commandArg = command.getName().split(" ");
            String[] original = commandArg;

            List<String> strings = new ArrayList<>(Arrays.asList(commandArg));

            List<String> argsList = new ArrayList<>(Arrays.asList(args));

            strings.removeIf(s -> !argsList.contains(s));
            commandArg = strings.toArray(new String[0]);

            if ((original.length - 1) == commandArg.length && commandArg.length != 0 && commandArg[commandArg.length - 1].equalsIgnoreCase(args[commandArg.length - 1])) {
                return command;
            }
        }

        return null;
    }

    /**
     * Executes the command
     *
     * @param commandSender sender of the command
     * @param args          arguments of the command
     */

    @SneakyThrows
    public void execute(CommandSender commandSender, String[] args, boolean skip) {

        if (async) {
            CommandHandler.getInstance().getJavaPlugin().getServer().getScheduler()
                    .runTaskAsynchronously(CommandHandler.getInstance().getJavaPlugin(), () -> execute(commandSender, args, skip));
            return;
        }

        if (player && !(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.RED + "This is a player command only!");
            return;
        }

        if (permission != null && !commandSender.hasPermission(permission)) {
            commandSender.sendMessage(permissionMessage);
            return;
        }

        if (args.length == 0 && !getSubCommands().isEmpty() && helpCommandService != null) {
            helpCommandService.get(this, getSubCommands()).forEach(commandSender::sendMessage);
            return;
        }

        Command subCommand = getSubCommand(args);

        if (!skip) {
            if (subCommand == null) {
                if (helpCommandService == null) {
                    this.execute(commandSender, args, true);
                    return;
                }

                helpCommandService.get(this, getSubCommands()).forEach(commandSender::sendMessage);
                return;
            }
        }

        if (args.length < commandParameters.size()) {
            commandSender.sendMessage(ChatColor.RED + "Wrong Usage: " + usage);
            return;
        }

        List<Object> objects = new ArrayList<>();
        List<CommandParameter> commandParameters = this.commandParameters;

        Method method = this.method;
        Object object = this.object;

        if (!skip) {
            commandParameters = this.subCommand && subCommand != null ? subCommand.getCommandParameters() : this.commandParameters;
            method = this.subCommand && subCommand != null ? subCommand.getMethod() : this.method;
            object = this.subCommand && subCommand != null ? subCommand.getObject() : this.object;
        }

        if (player) {
            Player player = (Player) commandSender;
            objects.add(player);
        } else {
            objects.add(commandSender);
        }

        for (CommandParameter commandParameter : commandParameters) {
            try {
                if (this.subCommand && subCommand != null && !skip) {
                    String[] subArgs = subCommand.getName().split(" ");
                    objects.add(commandParameter.getCommandProvider().provide(args[commandParameters.indexOf(commandParameter) + (subArgs.length - 1)]));
                } else {
                    objects.add(commandParameter.getCommandProvider().provide(args[commandParameters.indexOf(commandParameter)]));
                }
            } catch (ArrayIndexOutOfBoundsException exception) {
                if (subCommand != null && !skip) {
                    commandSender.sendMessage(ChatColor.RED + "Usage: " + subCommand.getUsage());
                    return;
                }
            } catch (CommandProviderNullException e) {
                if (!commandParameter.isOptional()) {
                    if (e.getMessage() == null) {
                        commandSender.sendMessage(ChatColor.RED + "The argument '" + commandParameter.getName() + "' is null!");
                        return;
                    }

                    commandSender.sendMessage(e.getMessage());
                    return;
                }
            }
        }

        method.invoke(object, objects.toArray());
    }

}
