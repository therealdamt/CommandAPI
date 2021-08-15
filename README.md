# CommandAPI
Simple Reflection Command API that just does what you want it to do without any problems.

* Currently not tested, please do not use unless this read me is changed.

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
	    <version>1.0</version>
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
	        implementation 'com.github.therealdamt:commandapi:Tag'
	}
```

### How to use

* Example main class

```java
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
        commandHandler.register(new FlyCommand());
    }

}
```

* Example command class

```
public class FlyCommand {

    /**
     * So this is a simple command example
     * There are 2 rules to every command:
     * <p>
     * 1- Must have a sender could be a player or a command sender
     * 2- Must have an array of strings
     *
     * @param player sender
     * @param args   arguments to execute with
     */

    @Command(value = "fly", description = "Fly Command", usage = "fly")
    @Permission(permission = "command.fly", message = "You may not use this command!")
    public void flyCommand(@Sender Player player, String[] args) {
        player.setAllowFlight(!player.getAllowFlight());

        String isFlying = player.getAllowFlight() ? "&aenabled" : "&cdisabled";
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYour flying is now " + isFlying));
    }

}
```

### Credits

You have full rights to use this command api within your projects, but if possible to include credits that'd be amazing!

### Contact

* [Telegram](https://t.me/therealdamt)
* [Website](https://damt.xyz)
* Discord | damt#0886
