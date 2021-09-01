package xyz.damt.command.executor;

import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import xyz.damt.command.command.Command;
import xyz.damt.command.command.CommandParameter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandExecutor extends BukkitCommand {

    private final Command command;

    /**
     * Command executor that executes a command from
     * The command api
     *
     * @param command command to execute
     */

    public CommandExecutor(Command command) {
        super(command.getName());

        setAliases(Arrays.asList(command.getAliases()));
        setDescription(command.getDescription());


        this.command = command;
    }

    /**
     * Executes the command
     *
     * @param commandSender sender of the command
     * @param s             label of the command
     * @param strings       arguments of the command
     * @return {@link Boolean}
     */

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        command.execute(commandSender, strings, false);
        return false;
    }

    /**
     * Completes or auto-completes a command
     * or suggests the rest of the command to
     * a sender
     *
     * @param sender sender of the command
     * @param alias  aliases of the command
     * @param args   arguments of the command
     * @return {@link List<String>}
     * @throws IllegalArgumentException
     */

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        if (command.getSubCommands().isEmpty())
            return Collections.emptyList();

        CommandParameter commandParameter = command.getCommandParameters().get(args.length - 1);

        if (commandParameter == null) {
            if (command.getTabComplete() == null)
                return Collections.emptyList();

            return command.getTabComplete().get(command, command.getSubCommands(), args);
        }

        return commandParameter.getCommandProvider().suggestions(commandParameter);
    }

}
