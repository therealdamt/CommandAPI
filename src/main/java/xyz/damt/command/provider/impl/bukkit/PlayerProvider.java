package xyz.damt.command.provider.impl.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.command.CommandParameter;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerProvider implements CommandProvider<Player> {

    private final JavaPlugin javaPlugin;

    public PlayerProvider(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public Player provide(String s) throws CommandProviderNullException {
        final Player player = javaPlugin.getServer().getPlayer(s);

        if (player == null)
            throw new CommandProviderNullException(ChatColor.RED + "The player you specified is not online or does not exist!");

        return player;
    }

    @Override
    public List<String> suggestions(CommandParameter commandParameter) {
        return javaPlugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }

}
