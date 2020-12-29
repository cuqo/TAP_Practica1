package part4;

import part1.MailStore;
import part1.User;
import part1.Mailbox;
import part1.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MailSystem {
    private MailStore mailStore;
    private List<User> userList;
    private Map<String, Mailbox> mapUsers;

    public MailSystem() {
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

    public Map<String, Mailbox> getAllMailboxes() {
        return mapUsers;
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

    public void readMailStore(Config config) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class mailStoreType = Class.forName(config.store());
        Object newMailStore = mailStoreType.newInstance();
        mailStore = (MailStore) newMailStore;

        System.out.println(config.store());
        System.out.println(config.log());
    }

    /* public void changeMailStore() {
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
    } */

}
