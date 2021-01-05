package part3.junitTests;

import org.junit.Before;
import org.junit.Test;
import part1.MailStore;
import part1.Message;
import part3.Redis;
import part3.RedisMailStore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MailStoreUnitTest {

    Redis redis;
    MailStore redisMailStore;
    List<Message> messages;

    @Before
    public void setup() {
        redis = Redis.getInstance();
        redisMailStore = new RedisMailStore(redis);
        messages = new ArrayList<>();
        messages.add(new Message("user1", "user2", "hola Joan", "Hola que tal?"));
        messages.add(new Message("user2", "user3", "hola Maria", "Ei que passa?"));
        messages.add(new Message("user1", "user2", "hola Joan", "No m'has contestat"));
        messages.add(new Message("user2", "user1", "TFG", "Perque m'has suspes?"));
        messages.add(new Message("user1", "user3", "Cita", "Hola vull quedar amb tu"));
        messages.add(new Message("user3", "user2", "RW: Cita", "Doncs jo no"));
        messages.add(new Message("user1", "user3", "Consulta", "Tens covid?"));
        messages.forEach(message -> redisMailStore.sendMail(message));
    }

    @Test
    public void testGetMailRedisMailStore() {
        System.out.println("testGetMailRedisMailStore");

        List<Message> test = redisMailStore.getMail("user2");
        List<Message> auxList = new ArrayList<>();
        auxList.add(messages.get(0));
        auxList.add(messages.get(2));
        auxList.add(messages.get(5));
        assertEquals(auxList, test);
    }

    @Test
    public void testGetAllMessagesRedisMailStore() {
        System.out.println("testGetAllMessagesRedisMailStore");

        List<Message> test = redisMailStore.getAllMessages();
        assertEquals(messages.toString(), test.toString());
    }

    @Test
    public void testGetLrangeMail() {
        System.out.println("testGetAllMessagesFileMailStore");

        List<String> test = redis.lrangeMail("user2");
        List<String> auxList = new ArrayList<>();
        auxList.add(messages.get(0).toStringRedis());
        auxList.add(messages.get(2).toStringRedis());
        auxList.add(messages.get(5).toStringRedis());
        assertEquals(messages.toString(), test.toString());
    }
}
