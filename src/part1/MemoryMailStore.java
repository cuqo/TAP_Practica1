package part1;

import java.util.ArrayList;

public class MemoryMailStore extends MailStore{

    public ArrayList getMail(String username){
        ArrayList messages = new ArrayList();

        for (Message message : getMessageList()){
            if (message.getReceiver().equals(username))
                messages.add(message);
        }

        return messages;
    }
}
