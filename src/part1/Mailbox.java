package part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Mailbox implements UserMailingOperations{
    private User account;
    private List<Message> messages = new ArrayList<>();
    private MailStore mailStore;

    public Mailbox(User account, int mailStore) {
        this.account = account;

        if (mailStore == 0)
            this.mailStore = new FileMailStore();
        else
            this.mailStore = new MemoryMailStore();
    }

    @Override
    public void updateMail() {
       messages = mailStore.getMail(account.getName());

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o2.getSentTime().compareTo(o1.getSentTime());
            }
        });

        messages.sort(Comparator.comparing(Message::getSentTime).reversed());
    }

    @Override
    public void listMail() {
        System.out.println(messages.toString());
    }

    @Override
    public void sendMail(String destination, String subject, String body) {
        Message message = new Message(subject, body, account.getName(), destination);
        mailStore.sendMail(message);
    }

    @Override
    public void getMail() {

    }

    @Override
    public void filterUserMail() {

    }
}
