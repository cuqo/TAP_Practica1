package part2;


import part1.MailStore;
import part1.Message;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class BodyDecorator extends MailStore {

    private MailStore mailStore;

    public BodyDecorator(MailStore mailStore) {
        this.mailStore = mailStore;
    }

    @Override
    public void sendMail(Message msg) {
        StringBuilder output = new StringBuilder(msg.getBody()).reverse();
        msg.setBody(output.toString());
        mailStore.sendMail(msg);
    }

    @Override
    public List<Message> getMail(String username) {
        List<Message> msg = mailStore.getMail(username);
        msg.forEach(message -> {
            StringBuilder output = new StringBuilder(message.getBody()).reverse();
            message.setBody(output.toString());
        });

        return msg;
    }

    @Override
    public List<Message> getAllMessages() {
        return mailStore.getAllMessages();
    }
}
