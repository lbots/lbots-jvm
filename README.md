[CircleCI]: https://circleci.com/gh/lbots/lbots-jvm
[CircleCIBadge]: https://circleci.com/gh/lbots/lbots-jvm.svg?style=svg
[What is sharding?]: https://discordapp.com/developers/docs/topics/gateway#sharding
[Gradle]: https://gradle.org
[Kotlin]: https://kotlinlang.org
[Website]: https://lbots.org
[API-documentation]: https://lbots.org/api/docs
[Support Discord]: https://lbots.org/support

<!-- Uncomment this, when the version is available again on CircleCI -->
<!-- [![CircleCIBadge]][CircleCI] -->

# lbots-jvm
A JVM wrapper for the LBots API, written in Kotlin

## Usage
### Gradle
Add the following repository and dependency to your build.gradle's `repositories` and `dependencies` section.
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

### Examples
#### kotlin
##### Creating an instance of the LBotsClient
You first have to create an instance of the LBotsClient, in order to access and use the API.  
The LBotsClient requires your bots ID and the API token of your bots page.

For the API-token, go to https://lbots.org/bots/:id/edit and click on `API Key` to then copy or create and copy a token.  
Please do not share the token with others, nor post it in your bots code, if it's public.
```kotlin
val MY_BOT_ID = 123456L
val MY_LBOTS_TOKEN = "abcdef"

val myClient = LBotsClient(MY_BOT_ID, MY_LBOTS_TOKEN)
```

##### Updating guild count
You can either update the guild count for all shards, or just for one particular shard.

For all shards use this:
```kotlin
val guildCount = 10
myClient.updateStats(guildCount)
```

For one particular shard, you have to provide the shard's ID and the total shard count.  
[What is sharding?]
```kotlin
val shardID = 1
val shardCount = 2
myClient.updateStats(guildCount, shardCount, shardID)
```

##### Getting favorite count of bot
You can use the `favoriteCount()` method to receive the number of people that favorited your bot.
```kotlin
val favoriteAmount: Int = myClient.favoriteCount()
```

##### Check if user favorite bot
You need the ID of the user (as long) to check, if they favorite you bot in the current quarter.  
`second()` may return null, in the event the specified user has not favorited your bot.
```kotlin
val targetUserID = 123123123L
val p = myClient.userFavorited(targetUserID)
val favorited: Boolean = p.first
val favoritedAtTime: String? = p.second
```

##### Invalidate token
If you want to invalidate your API token, you need to use the `invalidate()` method.  
Note that every other action with the LBotsClient after this one won't work as long as you don't create a new instance with a new token.
```kotlin
myClient.invalidate()
```
----

#### Java
##### Creating an instance of the LBotsClient
You first have to create an instance of the LBotsClient, in order to access and use the API.  
The LBotsClient requires your bots ID (as long) and the API token of your bots page (as String).

For the API-token, go to https://lbots.org/bots/:id/edit and click on `API Key` to then copy or create and copy a token.  
Please do not share the token with others, nor post it in your bots code, if it's public.
```java
long MY_BOT_ID = 123456L;
String MY_LBOTS_TOKEN = "abcdef";

LBotsClient myClient = new LBotsClient(MY_BOT_ID, MY_LBOTS_TOKEN);
```

##### Updating guild count
You can either update the guild count for all shards, or just for one particular shard.

For all shards use this:
```java
int guildCount = 10;
myClient.updateStats(guildCount);
```

For one particular shard, you have to provide the shard's ID and the total shard count.  
[What is sharding?]
```java
int guildCount = 2019;
int shardID = 0;
int shardCount = 1;
myClient.updateStats(guildCount, shardCount, shardID);
```

##### Getting favorite count of bot
You can use the `favoriteCount()` method to receive the number of people that favorited your bot.
```java
int favoriteAmount = myClient.favoriteCount();
```

##### Check if user favorite bot
You need the ID of the user (as long) to check, if they favorite you bot in the current quarter.  
`getSecond()` may return null, in the event the specified user has not favorited your bot.
```java
long targetUserID = 123123123L;

Pair p = myClient.userFavorited(targetUserID);

boolean favorited = (Boolean)p.getFirst(); // Did the user favorite your bot?
String favoritedAtTime = (String)p.getSecond(); // When did they favorite it?
```

##### Invalidate token
If you want to invalidate your API token, you need to use the `invalidate()` method.  
Note that every other action with the LBotsClient after this one won't work as long as you don't create a new instance with a new token.
```java
myClient.invalidate();
```

## Built with

* [Gradle] - Dependency Management
* [Kotlin] - Do more with less code

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the BSD 3-Clause License - see the [LICENSE](LICENSE) file for details

## Additional links
* [Website]
* [API-documentation]
* [Support Discord]

