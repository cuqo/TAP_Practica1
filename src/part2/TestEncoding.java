package part2;

import part1.*;

import java.util.List;

public class TestEncoding {
    public static void main(String[] args) {
        Context context1 = new Context(new ReverseBody());
        Context context2 = new Context(new CipherBody());
        MailSystem mailSystem = new MailSystem(new FileMailStore());
        MailStore mailStore = new BodyDecorator(mailSystem.getMailStore(), context1, context2);
        mailSystem.setMailStore(mailStore);

        //2. Create at least 3 users, two have the same name but different username.
        Mailbox mailbox1 = mailSystem.createNewUser("user1", "Joan", 2000);
        Mailbox mailbox2 = mailSystem.createNewUser("user2", "Joan", 2005);
        Mailbox mailbox3 = mailSystem.createNewUser("user3spam", "Maria", 1999);

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
