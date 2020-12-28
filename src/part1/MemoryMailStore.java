package part1;

import java.util.ArrayList;
import java.util.List;

public class MemoryMailStore extends MailStore{

    private List<Message> mailList = new ArrayList();

    @Override
    public void sendMail(Message msg) {
        mailList.add(msg);
    }

    public List getMail(String username){
        List<Message> messages = new ArrayList();

        for (Message message : mailList){
            if (message.getReceiver().equals(username))
                messages.add(message);
        }

        return messages;
    }

    @Override
    public List<Message> getAllMessages() {
        return mailList;
    }

    public void setMailList(List<Message> mailList) {
        this.mailList = mailList;
    }
}
