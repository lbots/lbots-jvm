package org.lbots.jvmclient

import okhttp3.*

import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.CompletableFuture

class LBotsNonblockingClient(botID: Long, private val token: String) {
    private val BASE_URL = "https://lbots.org/api/v1"
    private val base = "$BASE_URL/bots/$botID"

    private val client = OkHttpClient()
    private val ratelimiter = RatelimitHandler()

    private fun build(method: String, url: String, post_data: Map<String, Int>?): Request {
        var body: RequestBody? = null

        if (!post_data.isNullOrEmpty()) {
            val json = MediaType.parse("application/json; charset=utf-8")
            body = RequestBody.create(json, JSONObject(post_data.toMap()).toString())
        }

        return Request.Builder().apply {
            addHeader("Content-Type", "application/json")
            addHeader("Authorization", token)
            url(url)
            method(method, body)
        }.build()
    }

    private fun request(method: String, url: String, post_data: Map<String, Int>? = null): CompletableFuture<JSONObject> {
        val response = client.newCall(build(method, url, post_data))

        val fut = CompletableFuture<JSONObject>()

        response.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val status = response.code()

                if (200 > status || 300 <= status){
                    response.close()
                    throw HTTPException("Unexpected response code: $status")
                }

                val content = response.body()!!.string()
                response.close()

                fut.complete(JSONObject(content))
            }
        })

        return fut
    }

    fun invalidate(): CompletableFuture<Boolean> {
        val futt = CompletableFuture<Boolean>()

        val fut = request("GET", "$base/invalidate")
        fut.thenAccept {
            futt.complete(it.getBoolean("success"))
        }

        return futt
    }

    fun updateStats(guildCount: Int) = updateStats(guildCount, 1, 0)

    fun updateStats(guildCount: Int, shardCount: Int, shardID: Int): CompletableFuture<Boolean> {
        val futt = CompletableFuture<Boolean>()

        val data = mutableMapOf("guild_count" to guildCount)

        if (shardCount > 1){
            data["shard_count"] = shardCount
            data["shard_id"] = shardID
        }

        ratelimiter.ratelimit("/stats", 20, 5) {
            val fut = request("POST", "$base/stats", data.toMap())
            fut.thenAccept {
                futt.complete(it.getBoolean("success"))
            }
        }

        return futt
    }

    fun favoriteCount(): CompletableFuture<Int> {
        val futt = CompletableFuture<Int>()

        ratelimiter.ratelimit("/favorites", 3, 4) {
            val fut = request("GET", "$base/favorites")
            fut.thenAccept {
                futt.complete(it.getInt("favorites"))
            }
        }

        return futt
    }

    fun userFavorited(userID: Long): CompletableFuture<Pair<Boolean, String?>> {
        val futt = CompletableFuture<Pair<Boolean, String?>>()

        val fut = request("GET", "$base/favorites/user/$userID")

        fut.thenAccept {
            var x: String? = null
            if (!it.isNull("time")) {
                x = it.getString("time")
            }
            futt.complete(Pair(it.getBoolean("favorited"), x))
        }

        return futt
    }
}
