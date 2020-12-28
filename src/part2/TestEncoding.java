package part2;

import part1.MailStore;
import part1.MemoryMailStore;
import part1.Message;

import java.util.List;

public class TestEncoding {
    public static void main(String[] args) {
        MailSystem mailSystem = new MailSystem(new MemoryMailStore());
        MailStore mailStore = new BodyDecorator(mailSystem.getMailStore());
        mailSystem.setMailStore(mailStore);

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

        mailbox1.updateMail();
        List<Message> msg = mailbox1.listMail();
        msg.forEach(System.out::println);

    }
}
