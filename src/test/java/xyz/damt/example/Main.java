package xyz.damt.example;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.CommandHandler;
import xyz.damt.command.complete.impl.DefaultTabCompleter;
import xyz.damt.command.help.impl.DefaultHelpCommandService;
import xyz.damt.example.command.FlyCommand;
import xyz.damt.example.command.ItemStackCommand;
import xyz.damt.example.completer.TabCompleter;
import xyz.damt.example.help.HelpService;
import xyz.damt.example.provider.ItemStackProvider;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        new CommandHandler(this).bind(ItemStack.class, new ItemStackProvider())
                .register(new FlyCommand(), new ItemStackCommand()).helpService(new HelpService())
                .tabComplete(new TabCompleter()).registerCommands();
    }

}
