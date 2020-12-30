package part3;

import part1.Message;
import redis.clients.jedis.Jedis;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Redis extends Jedis {

    private static final Redis redis = new Redis();
    private final Jedis jedis;

    public Redis() {
        jedis = new Jedis("localhost");
    }

    public static Redis getInstance() {
        return redis;
    }

    public void lpushMail(Message msg) {
        jedis.lpush(msg.getReceiver(), msg.toStringRedis());
    }

    public List<String> lrangeMail(String username) {
        return jedis.lrange(username, 0, -1);
    }

    public List<List<String>> allMail() {
        List<List<String>> messageList = new ArrayList<>();
        Set<String> keys = jedis.keys("*");
        keys.forEach(key -> messageList.add(jedis.lrange(key, 0, -1)));

        return messageList;
    }

}
