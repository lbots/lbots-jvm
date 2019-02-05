[![CircleCI](https://circleci.com/gh/lbots/lbots-jvm.svg?style=svg)](https://circleci.com/gh/lbots/lbots-jvm)
# lbots-jvm
A JVM wrapper for the LBots API, written in Kotlin

## Usage
### Gradle
```gradle
repositories {
    maven { url 'https://jitpack.io' }
}
		
dependencies {
    implementation 'com.github.lbots:master-SNAPSHOT'
}
```

### Blocking or Non-blocking

In most applications that run synchronously, you'll want to use the `LBotsClient` class.
However, when integrating this directly into a bot, one should use the `LBotsNonblockingClient` instead.
This class returns everything in a `CompletableFuture` instead.

### Example
#### kotlin
```kotlin
val MY_BOT_ID = 123456L
val MY_LBOTS_TOKEN = "abcdef"

val myClient = LBotsClient(MY_BOT_ID, MY_LBOTS_TOKEN)

// Update your stats all at once...
val guildCount = 10
myClient.updateStats(guildCount)

// ...or per shard.
val shardID = 1
val shardCount = 2
myClient.updateStats(guildCount, shardCount, shardID)

// Get the amount of favorites your bot has
val favoriteAmount: Int = myClient.favoriteCount()

// Check if a user favorited your bot
val targetUserID = 123123123L
val p = myClient.userFavorited(targetUserID)
val favorited: Boolean = p.first
val favoritedAtTime: String? = p.second

// Invalidate your API token, in case it leaked.
myClient.invalidate()
```

#### Java
```java
long MY_BOT_ID = 123456L;
String MY_LBOTS_TOKEN = "abcdef";
LBotsClient myClient = new LBotsClient(MY_BOT_ID, MY_LBOTS_TOKEN);
int guildCount = 10;
myClient.updateStats(guildCount)
int shardID = 1;
int shardCount = 2;
myClient.updateStats(guildCount, shardCount, shardID);
int favoriteAmount = myClient.favoriteCount();
long targetUserID = 123123123L;
Pair p = myClient.userFavorited(targetUserID);
boolean favorited = (Boolean)p.getFirst();
String favoritedAtTime = (String)p.getSecond();
myClient.invalidate();
```

## Built with

* [Gradle](https://gradle.org/) - Dependency Management
* [Kotlin](https://kotlinlang.org/) - Do more with less code

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the BSD 3-Clause License - see the [LICENSE](LICENSE) file for details

