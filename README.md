[![CircleCI](https://circleci.com/gh/lbots/lbots-jvm.svg?style=svg)](https://circleci.com/gh/lbots/lbots-jvm)
# lbots-jvm
A JVM wrapper for the LBots API, written in Kotlin

## usage
### Gradle
```gradle
epositories {
			...
			maven { url 'https://jitpack.io' }
		}
		
dependencies {
	        implementation 'com.github.lbots:master-SNAPSHOT'
	}
```

### example
```kotlin
val lbots = LBotsClient(your bot id, "your-api-key"
// get favorite count
lbots.favoriteCount()
//check if favorited
lbots.userFavorited(some user id)
// update stats
lbots.updateStats(guild-count)
// update stats with shards
lbots.updateStats(guild-count, shard-count, shard-id)
```
## Built With

* [Gradle](https://gradle.org/) - Dependency Management
* [Kotlin](https://kotlinlang.org/) - Do more with less code

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## License

This project is licensed under the BSD 3-Clause License - see the [LICENSE](LICENSE) file for details

