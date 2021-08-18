package xyz.damt.command.provider.impl.normal;

import org.bukkit.ChatColor;
import xyz.damt.command.exception.CommandProviderNullException;
import xyz.damt.command.provider.CommandProvider;

import java.util.UUID;

public class UUIDCommandProvider implements CommandProvider<UUID> {

    @Override
    public UUID provide(String s) throws CommandProviderNullException {
        UUID uuid;

        try {
            uuid = UUID.fromString(s);
        } catch (Exception exception) {
            throw new CommandProviderNullException(ChatColor.RED + "The UUID you have inputted is not valid!");
        }

        return uuid;
    }

}
