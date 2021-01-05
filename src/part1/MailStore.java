package part1;

import java.util.List;

public interface MailStore {

    /**
     * Method that receive a Message and append the message in current mail store
     * @param msg -> Message to save in mail store
     */
    void sendMail(Message msg);

    /**
     * Method that filter the messages of an specific user in the current mail store
     * @param username -> username of the specific user
     * @return list of messages of the specific user
     */
    List<Message> getMail(String username);

    /**
     * Method that catch all messages of the current mail store
     * @return list of all messages of the current mail store
     */
    List<Message> getAllMessages();

}
