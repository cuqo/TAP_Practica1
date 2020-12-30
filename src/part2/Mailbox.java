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

    public Mailbox(User account, MailStore mailStore) {
        this.account = account;
        this.mailStore = mailStore;
    }

    public List<Message> listSpamMail() {
        return spamList;
    }

    public void attach(MailboxFilter observer) {
        observers.add(observer);
    }

    public void notifyAllObservers() {
        for (MailboxFilter observer : observers) {
            spamList.addAll(observer.update(messages));
        }
    }

    @Override
    public void updateMail() {
        messages = mailStore.getMail(account.getUsername());

        messages.sort((o1, o2) -> o2.getSentTime().compareTo(o1.getSentTime()));

        messages.sort(Comparator.comparing(Message::getSentTime).reversed());
        notifyAllObservers();
    }

    @Override
    public List<Message> listMail() {

        return messages;
    }

    @Override
    public void sendMail(String destination, String subject, String body) {
        Message message = new Message(account.getUsername(), destination, subject, body);
        mailStore.sendMail(message);
    }

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

    @Override
    public List<Message> filterUserMail(Predicate<Message> predicate) {
        return messages
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
