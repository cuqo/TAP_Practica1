package part1.junitTests;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import part1.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class MailStoreUnitTest {

    User user;
    MailStore memoryMailStore, fileMailStore;
    List<Message> messages;

    @Before
    public void setup() {
        memoryMailStore = new MemoryMailStore();
        fileMailStore = new FileMailStore();
        user = new User("user2", "John", 1995);
        messages = new ArrayList<>();
        messages.add(new Message("user1", "user2", "hola Joan", "Hola que tal?"));
        messages.add(new Message("user2", "user3", "hola Maria", "Ei que passa?"));
        messages.add(new Message("user1", "user2", "hola Joan", "No m'has contestat"));
        messages.add(new Message("user2", "user1", "TFG", "Perque m'has suspes?"));
        messages.add(new Message("user1", "user3", "Cita", "Hola vull quedar amb tu"));
        messages.add(new Message("user3", "user2", "RW: Cita", "Doncs jo no"));
        messages.add(new Message("user1", "user3", "Consulta", "Tens covid?"));
        ((MemoryMailStore) memoryMailStore).setMailList(messages);
    }

    @Test
    public void testGetMailMemoryMailStore() {
        System.out.println("testGetMailMemoryMailStore");

        List<Message> test = memoryMailStore.getMail("user2");
        List<Message> auxList = new ArrayList<>();
        auxList.add(messages.get(0));
        auxList.add(messages.get(2));
        auxList.add(messages.get(5));
        assertEquals(test, auxList);
    }

    @Test
    public void testGetMailFileMailStore() {
        System.out.println("testGetMailFileMailStore");

        messages.forEach(message -> fileMailStore.sendMail(message));
        List<Message> test = fileMailStore.getMail("user2");
        List<Message> auxList = new ArrayList<>();
        auxList.add(messages.get(0));
        auxList.add(messages.get(2));
        auxList.add(messages.get(5));
        assertEquals(test.toString(), auxList.toString());
    }
}
