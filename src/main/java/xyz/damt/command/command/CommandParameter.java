package xyz.damt.command.command;

import lombok.Getter;
import lombok.SneakyThrows;
import xyz.damt.command.annotation.Name;
import xyz.damt.command.annotation.Optional;
import xyz.damt.command.exception.CommandParameterException;
import xyz.damt.command.provider.CommandProvider;
import xyz.damt.command.provider.CommandProviderHandler;

import java.lang.reflect.Parameter;

@Getter
public class CommandParameter {

    private final Parameter parameter;
    private final Command command;
    private final CommandProviderHandler commandProviderHandler;

    private CommandProvider<?> commandProvider;
    private boolean optional, text;
    private String name;

    @SneakyThrows
    public CommandParameter(Command command, Parameter parameter, CommandProviderHandler commandProviderHandler) {
        this.parameter = parameter;
        this.command = command;
        this.commandProviderHandler = commandProviderHandler;

        this.init();
    }

    private final void init() throws CommandParameterException {
        Name name = parameter.getAnnotation(Name.class);

        if (name == null)
            this.name = parameter.getName();
        else this.name = name.value();

        Optional optional = parameter.getAnnotation(Optional.class);

        if (optional != null) {
            this.optional = true;
            this.name = optional.value();
        }

        this.commandProvider = commandProviderHandler.getProvider(parameter);

        if (commandProvider == null)
            throw new CommandParameterException("The command parameter '" + parameter.getType().getSimpleName() + "' for command " + command
                    .getName() + " does not exist!");
    }

}
