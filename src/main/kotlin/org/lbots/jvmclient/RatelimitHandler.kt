package org.lbots.jvmclient

import java.lang.Thread.sleep

object RatelimitHandler {
    private val ratelimits = mapOf<String, MutableList<Int>>()

    fun ratelimit(key: String, requests: Int, seconds: Int, callable: () -> Any): Any {
        val now = System.currentTimeMillis().toInt()
        val entry = ratelimits[key] ?: mutableListOf(0, now)

        if (entry[1] + 1000*seconds >= now) {
            entry[1] = now
            entry[0] = 0
        }

        entry[0]++

        if (entry[0] > requests) {
            val waitMillis = now - entry[1]
            sleep(waitMillis.toLong())
        }

        return callable()
    }
}
