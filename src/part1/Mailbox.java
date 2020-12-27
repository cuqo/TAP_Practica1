package part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Mailbox implements UserMailingOperations{
    private User account;
    private List<Message> messages = new ArrayList<>();
    private MailStore mailStore;

    public Mailbox(User account, MailStore mailStore) {
        this.account = account;
        this.mailStore = mailStore;
    }

    @Override
    public void updateMail() {
       messages = mailStore.getMail(account.getUsername());

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o2.getSentTime().compareTo(o1.getSentTime());
            }
        });

        messages.sort(Comparator.comparing(Message::getSentTime).reversed());
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

        switch(cond){
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
        //predicate= m -> m.getSender().equals("Lluis") && m.getBody().startsWith("Hola") ;

        List<Message> filterList = messages
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        return filterList;
    }
}
