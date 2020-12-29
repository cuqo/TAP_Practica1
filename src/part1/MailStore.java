package part1;

import part4.Config;

import java.util.ArrayList;
import java.util.List;

@Config(
        store = "part1.MailStore",
        log = true
)
public abstract class  MailStore {

    public abstract void sendMail (Message msg);
    public abstract List<Message> getMail(String username);
    public abstract List<Message> getAllMessages();


}
