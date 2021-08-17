package xyz.damt.command.command;

import lombok.Getter;
import xyz.damt.command.CommandHandler;
import xyz.damt.command.annotation.Name;
import xyz.damt.command.provider.CommandProvider;

import java.lang.reflect.Parameter;

@Getter
public class CommandParameter {

    private final Parameter parameter;
    private final Command command;

    private CommandProvider<?> commandProvider;
    private String name;

    public CommandParameter(Command command, Parameter parameter) {
        this.parameter = parameter;
        this.command = command;

        this.init();
    }

    private final void init() {
        Name name = parameter.getAnnotation(Name.class);

        if (name == null)
            this.name = parameter.getName();
        else this.name = name.value();

        this.commandProvider = CommandHandler.getInstance().getCommandProviderHandler().getProvider(parameter);
    }

}
