package part1;

import java.util.List;
import java.util.function.Predicate;

public class Test {

    public static void main(String[] args){
        //1. Initialize the mail system with an in-memory mail store.
        MailSystem mailSystem = new MailSystem(new MemoryMailStore());

        //2. Create at least 3 users, two have the same name but different username.
        Mailbox mailbox1 = mailSystem.createNewUser("user1", "Joan", 2000);
        Mailbox mailbox2 = mailSystem.createNewUser("user2", "Joan", 2005);
        Mailbox mailbox3 = mailSystem.createNewUser("user3", "Maria", 1999);

        //3. Then, use the mailboxes to send a few emails between them. Make some of them share the same subject and make enough so that the following tests have results


        //4. Get one of the mailboxes and update its mail.
        mailbox1.updateMail();

        //5. List the mailbox messages in the console. (Sorted by newer first.) Use the iterable capabilities of the mailbox!
        List<Message> msg = mailbox1.listMail();
        msg.forEach(System.out::println);

        //6. Now list the messages by sender username using the mailbox feature.
        msg = mailbox1.listSortedMail(1);
        msg.forEach(System.out::println);

        //7. Filter the messages with the following conditions:
        //  - The message subject contains a certain word.
        //  - The message sender is a certain user

        msg = mailbox1.filterUserMail(new Predicate<Message>() {
            @Override
            public boolean test(Message message) {
                return message.getSubject().contains("hola") && message.getSender().equals("user2");
            }
        });
        msg.forEach(System.out::println);

        //8. Use the mail system object to retrieve all messages and print them
        msg = mailSystem.getAllMessages();
        msg.forEach(System.out::println);

        //9. Filter messages globally that fulfill the following conditions:
        //  - The message subject is a single word.
        //  - The sender was born after year 2000.
        msg = mailSystem.messagesBornBefore(2000);
    }
}
