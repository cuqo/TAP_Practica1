package part2;

import part1.*;

import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestFilter {

    public static void main(String[] args) {
        //1. Initialize the mail system with an in-memory mail store.
        MailSystem mailSystem = new MailSystem(new MemoryMailStore());

        //2. Create at least 3 users, two have the same name but different username.
        MailboxPart2 mailbox1 = mailSystem.createNewUser("user1", "Joan", 2000);
        MailboxPart2 mailbox2 = mailSystem.createNewUser("user2", "Joan", 2005);
        MailboxPart2 mailbox3 = mailSystem.createNewUser("user3spam", "Maria", 1999);

        //3. Then, use the mailboxes to send a few emails between them. Make some of them share the same subject and make enough so that the following tests have results
        mailbox2.sendMail("user1", "hola Joan", "Hola que tal?");
        mailbox2.sendMail("user3spam", "hola Maria", "Ei que passa?");
        mailbox2.sendMail("user1", "hola Joan", "No m'has contestat");
        mailbox1.sendMail("user2", "TFG", "Perque m'has suspes?");
        mailbox3.sendMail("user1", "Cita", "Hola vull quedar amb tu");
        mailbox1.sendMail("user2", "Cita", "Hola vull quedar amb tu");
        mailbox1.sendMail("user3spam", "RW: Cita", "Doncs jo no");
        mailbox2.sendMail("user3spam", "Consulta", "Tens covid?");

        //4. Get one of the mailboxes and update its mail.
        mailbox1.updateMail();
        mailbox2.updateMail();
        mailbox3.updateMail();

        //5. List the mailbox messages in the console. (Sorted by newer first.) Use the iterable capabilities of the mailbox!
        System.out.println("********* MAIL LIST *******************************************************************************************************************");
        System.out.println("********* USER1 ***********************************************************************************************************************");
        List<Message> msg;
        msg = mailbox1.listMail();
        msg.forEach(System.out::println);
        System.out.println("********* USER2 ***********************************************************************************************************************");
        msg = mailbox2.listMail();
        msg.forEach(System.out::println);
        System.out.println("********* USER3SPAM *******************************************************************************************************************");
        msg = mailbox3.listMail();
        msg.forEach(System.out::println);
        System.out.println("***************************************************************************************************************************************");
        System.out.println();

        //6. List the spamlist messages in the console
        System.out.println("********* SPAM LIST *******************************************************************************************************************");
        System.out.println("********* USER1 ***********************************************************************************************************************");
        msg = mailbox1.listSpamMail();
        msg.forEach(System.out::println);
        System.out.println("********* USER2 ***********************************************************************************************************************");
        msg = mailbox2.listSpamMail();
        msg.forEach(System.out::println);
        System.out.println("********* USER3SPAM *******************************************************************************************************************");
        msg = mailbox3.listSpamMail();
        msg.forEach(System.out::println);
        System.out.println("***************************************************************************************************************************************");
        System.out.println();

        //7. Using streams, obtain the users that have sent any message that got filtered as spam
        System.out.println("********* SPAM USERS *********");
        msg = mailSystem.filterMessageGlobally(new Predicate<Message>() {
            @Override
            public boolean test(Message message) {
                return message.getBody().toCharArray().length > 20 || message.getSender().contains("spam");
            }
        });
        msg.forEach(message -> System.out.println(message.getSender()));
    }
}
