package part4;

import part1.*;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class TestConfig {
    public static void main(String[] args) {
        List<Class> mailStoreClasses = new ArrayList<>();
        MailSystem mailSystem = new MailSystem();

        mailStoreClasses.add(MemoryMailStore.class);
        mailStoreClasses.add(FileMailStore.class);

        for (Class mailStoreClass : mailStoreClasses) {
            if (mailStoreClass.isAnnotationPresent(Config.class)) {
                Annotation annotation = mailStoreClass.getAnnotation(Config.class);
                Config config = (Config) annotation;
                try {
                    mailSystem.readMailStore(config);
                } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                    e.printStackTrace();
                }

                //2. Create at least 3 users, two have the same name but different username.
                Mailbox mailbox1 = mailSystem.createNewUser("user1", "Joan", 2000);
                Mailbox mailbox2 = mailSystem.createNewUser("user2", "Joan", 2005);
                Mailbox mailbox3 = mailSystem.createNewUser("user3", "Maria", 1999);

                //3. Then, use the mailboxes to send a few emails between them. Make some of them share the same subject and make enough so that the following tests have results
                mailbox2.sendMail("user1", "hola Joan", "Hola que tal?");
                mailbox2.sendMail("user3", "hola Maria", "Ei que passa?");
                mailbox2.sendMail("user1", "hola Joan", "No m'has contestat");
                mailbox1.sendMail("user2", "TFG", "Perque m'has suspes?");
                mailbox3.sendMail("user1", "Cita", "Hola vull quedar amb tu");
                mailbox1.sendMail("user3", "RW: Cita", "Doncs jo no");
                mailbox2.sendMail("user3", "Consulta", "Tens covid?");

                //4. Get one of the mailboxes and update its mail.
                mailbox1.updateMail();
                mailbox2.updateMail();
                mailbox3.updateMail();

                //5. List the mailbox messages in the console. (Sorted by newer first.) Use the iterable capabilities of the mailbox!
                List<Message> msg = mailbox1.listMail();
                System.out.print("5: ");
                msg.forEach(System.out::println);

                //8. Use the mail system object to retrieve all messages and print them
                msg = mailSystem.getAllMessages();
                System.out.print("8: ");
                msg.forEach(System.out::println);
            }
        }
    }
}

