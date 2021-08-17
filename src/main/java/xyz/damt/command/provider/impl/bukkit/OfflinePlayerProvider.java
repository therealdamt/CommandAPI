package xyz.damt.command.provider.impl.bukkit;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.command.CommandParameter;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

import java.util.List;
import java.util.stream.Collectors;

public class OfflinePlayerProvider implements CommandProvider<OfflinePlayer> {

    private final JavaPlugin javaPlugin;

    public OfflinePlayerProvider(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public OfflinePlayer provide(String s) throws CommandProviderNullException {
        final OfflinePlayer player = javaPlugin.getServer().getOfflinePlayer(s);

        if (!player.hasPlayedBefore() || player == null)
            throw new CommandProviderNullException(ChatColor.RED + "The player you specified never logged on the server before!");

        return player;
    }

    @Override
    public List<String> suggestions(CommandParameter commandParameter) {
        return javaPlugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
    }

}
