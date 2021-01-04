package part1.junitTests;

import org.junit.Before;
import org.junit.Test;
import part1.*;

import static org.junit.Assert.assertEquals;

public class Part1UnitTest {

    MailSystem memoryMailSystem, fileMailSystem;
    Mailbox mailbox;
    User user;
    MailStore memoryMailStore, fileMailStore;

    @Before
    public void setup() {
        memoryMailSystem = new MailSystem(new MemoryMailStore());
        fileMailSystem = new MailSystem(new FileMailStore());
        memoryMailStore = new MemoryMailStore();
        fileMailStore = new FileMailStore();
        user = new User("userTest", "John", 1995);
        mailbox = new Mailbox(user, memoryMailStore);


    }

    @Test
    public void testSendMail() {
        Message test = new Message("userTest", "userTest2", "test subject", "This is a test");
        mailbox.sendMail("userTest2", "test subject", "This is a test");
        assertEquals(test, memoryMailStore.getAllMessages().get(0));
    }

    @Test
    public void testUpdateMail() {
        Message test = new Message("userTest", "userTest2", "test subject", "This is a test");
        mailbox.sendMail("userTest2", "test subject", "This is a test");
        assertEquals(test, memoryMailStore.getAllMessages().get(0));
    }
}
