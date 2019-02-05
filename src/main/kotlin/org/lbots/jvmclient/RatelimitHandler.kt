package org.lbots.jvmclient

import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.concurrent.schedule


class RatelimitHandler {
    private val ratelimits = mutableMapOf<String, Pair<Int, Long>>()

    fun ratelimit(key: String, requests: Int, seconds: Int, callable: () -> Any): Any {
        val now = System.currentTimeMillis()
        var entry = ratelimits[key] ?: Pair(0, now)

        if (entry.second + 1000*seconds >= now) {
            entry = Pair(0, now)
        }

        entry = Pair(entry.first + 1, entry.second)

        if (entry.first > requests) {
            val waitMillis = now - entry.second
            sleep(waitMillis)
        }

        ratelimits[key] = entry

        return callable()
    }

    fun ratelimitNonblocking(key: String, requests: Int, seconds: Int, callable: () -> Any): CompletableFuture<Any> {
        val fut = CompletableFuture<Any>()

        val now = System.currentTimeMillis()
        var entry = ratelimits[key] ?: Pair(0, now)

        if (entry.second + 1000*seconds >= now) {
            entry = Pair(0, now)
        }

        entry = Pair(entry.first + 1, entry.second)

        ratelimits[key] = entry

        if (entry.first > requests) {
            val waitMillis = now - entry.second
            Timer().schedule(waitMillis) {
                fut.complete(callable())
            }
        }

        return fut
    }
}
