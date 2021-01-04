package part1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Mailbox implements UserMailingOperations {
    private User account;
    private List<Message> messages = new ArrayList<>();
    private MailStore mailStore;

    /**
     * Empty constructor
     */
    public Mailbox() {
    }

    /**
     * Constructor
     * @param account -> current mailbox user
     * @param mailStore -> current mail store
     */
    public Mailbox(User account, MailStore mailStore) {
        this.account = account;
        this.mailStore = mailStore;
    }

    /**
     * Override method that updates mailbox messages list and sorts it by sent time
     */
    @Override
    public void updateMail() {
        messages = mailStore.getMail(account.getUsername());

        messages.sort((o1, o2) -> o2.getSentTime().compareTo(o1.getSentTime()));

        messages.sort(Comparator.comparing(Message::getSentTime).reversed());
    }

    /**
     * Override method that return the current message list
     * @return list of user messages
     */
    @Override
    public List<Message> listMail() {

        return messages;
    }

    /**
     * Override method that save a message in current mail store
     * @param destination -> receiver of the message
     * @param subject -> subject of the message
     * @param body -> body of the message
     */
    @Override
    public void sendMail(String destination, String subject, String body) {
        Message message = new Message(account.getUsername(), destination, subject, body);
        mailStore.sendMail(message);
    }

    /**
     * Override method that sort the message list based on condition parameter
     * @param cond -> value to decide the sort parameter of the list
     * @return list of messages sorted by condition
     */
    @Override
    public List<Message> listSortedMail(String cond) {
        List<Message> sortedList;

        switch (cond) {
            case "sender":
                sortedList = messages.stream()
                        .sorted(Comparator.comparing(Message::getSender))
                        .collect(Collectors.toList());
                break;
            case "receiver":
                sortedList = messages.stream()
                        .sorted(Comparator.comparing(Message::getReceiver))
                        .collect(Collectors.toList());
                break;
            case "sentTime":
                sortedList = messages.stream()
                        .sorted(Comparator.comparing(Message::getSentTime))
                        .collect(Collectors.toList());
                break;
            case "subject":
                sortedList = messages.stream()
                        .sorted(Comparator.comparing(Message::getSubject))
                        .collect(Collectors.toList());
                break;
            default:
                sortedList = messages.stream()
                        .sorted(Comparator.comparing(Message::getBody))
                        .collect(Collectors.toList());
                break;

        }

        return sortedList;
    }

    /**
     * Override method that filter message list based on a predicate
     * @param predicate -> predicate to filter list
     * @return list of messages filtered by the predicate
     */
    @Override
    public List<Message> filterUserMail(Predicate<Message> predicate) {
        return messages
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    /**
     * Set the message list with list passed by parameter
     * @param messages -> current message list
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * Method that print current mailbox
     * @return print mailbox
     */
    @Override
    public String toString() {
        return "Mailbox{" +
                "account=" + account +
                ", messages=" + messages +
                ", mailStore=" + mailStore +
                '}';
    }
}
