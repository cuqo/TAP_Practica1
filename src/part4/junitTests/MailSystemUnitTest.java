package part4.junitTests;

import org.junit.Before;
import org.junit.Test;
import part1.*;
import part4.MailSystem;

import static org.junit.Assert.assertEquals;

public class MailSystemUnitTest {

    MailStore memoryMailStore;

    @Before
    public void setup() {
        memoryMailStore = new MemoryMailStore();
    }

    @Test
    public void testReadMailStore() {
        System.out.println("testReadMailStore");
        MailSystem mailSystem = new MailSystem();

        assertEquals(memoryMailStore.toString(), mailSystem.getMailStore().toString());
    }
}
