package part1;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MailSystem {
    //fer hashmap user-mailbox
    private MailStore mailStore;
    private List<User> userList;
    private Map<String, Mailbox> mapUsers;

    public MailSystem(MailStore mailStore) {
        this.mailStore = mailStore;
        this.userList = new ArrayList<>();
        this.mapUsers = new HashMap<String, Mailbox>();
    }

    public Mailbox createNewUser(String username, String name, int yearBirth) {
        User user = new User(username, name, yearBirth);
        Mailbox mailbox = new Mailbox(user, mailStore);

        userList.add(user);
        mapUsers.put(username, mailbox);

        return mailbox;
    }

    public List<Message> getAllMessages() {
        return mailStore.getAllMessages();

    }

    public List<User> getAllUsers(){
        return userList;
    }

    public List<Message> filterMessageGlobally(Predicate<Message> predicate) {
        //predicate= m -> m.getSender().equals("Lluis") && m.getBody().startsWith("Hola") ;


        List<Message> filterList = getAllMessages()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return filterList;
    }

    public int countTotalMessages(){
        return getAllMessages().size();
    }

    public int avgPerUser(){
        return countTotalMessages()/userList.size();
    }

    public Map<String, List<Message>> groupPerSubject(){
        return getAllMessages().stream().collect(Collectors.groupingBy(Message::getSubject));
    }

    public List<Message> messagesBornBefore (int year){
        List<User> users = userList
                .stream()
                .filter(u -> u.getYearBirth() < year)
                .collect(Collectors.toList());

        return getAllMessages().stream()
                        .filter(e -> users.stream().map(User::getName).anyMatch(name -> name.equals(e.getReceiver())))
                        .collect(Collectors.toList());

    }

    //FALTA Count the words of all messages from users with a particular name.
}
