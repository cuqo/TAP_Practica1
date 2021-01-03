package part1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MailSystem {
    private MailStore mailStore;
    private List<User> userList;
    private Map<String, Mailbox> mapUsers;

    /**
     * Empty constructor
     */
    public MailSystem() {
    }

    /**
     * Constructor
     * @param mailStore -> current mail store
     */
    public MailSystem(MailStore mailStore) {
        this.mailStore = mailStore;
        this.userList = new ArrayList<>();
        this.mapUsers = new HashMap<>();
    }

    /**
     * Method that create new user in the system and add user and mailbox user in specific lists
     * @param username -> user username
     * @param name -> user name
     * @param yearBirth -> user birth year
     * @return mailbox of user
     */
    public Mailbox createNewUser(String username, String name, int yearBirth) {
        User user = new User(username, name, yearBirth);
        Mailbox mailbox = new Mailbox(user, mailStore);

        userList.add(user);
        mapUsers.put(username, mailbox);

        return mailbox;
    }

    /**
     * Method that catch all messages of the current mail store
     * @return list of all messages of the current mail store
     */
    public List<Message> getAllMessages() {
        return mailStore.getAllMessages();

    }

    /**
     * Return users map from mail store
     * @return users map
     */
    public Map<String, Mailbox> getAllMailboxes() {
        return mapUsers;
    }

    /**
     * Return users list from mail system
     * @return users list
     */
    public List<User> getAllUsers() {
        return userList;
    }

    /**
     * Method that filter messages list based on a predicate
     * @param predicate -> predicate to filter list
     * @return list of messages filtered by the predicate
     */
    public List<Message> filterMessageGlobally(Predicate<Message> predicate) {
        return getAllMessages()
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Method that return the total messages of current mail store
     * @return number of total messages
     */
    public int countTotalMessages() {
        return getAllMessages().size();
    }

    /**
     * Method that return the average of messages per user
     * @return average of messages per user
     */
    public int avgPerUser() {
        return countTotalMessages() / userList.size();
    }

    /**
     * Method that group messages per subject
     * @return map list of grouped messages per subject
     */
    public Map<String, List<Message>> groupPerSubject() {
        return getAllMessages().stream().collect(Collectors.groupingBy(Message::getSubject));
    }

    /**
     * Method that count the words of all messages of a specific group of users with the same name
     * @param name -> name of users
     * @return number of words
     */
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

    /**
     * Method that filter the message list with users who has born before the certain year
     * @param year -> year
     * @return message list filtered with users who has born before the certain year
     */
    public List<Message> messagesBornBefore(int year) {
        List<User> users = userList
                .stream()
                .filter(u -> u.getYearBirth() < year)
                .collect(Collectors.toList());

        return getAllMessages().stream()
                .filter(e -> users.stream().map(User::getUsername).anyMatch(name -> name.equals(e.getReceiver())))
                .collect(Collectors.toList());

    }

    /**
     * Method that change current mail store to another mail store saving the users messages
     */
    public void changeMailStore() {
        if (mailStore instanceof MemoryMailStore) {
            FileWriter myWriter;

            try {
                List<Message> msg = mailStore.getAllMessages();
                myWriter = new FileWriter("messages.txt", true);
                for (Message m : msg)
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
