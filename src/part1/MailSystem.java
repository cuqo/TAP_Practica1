package part1;

import java.io.FileWriter;
import java.io.IOException;
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

    public List<User> getAllUsers() {
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

    public int countTotalMessages() {
        return getAllMessages().size();
    }

    public int avgPerUser() {
        return countTotalMessages() / userList.size();
    }

    public Map<String, List<Message>> groupPerSubject() {
        return getAllMessages().stream().collect(Collectors.groupingBy(Message::getSubject));
    }

    public int countParticularName(String name) {
        List<User> users = userList
                .stream()
                .filter(u -> u.getName().equals(name))
                .collect(Collectors.toList());

        List<Message> msg = getAllMessages().stream()
                .filter(e -> users.stream().map(User::getUsername).anyMatch(n -> n.equals(e.getSender())))
                .collect(Collectors.toList());

        int count = 0;
        for (Message m : msg) {
            count += m.getBody().split(" ").length;
        }

        return count;
    }

    public List<Message> messagesBornBefore(int year) {
        List<User> users = userList
                .stream()
                .filter(u -> u.getYearBirth() < year)
                .collect(Collectors.toList());

        return getAllMessages().stream()
                .filter(e -> users.stream().map(User::getUsername).anyMatch(name -> name.equals(e.getReceiver())))
                .collect(Collectors.toList());

    }

    public void changeMailStore() {
        if (mailStore instanceof MemoryMailStore) {
            FileWriter myWriter = null;

            try {
                List<Message> msg = mailStore.getAllMessages();
                myWriter = new FileWriter("messages.txt", true);
                for(Message m : msg)
                    myWriter.write(m.toStringFile());
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mailStore = new FileMailStore();

        } else {
            List<Message> aux = mailStore.getAllMessages();
            mailStore = new MemoryMailStore();
            ((MemoryMailStore) mailStore).setMailList(aux);
        }
    }

}
