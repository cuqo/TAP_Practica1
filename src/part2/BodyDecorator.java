package part2;



import part1.MailStore;
import part1.Message;

import java.util.List;

public class BodyDecorator extends MailStore {

    private MailStore mailStore;
    private Context context;

    public BodyDecorator(MailStore mailStore, Context context) {
        this.mailStore = mailStore;
        this.context = context;
    }

    @Override
    public void sendMail(Message msg) {
        String body = context.sendMailStrategy(msg.getBody());
        //System.out.println(body);
        msg.setBody(body);
        mailStore.sendMail(msg);
    }

    @Override
    public List<Message> getMail(String username) {
        List<Message> msgList = mailStore.getMail(username);
        msgList.forEach(message -> {
            //System.out.println(message.getBody());
            String body = context.getMailStrategy(message.getBody());
            message.setBody(body);
        });

        return msgList;
    }

    @Override
    public List<Message> getAllMessages() {
        return mailStore.getAllMessages();
    }
}
