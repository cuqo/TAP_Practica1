package part3;

import part1.MailStore;
import part1.Message;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RedisMailStore implements MailStore {

    private final Redis redis;

    /**
     * Constructor
     * @param redis -> redis database instance
     */
    public RedisMailStore(Redis redis) {
        this.redis = redis;
    }

    /**
     * Override method that receive a Message and save in redis database
     * @param msg -> Message to save in redis database
     */
    @Override
    public void sendMail(Message msg) {
        redis.lpushMail(msg);
    }

    /**
     * Override method that filter the messages of an specific user
     * @param username -> username of the specific user
     * @return list of messages of the specific user
     */
    @Override
    public List<Message> getMail(String username) {
        List<String> redisMsg = redis.lrangeMail(username);
        List<Message> messageList = new ArrayList<>();
        redisMsg.forEach(str -> {
            String[] msg = str.split(";");


            DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date date = null;
            try {
                date = format.parse(msg[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Message aux = new Message(msg[0], msg[1], date, msg[3], msg[4]);
            messageList.add(aux);
        });
        return messageList;
    }

    /**
     * Override method that catch all messages in redis database
     * @return list of all messages in redis database
     */
    @Override
    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        List<List<String>> redisList = redis.allMail();

        redisList.forEach(key -> key.forEach(str -> {
            String[] msg = str.split(";");

            Date date = new Date(Long.parseLong(msg[2]));
            Message aux = new Message(msg[0], msg[1], date, msg[3], msg[4]);
            messageList.add(aux);
        }));

        return messageList;
    }
}
