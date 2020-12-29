package part3;

import part1.MailStore;

public class RedisMailStoreFactory implements MailStoreFactory{
    public MailStore createMailStore() {
        Redis jedis = Redis.getInstance();
        return new RedisMailStore(jedis);
    }
}
