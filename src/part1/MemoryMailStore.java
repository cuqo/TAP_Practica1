package part1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        return mailList
                .stream()
                .filter(m -> m.getReceiver().equals(username))
                .collect(Collectors.toList());
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

    /**
     * Method that print mail store
     * @return print mail store
     */
    @Override
    public String toString() {
        return "MemoryMailStore{" +
                "mailList=" + mailList +
                '}';
    }
}
