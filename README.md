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
	    <version>086ce74d0a</version>
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
	        implementation 'com.github.therealdamt:commandapi:086ce74d0a'
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
public class ItemStackProvider implements CommandProvider<ItemStack> {

    @Override
    public ItemStack provide(String s) throws CommandProviderNullException {

	Material material = Material.getMaterial(s.toUpperCase());

        if (material == null)
            throw new CommandProviderNullException(ChatColor.RED + "It seems like you did not provide a valid material.");

        return new ItemStack(material);
    }

}
```

* Example Help Service

```java
public class HelpService implements HelpCommandService {

    @Override
    public List<String> get(Command command, List<Command> subCommands) {
        return subCommands
		.stream()
		.map(command1 -> command.getUsage() + ", ")
		.collect(Collectors.toList());
    }

}
```

* Example Tab Completer

```java
public class TabCompleter implements TabComplete {

    @Override
    public List<String> get(Command command, List<Command> subCommands, String[] args) {
        return subCommands
		.stream()
		.map(subCommand -> subCommand.getName() + "\n")
		.collect(Collectors.toList());
    }

}
```

### Credits

You have full rights to use this command api within your projects, but if possible to include credits that'd be amazing!

### Contact

* [Telegram](https://t.me/therealdamt)
* [Website](https://damt.xyz)
* Discord | damt#0886
