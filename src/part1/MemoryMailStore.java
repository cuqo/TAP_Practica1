package part1;

import java.util.ArrayList;

public class MemoryMailStore implements MailStore{

    private ArrayList<Message> mailList = new ArrayList();

    @Override
    public void sendMail(Message msg) {
        mailList.add(msg);
    }

    public ArrayList getMail(String username){
        ArrayList<Message> messages = new ArrayList();

        for (Message message : mailList){
            if (message.getReceiver().equals(username))
                messages.add(message);
        }

        return messages;
    }
}
