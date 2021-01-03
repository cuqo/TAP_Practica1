package part3;

import part1.MailStore;

public class RedisMailStoreFactory implements MailStoreFactory {

    /**
     * Method that generate new instance of RedisMailStore
     * @return new instance of RedisMailStore
     */
    public MailStore createMailStore() {
        Redis redis = Redis.getInstance();
        return new RedisMailStore(redis);
    }
}
