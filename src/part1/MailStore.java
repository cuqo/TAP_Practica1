package part1;

import java.util.ArrayList;
import java.util.List;

public abstract class  MailStore {

    public abstract void sendMail (Message msg);
    public abstract List<Message> getMail(String username);
    public abstract List<Message> getAllMessages();


}
