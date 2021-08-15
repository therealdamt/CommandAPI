package xyz.damt.example;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.CommandHandler;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    private CommandHandler commandHandler;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        this.commandHandler = new CommandHandler(this);
    }

}
