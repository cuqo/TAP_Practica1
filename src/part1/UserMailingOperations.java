package part1;

import java.util.List;
import java.util.function.Predicate;

public interface UserMailingOperations {

    /**
     * Method that update mailbox messages and sort it by sent time
     */
    void updateMail();

    /**
     * Method that return current user message list
     * @return list of user messages
     */
    List<Message> listMail();

    /**
     * Method that save a message in the current mail store
     * @param destination -> receiver of the message
     * @param subject -> subject of the message
     * @param body -> body of the message
     */
    void sendMail(String destination, String subject, String body);

    /**
     * Method that sort the message list based on condition parameter
     * @param cond -> value to decide the sort parameter of the list
     * @return list of messages sorted by condition
     */
    List<Message> listSortedMail(String cond);

    /**
     * Method that filter message list based on a predicate
     * @param predicate -> predicate to filter list
     * @return list of messages filtered by the predicate
     */
    List<Message> filterUserMail(Predicate<Message> predicate);
}
