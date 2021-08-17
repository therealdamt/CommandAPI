package xyz.damt.command.provider;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import xyz.damt.command.CommandHandler;
import xyz.damt.command.provider.impl.bukkit.OfflinePlayerCommandProvider;
import xyz.damt.command.provider.impl.bukkit.PlayerCommandProvider;
import xyz.damt.command.provider.impl.normal.*;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class CommandProviderHandler {

    private final Map<Class<?>, CommandProvider<?>> commandProviderMap = new HashMap<>();
    private final CommandHandler commandHandler;

    /**
     * Handles all command providers, and insures nothing goes wrong
     *
     * @param commandHandler instance of {@link CommandHandler}
     */

    public CommandProviderHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
        this.init();
    }

    /**
     * Registers all default and spigot providers
     */

    private final void init() {
        this.register(Integer.class, new IntegerCommandProvider());
        this.register(String.class, new StringCommandProvider());
        this.register(Float.class, new FloatCommandProvider());
        this.register(Boolean.class, new BooleanCommandProvider());
        this.register(Double.class, new DoubleCommandProvider());
        this.register(String[].class, new StringArrayCommandProvider());

        this.register(Player.class, new PlayerCommandProvider(commandHandler.getJavaPlugin()));
        this.register(OfflinePlayer.class, new OfflinePlayerCommandProvider(commandHandler.getJavaPlugin()));
    }

    /**
     * Registers a provider
     *
     * @param clazz class of the provider
     * @param commandProvider command provider of the provider
     */

    public final void register(Class<?> clazz, CommandProvider<?> commandProvider) {
        commandProviderMap.put(clazz, commandProvider);
    }

    /**
     * Gets a provider from it's class
     *
     * @param clazz class to get the provider of
     * @return {@link CommandProvider}
     */

    public final CommandProvider<?> getProvider(Class<?> clazz) {
        return commandProviderMap.get(clazz);
    }

    /**
     * Gets a provider from a parameter
     *
     * @param parameter parameter to get the provider from
     * @return {@link CommandProvider}
     */

    public final CommandProvider<?> getProvider(Parameter parameter) {
        return commandProviderMap.get(parameter.getType());
    }

}
