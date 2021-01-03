package part2;

import part1.Message;
import part1.User;
import part1.UserMailingOperations;
import part1.MailStore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Mailbox implements UserMailingOperations {

    private final User account;
    private List<Message> messages = new ArrayList<>();
    private final MailStore mailStore;
    private final List<MailboxFilter> observers = new ArrayList<>();
    private final List<Message> spamList = new ArrayList<>();

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
     * Method to get the list of messages that are marked as spam
     * @return list of spam messages
     */
    public List<Message> listSpamMail() {
        return spamList;
    }

    /**
     * Method that adds a filter to the mailbox
     * @param observer -> filter that will be subscribed to the mailbox
     */
    public void attach(MailboxFilter observer) {
        observers.add(observer);
    }

    /**
     * Method that calls every filter to update the spam list
     */
    public void notifyAllObservers() {
        for (MailboxFilter observer : observers) {
            spamList.addAll(observer.update(messages));
        }
    }

    /**
     * Override method that updates mailbox messages list and sorts it by sent time. It also filters the messages that have to be marked as spam
     */
    @Override
    public void updateMail() {
        messages = mailStore.getMail(account.getUsername());

        messages.sort((o1, o2) -> o2.getSentTime().compareTo(o1.getSentTime()));

        messages.sort(Comparator.comparing(Message::getSentTime).reversed());
        notifyAllObservers();
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
}
