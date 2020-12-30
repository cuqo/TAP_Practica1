package part1;

import part4.Config;
import java.util.List;

@Config(
        store = "part1.MailStore",
        log = true
)
public interface MailStore {

    void sendMail(Message msg);

    List<Message> getMail(String username);

    List<Message> getAllMessages();

}
