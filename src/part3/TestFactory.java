package part3;

import part1.Mailbox;
import part1.Message;
import part1.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestFactory {
    public static void main(String[] args) {

        List<MailStoreFactory> factoriesList = new ArrayList<>();
        factoriesList.add(new MemoryMailStoreFactory());
        factoriesList.add(new FileMailStoreFactory());
        factoriesList.add(new RedisMailStoreFactory());

        factoriesList.forEach(mailStoreFactory -> {
            MailSystem mailSystem = new MailSystem(mailStoreFactory);

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

            //6. Now list the messages by sender username using the mailbox feature.
            msg = mailbox1.listSortedMail("sender");
            System.out.print("6: ");
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
            System.out.print("7: ");
            msg.forEach(System.out::println);

            //8. Use the mail system object to retrieve all messages and print them
            msg = mailSystem.getAllMessages();
            System.out.print("8: ");
            msg.forEach(System.out::println);

            //9. Filter messages globally that fulfill the following conditions:
            //  - The message subject is a single word.
            //  - The sender was born after year 2000.


            /*msg = mailSystem.getAllMessages().stream()
                .filter(e -> users.stream().map(User::getName).anyMatch(name -> name.equals(e.getReceiver())) && e.getSubject().split(" ").length == 1)
                .collect(Collectors.toList());*/

            msg = mailSystem.filterMessageGlobally(new Predicate<Message>() {
                @Override
                public boolean test(Message message) {
                    List<User> users = mailSystem.getAllUsers()
                            .stream()
                            .filter(u -> u.getYearBirth() < 2000)
                            .collect(Collectors.toList());
                    return users.stream().map(User::getUsername).anyMatch(name -> name.equals(message.getReceiver())) && message.getSubject().split(" ").length == 1;
                }
            });
            System.out.print("9: ");
            msg.forEach(System.out::println);

            //10. Get the count of messages in the system and print it.
            System.out.println("10: " + mailSystem.countTotalMessages());

            //11. Get the average number of messages received per user and print it.
            System.out.println("11: " + mailSystem.avgPerUser());

            //12. Group the messages per subject in a Map<String, List<Message>> and print it.
            Map<String, List<Message>> map = mailSystem.groupPerSubject();
            System.out.println("12: " + map);

            //13. Count the words of all messages sent by users with a certain real name.
            int count = mailSystem.countParticularName("Maria");
            System.out.println("13: " + count);

            //14. Use the name that you used on two users. Print the result.
            count = mailSystem.countParticularName("Joan");
            System.out.println("14: " + count);

            //15. Print the messages received by users born before year 2000.
            msg = mailSystem.messagesBornBefore(2000);
            System.out.println("15: " + msg);

            //16. Now change the mail store to the file implementation
            /*mailSystem.changeMailStore();*/
        });


    }
}
