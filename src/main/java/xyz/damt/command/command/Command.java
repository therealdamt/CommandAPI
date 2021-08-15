package xyz.damt.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.damt.command.CommandHandler;
import xyz.damt.command.executor.CommandExecutor;

import java.lang.reflect.Method;
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

    private final CommandExecutor commandExecutor;
    private final List<Command> subCommands = new LinkedList<>();

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

    public Command(String name, String[] aliases, Method method, String description, String usage, boolean async) {
        this.name = name;
        this.aliases = aliases;
        this.method = method;
        this.description = description;
        this.async = async;
        this.usage = usage;

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

        if (player) {
            Player player = (Player) commandSender;
            method.invoke(this, player, args);
        } else {
            method.invoke(this, commandSender, args);
        }
    }

}
