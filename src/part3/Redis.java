package part3;

import part1.Message;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Redis extends Jedis {

    private static final Redis redis = new Redis();
    private final Jedis jedis;

    /**
     * Constructor that instantiate redis database
     */
    public Redis() {
        jedis = new Jedis("localhost");
    }

    /**
     * Returns redis instance
     * @return redis instance
     */
    public static Redis getInstance() {
        return redis;
    }

    /**
     * Method that save message in redis database
     * @param msg -> message to save
     */
    public void lpushMail(Message msg) {
        jedis.lpush(msg.getReceiver(), msg.toStringRedis());
    }

    /**
     * Method that get all the messages from user in redis database
     * @param username -> username of the specific user
     * @return list of messages of the specific user in redis database structure
     */
    public List<String> lrangeMail(String username) {
        return jedis.lrange(username, 0, -1);
    }

    /**
     * Method that get all database entries and grouped per user
     * @return list of message list per user in redis database structure
     */
    public List<List<String>> allMail() {
        List<List<String>> messageList = new ArrayList<>();
        Set<String> keys = jedis.keys("*");
        keys.forEach(key -> messageList.add(jedis.lrange(key, 0, -1)));

        return messageList;
    }

}
