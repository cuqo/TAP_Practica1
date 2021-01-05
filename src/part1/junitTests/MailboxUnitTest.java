package part1.junitTests;

import org.junit.Before;
import org.junit.Test;
import part1.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class MailboxUnitTest {

    Mailbox mailbox;
    User user;
    MailStore memoryMailStore;
    List<Message> messages;

    @Before
    public void setup() {
        memoryMailStore = new MemoryMailStore();
        user = new User("user2", "John", 1995);
        mailbox = new Mailbox(user, memoryMailStore);
        messages = new ArrayList<>();
        messages.add(new Message("user1", "user2", "hola Joan", "Hola que tal?"));
        messages.add(new Message("user2", "user3", "hola Maria", "Ei que passa?"));
        messages.add(new Message("user1", "user2", "hola Joan", "No m'has contestat"));
        messages.add(new Message("user2", "user1", "TFG", "Perque m'has suspes?"));
        messages.add(new Message("user1", "user3", "Cita", "Hola vull quedar amb tu"));
        messages.add(new Message("user3", "user2", "RW: Cita", "Doncs jo no"));
        messages.add(new Message("user1", "user3", "Consulta", "Tens covid?"));
        mailbox.setMessages(messages);
        ((MemoryMailStore) memoryMailStore).setMailList(messages);
    }

    @Test
    public void testListSortedMail() {
        System.out.println("testListSortedMail");

        List<Message> test = mailbox.listSortedMail("sender");
        messages.sort(Comparator.comparing(Message::getSender));
        assertEquals(messages, test);

        test = mailbox.listSortedMail("receiver");
        messages.sort(Comparator.comparing(Message::getReceiver));
        assertEquals(messages, test);

        test = mailbox.listSortedMail("sentTime");
        messages.sort(Comparator.comparing(Message::getSentTime));
        assertEquals(messages, test);

        test = mailbox.listSortedMail("subject");
        messages.sort(Comparator.comparing(Message::getSubject));
        assertEquals(messages, test);

        test = mailbox.listSortedMail("body");
        messages.sort(Comparator.comparing(Message::getBody));
        assertEquals(messages, test);
    }

    @Test
    public void testFilterUserMail() {
        System.out.println("testFilterUserMail");

        List<Message> auxFilterList = new ArrayList<>();
        auxFilterList.add(messages.get(4));
        auxFilterList.add(messages.get(5));

        List<Message> test = mailbox.filterUserMail(message -> message.getSubject().contains("Cita"));
        assertEquals(auxFilterList, test);
    }

    @Test
    public void testUpdateMail() {
        System.out.println("testUpdateMail");

        mailbox.updateMail();
        List<Message> test = mailbox.listMail();
        assertEquals(messages.stream().filter(message -> message.getReceiver().equals("user2")).collect(Collectors.toList()), test);
    }
}
