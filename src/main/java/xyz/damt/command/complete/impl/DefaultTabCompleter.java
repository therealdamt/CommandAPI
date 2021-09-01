package xyz.damt.command.complete.impl;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.damt.command.CommandHandler;
import xyz.damt.command.command.Command;
import xyz.damt.command.complete.TabComplete;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

public class DefaultTabCompleter implements TabComplete {

    @Override
    public List<String> get(Command command, List<Command> subCommands, String[] args) {
        JavaPlugin javaPlugin = CommandHandler.getInstance().getJavaPlugin();
        javaPlugin.getLogger().log(Level.SEVERE, "Default Tab Completion Is not created yet, make your own!");
        return Collections.emptyList();
    }

}
