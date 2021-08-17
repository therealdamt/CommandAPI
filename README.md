# CommandAPI
Simple Reflection Command API that just does what you want it to do without any problems.

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
	    <version>1.1.0</version>
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
	        implementation 'com.github.therealdamt:commandapi:1.1.0'
	}
```

### How to use

* Example main class

```java
@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        new CommandHandler(this).bind(ItemStack.class, new ItemStackProvider())
                .register(new FlyCommand(), new ItemStackCommand()).registerCommands();
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

#Example provider

```java
public class ItemStackProvider implements CommandProvider<ItemStack> {

    @Override
    public ItemStack provide(String s) throws CommandProviderNullException {

        if (!s.equalsIgnoreCase("itemStack"))
            throw new CommandProviderNullException(ChatColor.RED + "You need to type 'itemStack' to get this provider!");

        return new ItemStack(Material.DIRT);
    }

}
```

### Credits

You have full rights to use this command api within your projects, but if possible to include credits that'd be amazing!

### Contact

* [Telegram](https://t.me/therealdamt)
* [Website](https://damt.xyz)
* Discord | damt#0886
