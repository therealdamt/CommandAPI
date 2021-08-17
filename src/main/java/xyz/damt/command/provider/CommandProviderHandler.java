package xyz.damt.command.provider;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class CommandProviderHandler {

    private final Map<Class<?>, CommandProvider<?>> commandProviderMap = new HashMap<>();

    public final void register(Class<?> clazz, CommandProvider<?> commandProvider) {
        commandProviderMap.put(clazz, commandProvider);
    }

    public final CommandProvider<?> getProvider(Class<?> clazz) {
        return commandProviderMap.get(clazz);
    }

    public final CommandProvider<?> getProvider(Parameter parameter) {
        return commandProviderMap.get(parameter.getType());
    }

}
