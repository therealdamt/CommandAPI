# CommandAPI
Annotation/Reflection Based Command API that just does what you want it to do without any problems.

### Importing

* Maven

```xml
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository
```

```xml
	<dependency>
	    <groupId>com.github.therealdamt</groupId>
	    <artifactId>commandapi</artifactId>
	    <version>aa9298fe3e</version>
	    <scope>provided</scope>
	</dependency>
```

* Gradle

```gradle
  repositories {
	        ...
	        maven { url 'https://jitpack.io' }
	}
```

```gradle
  dependencies {
	        implementation 'com.github.therealdamt:commandapi:aa9298fe3e'
	}
```

### How to use

* Example main class

```java
public class Main extends JavaPlugin {
    @Override
    public void onEnable() { // You also have the default tab completer, and the default help service, new DefaultTabCompleter(); 
        new CommandHandler(this).bind(ItemStack.class, new ItemStackProvider())
                .register(new FlyCommand(), new ItemStackCommand()).helpService(new HelpService())
                .tabComplete(new TabCompleter()).registerCommands();
    }

}
```

* Example command class

```java
public class ItemStackCommand {

    @Command(value = "kekw", async = true, usage = "/kekw itemStack", description = "Just makes an itemstack lol")
    @Permission(permission = "kekw.kekew", message = "no perm noob")
    public void itemStackCommand(@Sender Player player, @Name("itemStack") ItemStack itemStack) {
        player.getInventory().addItem(itemStack);
        player.sendMessage(ChatColor.GREEN + "Gave ya your itemstack!");
    }

}
```

* Example provider

```java
public class PlayerCommandProvider implements CommandProvider<Player> {

    private final JavaPlugin javaPlugin;

    public PlayerCommandProvider(JavaPlugin javaPlugin) {
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
```

* Example Help Service

```java
public class HelpService implements HelpCommandService {

    @Override
    public List<String> get(Command command, List<Command> subCommands) {
        return subCommands.stream().map(command1 -> command.getUsage() + ", ").collect(Collectors.toList());
    }

}
```

* Example Tab Completer

```java
public class TabCompleter implements TabComplete {

    @Override
    public List<String> get(Command command, List<Command> subCommands, String[] args) {
        return subCommands.stream().map(subCommand -> subCommand.getName() + "\n").collect(Collectors.toList());
    }

}
```

### Credits

You have full rights to use this command api within your projects, but if possible to include credits that'd be amazing!

### Contact

* [Telegram](https://t.me/therealdamt)
* [Website](https://damt.xyz)
* Discord | damt#0886
