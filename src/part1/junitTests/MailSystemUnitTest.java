package part1.junitTests;

import org.junit.Before;
import org.junit.Test;
import part1.*;

import java.util.*;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

public class MailSystemUnitTest {

    MailSystem mailSystem;
    Mailbox mailbox;
    User user;
    MailStore mailStore;
    List<Message> messages;

    @Before
    public void setup() {
        mailStore = new MemoryMailStore();
        mailSystem = new MailSystem(mailStore);
        user = new User("user2", "John", 1995);
        mailbox = new Mailbox(user, mailStore);
        messages = new ArrayList<>();
        messages.add(new Message("user1", "user2", "hola Joan", "Hola que tal?"));
        messages.add(new Message("user2", "user3", "hola Maria", "Ei que passa?"));
        messages.add(new Message("user1", "user2", "hola Joan", "No m'has contestat"));
        messages.add(new Message("user2", "user1", "TFG", "Perque m'has suspes?"));
        messages.add(new Message("user1", "user3", "Cita", "Hola vull quedar amb tu"));
        messages.add(new Message("user3", "user2", "RW: Cita", "Doncs jo no"));
        messages.add(new Message("user1", "user3", "Consulta", "Tens covid?"));
    }

    @Test
    public void testCreateNewUser() {
        System.out.println("testCreateNewUser");

        Mailbox test = mailSystem.createNewUser("user2", "John", 1995);
        assertEquals(mailbox.toString(), test.toString());
    }

    @Test
    public void testGetAllMessages() {
        System.out.println("testGetAllMessages");
        ((MemoryMailStore) mailStore).setMailList(messages);

        List<Message> test = mailSystem.getAllMessages();
        assertEquals(messages, test);
    }

    @Test
    public void testFilterMessageGlobally() {
        System.out.println("testFilterMessageGlobally");
        ((MemoryMailStore) mailStore).setMailList(messages);

        List<Message> auxFilterList = new ArrayList<>();
        auxFilterList.add(messages.get(4));
        auxFilterList.add(messages.get(5));

        List<Message> test = mailSystem.filterMessageGlobally(message -> message.getSubject().contains("Cita"));
        assertEquals(auxFilterList, test);
    }

    @Test
    public void testAvgPerUser() {
        System.out.println("testAvgPerUser");
        ((MemoryMailStore) mailStore).setMailList(messages);

        Mailbox mailbox1 = mailSystem.createNewUser("user1", "Steve", 1998);
        Mailbox mailbox2 = mailSystem.createNewUser("user2", "John", 1995);
        Mailbox mailbox3 = mailSystem.createNewUser("user3", "Maria", 2001);

        int avg = messages.size() / 3;
        int test = mailSystem.avgPerUser();
        assertEquals(avg, test);
    }

    @Test
    public void testGroupPerSubject() {
        System.out.println("testGroupPerSubject");
        List<Message> aux = new ArrayList<>();
        aux.add(messages.get(0));
        aux.add(messages.get(1));
        aux.add(messages.get(2));

        List<Message> auxSubjectList1 = new ArrayList<>();
        auxSubjectList1.add(messages.get(0));
        auxSubjectList1.add(messages.get(2));

        List<Message> auxSubjectList2 = new ArrayList<>();
        auxSubjectList2.add(messages.get(1));

        ((MemoryMailStore) mailStore).setMailList(aux);

        Map<String, List<Message>> auxMapList = new HashMap<>();
        auxMapList.put(messages.get(0).getSubject(), auxSubjectList1);
        auxMapList.put(messages.get(1).getSubject(), auxSubjectList2);

        Map<String, List<Message>> test = mailSystem.groupPerSubject();
        assertEquals(auxMapList, test);
    }

    @Test
    public void testCountParticularName() {
        System.out.println("testCountParticularName");
        ((MemoryMailStore) mailStore).setMailList(messages);

        Mailbox mailbox3 = mailSystem.createNewUser("user3", "Maria", 2001);
        int test = mailSystem.countParticularName("Maria");
        assertEquals(3, test);
    }

    @Test
    public void testMessagesBornBefore() {
        System.out.println("testMessagesBornBefore");
        ((MemoryMailStore) mailStore).setMailList(messages);

        Mailbox mailbox1 = mailSystem.createNewUser("user1", "Steve", 1998);
        Mailbox mailbox2 = mailSystem.createNewUser("user2", "John", 1995);
        Mailbox mailbox3 = mailSystem.createNewUser("user3", "Maria", 2001);
        List<Message> test = mailSystem.messagesBornBefore(1998);
        assertEquals(messages.stream().filter(message -> message.getReceiver().equals("user2")).collect(Collectors.toList()), test);
    }

}
