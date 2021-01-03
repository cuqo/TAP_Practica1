package part1;

import part4.Config;

import java.util.ArrayList;
import java.util.List;

@Config(
        store = "part1.MemoryMailStore",
        log = true
)
public class MemoryMailStore implements MailStore {

    private List<Message> mailList = new ArrayList<>();

    /**
     * Override method that receive a Message and append the message in mail list
     * @param msg -> Message to save in mail list
     */
    @Override
    public void sendMail(Message msg) {
        mailList.add(msg);
    }

    /**
     * Override method that filter the messages of an specific user
     * @param username -> username of the specific user
     * @return list of messages of the specific user
     */
    @Override
    public List<Message> getMail(String username) {
        List<Message> messages = new ArrayList<>();

        for (Message message : mailList) {
            if (message.getReceiver().equals(username))
                messages.add(message);
        }

        return messages;
    }

    /**
     * Override method that catch all messages in mail list
     * @return list of all messages in mail list
     */
    @Override
    public List<Message> getAllMessages() {
        return mailList;
    }

    /**
     * Set the mail list with the list passed by parameter
     * @param mailList -> current mail store mail list
     */
    public void setMailList(List<Message> mailList) {
        this.mailList = mailList;
    }
}
