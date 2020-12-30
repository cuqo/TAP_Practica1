package part2;

import part1.Message;
import part1.MailStore;

import java.util.List;

public class BodyDecorator implements MailStore {

    private final MailStore mailStore;
    private final Context context1;
    private final Context context2;

    public BodyDecorator(MailStore mailStore, Context context1, Context context2) {
        this.mailStore = mailStore;
        this.context1 = context1;
        this.context2 = context2;
    }

    @Override
    public void sendMail(Message msg) {
        String body = context1.sendMailStrategy(msg.getBody());
        body = context2.sendMailStrategy(body);
        msg.setBody(body);
        mailStore.sendMail(msg);
    }

    @Override
    public List<Message> getMail(String username) {
        List<Message> msgList = mailStore.getMail(username);
        msgList.forEach(message -> {
            String body = context2.getMailStrategy(message.getBody());
            body = context1.getMailStrategy(body);
            message.setBody(body);
        });

        return msgList;
    }

    @Override
    public List<Message> getAllMessages() {
        List<Message> msgList = mailStore.getAllMessages();
        msgList.forEach(message -> {
            String body = context2.getMailStrategy(message.getBody());
            body = context1.getMailStrategy(body);
            message.setBody(body);
        });

        return msgList;
    }
}
