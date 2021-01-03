package part2;

import part1.Message;
import part1.MailStore;

import java.util.List;

public class BodyDecorator implements MailStore {

    private final MailStore mailStore;
    private final Context context1;
    private final Context context2;

    /**
     * BodyDecorator constructor
     * @param mailStore -> current mail store object
     * @param context1 -> context object for the first strategy to cipher the mail's body
     * @param context2 -> context object for the second strategy to cipher the mail's body
     */
    public BodyDecorator(MailStore mailStore, Context context1, Context context2) {
        this.mailStore = mailStore;
        this.context1 = context1;
        this.context2 = context2;
    }

    /**
     * Override method that receive a Message, ciphers its body and sends the message to the mail store
     * @param msg -> Message to save in file
     */
    @Override
    public void sendMail(Message msg) {
        String body = context1.sendMailStrategy(msg.getBody());
        body = context2.sendMailStrategy(body);
        msg.setBody(body);
        mailStore.sendMail(msg);
    }

    /**
     * Override method that filters the messages of an specific user deciphering its body
     * @param username -> username of the specific user
     * @return list of messages of the specific user
     */
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

    /**
     * Override method that catch all messages of the mail store deciphering its body
     * @return list of all messages of the file
     */
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
