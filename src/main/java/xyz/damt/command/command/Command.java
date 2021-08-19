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
import java.util.LinkedList;
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
     * Executes the command
     *
     * @param commandSender sender of the command
     * @param args          arguments of the command
     */

    @SneakyThrows
    public void execute(CommandSender commandSender, String[] args) {

        if (async) {
            CommandHandler.getInstance().getJavaPlugin().getServer().getScheduler()
                    .runTaskAsynchronously(CommandHandler.getInstance().getJavaPlugin(), () -> execute(commandSender, args));
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

        if (args.length < commandParameters.size()) {
            commandSender.sendMessage(ChatColor.RED + "Wrong Usage: " + usage);
            return;
        }

        List<Object> objects = new LinkedList<>();

        for (CommandParameter commandParameter : commandParameters) {
            try {
                objects.add(commandParameter.getCommandProvider().provide(args[commandParameters.indexOf(commandParameter)]));
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

        if (player) {
            Player player = (Player) commandSender;
            method.invoke(object, player, objects.toArray());
        } else {
            method.invoke(object, commandSender, objects.toArray());
        }
    }

}
