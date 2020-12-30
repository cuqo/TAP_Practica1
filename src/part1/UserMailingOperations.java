package part1;

import java.util.List;
import java.util.function.Predicate;

public interface UserMailingOperations {

    void updateMail();

    List<Message> listMail();

    void sendMail(String destination, String subject, String body);

    List<Message> listSortedMail(String cond);

    List<Message> filterUserMail(Predicate<Message> predicate);
}
