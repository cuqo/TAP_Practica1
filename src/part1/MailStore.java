package part1;

import java.util.ArrayList;
import java.util.List;

public abstract class MailStore {
    private List<Message> messageList = new ArrayList<>();

    public void sendMail (){
    }

    public abstract ArrayList getMail(String username);

    public List<Message> getMessageList() {
        return messageList;
    }
}
