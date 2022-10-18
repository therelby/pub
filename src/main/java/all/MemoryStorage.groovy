package all

import above.ConfigReader
import io.lettuce.core.RedisClient
import io.lettuce.core.api.StatefulRedisConnection
import io.lettuce.core.api.sync.RedisCommands

class MemoryStorage {

    static RedisClient redisClient
    static StatefulRedisConnection<String, String> connection
    static RedisCommands<String, String> syncCommands


    /** Connection */
    static void connect() {
        if (redisClient) { return }
        run().log 'Connecting to Redis DB...'
        redisClient = RedisClient.create(ConfigReader.get('redisServer'))
        connection = redisClient.connect()
        syncCommands = connection.sync()
    }


    /** Save data */
    static setData(String name, Object data, boolean frameworkInternal = false) {
        if (!name.toLowerCase().startsWith(run().testAuthor) && run().testAuthor != ConfigReader.get('frameworkDebugPerson') && !frameworkInternal) {
            throw new Exception("MemoryStorage: You cannot create variable named [$name], please use ${run().testAuthor}-${name}")
        }
        connect()
        syncCommands.set(name, Json.getJson(data))
    }


    /** Get data */
    static Object getData(String name) {
        if (!name) { return [] }
        try {
            connect()
            return Json.getData(syncCommands.get(name))
        } catch (e) {
            run().log e.getMessage()
            return []
        }
    }


    /** Set String */
    static setString(String name, String text) {
        if (!name) { return }
        connect()
        syncCommands.set(name, text)
    }


    /** Get String */
    static Object getString(String name) {
        if (!name) { return [] }
        connect()
        return syncCommands.get(name)
    }

}
