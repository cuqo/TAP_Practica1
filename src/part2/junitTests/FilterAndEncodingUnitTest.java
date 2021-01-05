package part2.junitTests;

import org.junit.Before;
import org.junit.Test;
import part1.*;
import part2.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FilterAndEncodingUnitTest {

    MailboxFilter spamUserFilter, tooLongFilter;
    ReverseBody reverseBody;
    CipherBody cipherBody;
    List<Message> messages;

    @Before
    public void setup() {
        spamUserFilter = new SpamUserFilter();
        tooLongFilter = new TooLongFilter();
        reverseBody = new ReverseBody();
        cipherBody = new CipherBody();
        messages = new ArrayList<>();
        messages.add(new Message("user1", "user2spam", "hola Joan", "Hola que tal?"));
        messages.add(new Message("user2spam", "user3", "hola Maria", "Ei que passa?"));
        messages.add(new Message("user1", "user2spam", "hola Joan", "No m'has contestat"));
        messages.add(new Message("user2spam", "user1", "TFG", "Perque m'has suspes?"));
        messages.add(new Message("user1", "user3", "Cita", "Hola vull quedar amb tu"));
        messages.add(new Message("user3", "user2spam", "RW: Cita", "Doncs jo no"));
        messages.add(new Message("user1", "user3", "Consulta", "Tens covid?"));
    }

    @Test
    public void testSpamUserFilter() {
        System.out.println("testSpamUserFilter");
        List<Message> auxList = new ArrayList<>();
        auxList.add(messages.get(1));
        auxList.add(messages.get(3));

        List<Message> test = spamUserFilter.update(messages);
        assertEquals(auxList, test);
    }

    @Test
    public void testTooLongFilter() {
        System.out.println("testTooLongFilter");
        List<Message> auxList = new ArrayList<>();
        auxList.add(messages.get(4));

        List<Message> test = tooLongFilter.update(messages);
        assertEquals(auxList, test);
    }

    @Test
    public void testSendMailReverseBody() {
        System.out.println("testSendMailReverseBody");

        String body = "?asoc al av moc aloH";
        String test = reverseBody.sendMail("Hola com va la cosa?");
        assertEquals(body, test);
    }

    @Test
    public void testGetMailReverseBody() {
        System.out.println("testGetMailReverseBody");

        String body = "Hola com va la cosa?";
        String test = reverseBody.sendMail("?asoc al av moc aloH");
        assertEquals(body, test);
    }

    @Test
    public void testSendMailCipherBody() {
        System.out.println("testSendMailCipherBody");

        String body = "B1xJ6Br0ncmUSjH2cF8FZIKpTBJ/SjNCuFYWJmZOs8E=";
        String test = cipherBody.sendMail("Hola com va la cosa?");
        assertEquals(body, test);
    }

    @Test
    public void testGetMailCipherBody() {
        System.out.println("testGetMailCipherBody");

        String body = "Hola com va la cosa?";
        String test = cipherBody.getMail("B1xJ6Br0ncmUSjH2cF8FZIKpTBJ/SjNCuFYWJmZOs8E=");
        assertEquals(body, test);
    }
}
