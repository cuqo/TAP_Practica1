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

    public RedisMailStore(Redis redis) {
        this.redis = redis;
    }

    @Override
    public void sendMail(Message msg) {
        redis.lpushMail(msg);
    }

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

    @Override
    public List<Message> getAllMessages() {
        List<Message> messageList = new ArrayList<>();
        List<List<String>> redisList = redis.allMail();

        redisList.forEach(key -> key.forEach(str -> {
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
        }));

        return messageList;
    }
}
