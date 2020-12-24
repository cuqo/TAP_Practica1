package part1;

import java.util.List;
import java.util.function.Predicate;

public interface UserMailingOperations {

    public void updateMail();
    public List<Message> listMail();
    public void sendMail(String destination, String subject, String body);
    public List<Message> listSortedMail(int cond);
    public List<Message> filterUserMail(Predicate<Message> predicate);
}
