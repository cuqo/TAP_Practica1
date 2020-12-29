package part4;

import part1.*;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class TestConfig {
    public static void main(String[] args) {
        Class<FileMailStore> obj = FileMailStore.class;
        MailSystem mailSystem = new MailSystem();

        if(obj.isAnnotationPresent(Config.class)){
            Annotation annotation = obj.getAnnotation(Config.class);
            Config config = (Config) annotation;
            try {
                mailSystem.readMailStore(config);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        cliOperations(mailSystem);
    }

    public static void cliOperations(MailSystem mailSystem) {
        Scanner teclat = new Scanner(System.in);
        String line = "";
        while (!line.equals("exit")) {
            menuCLI();
            line = teclat.nextLine();
            String[] lineSplit = line.split(" ");
            switch (lineSplit[0]) {
                case "createuser":
                    if (lineSplit.length < 3)
                        System.out.println("Error al crear usuari: createuser <username> <name> <birthYear>");
                    else mailSystem.createNewUser(lineSplit[1], lineSplit[2], Integer.parseInt(lineSplit[3]));
                    break;
                case "filter":
                    List<Message> msg;
                    switch (lineSplit[1]) {
                        case "contains":
                            msg = mailSystem.filterMessageGlobally(new Predicate<Message>() {
                                @Override
                                public boolean test(Message message) {
                                    return message.getSubject().contains(lineSplit[2]) || message.getBody().contains(lineSplit[2]);
                                }
                            });
                            msg.forEach(System.out::println);
                            break;
                        case "lessthan":
                            msg = mailSystem.filterMessageGlobally(new Predicate<Message>() {
                                @Override
                                public boolean test(Message message) {
                                    return message.getBody().split(" ").length < Integer.parseInt(lineSplit[2]);
                                }
                            });
                            msg.forEach(System.out::println);
                            break;
                    }
                    break;
                case "logas":
                    mailSystem.getAllUsers().forEach((u) -> {
                        if (u.getUsername().equals(lineSplit[1])) {
                            System.out.println("Correct user");
                            cliUser(mailSystem.getAllMailboxes().get(lineSplit[1]), lineSplit[1]);
                        }
                    });
                    break;
                case "exit":
                    break;
                default:
                    System.out.println("Error, command doesnt exist");
                    break;
            }
        }
    }

    public static void cliUser(Mailbox mailbox, String username) {
        Scanner teclat = new Scanner(System.in);
        String line = "";
        List<Message> msg;
        while (!line.equals("logout")) {
            menuUserCLI();
            System.out.print("<" + username + ">: ");
            line = teclat.nextLine();
            String[] lineSplit = line.split(" ");
            switch (lineSplit[0]) {
                case "send":
                    String[] lineSplitComa = line.split("\"");
                    mailbox.sendMail(lineSplit[1], lineSplitComa[1], lineSplitComa[3]);
                    break;
                case "update":
                    mailbox.updateMail();
                    break;
                case "list":
                    msg = mailbox.listMail();
                    msg.forEach(System.out::println);
                    break;
                case "sort":
                    msg = mailbox.listSortedMail(lineSplit[1]);
                    msg.forEach(System.out::println);
                    break;
                case "filter":
                    switch (lineSplit[1]) {
                        case "contains":
                            msg = mailbox.filterUserMail(new Predicate<Message>() {
                                @Override
                                public boolean test(Message message) {
                                    return message.getSubject().contains(lineSplit[2]) || message.getBody().contains(lineSplit[2]);
                                }
                            });
                            msg.forEach(System.out::println);
                            break;
                        case "lessthan":
                            msg = mailbox.filterUserMail(new Predicate<Message>() {
                                @Override
                                public boolean test(Message message) {
                                    return message.getBody().split(" ").length < Integer.parseInt(lineSplit[2]);
                                }
                            });
                            msg.forEach(System.out::println);
                            break;
                    }
                    break;
                case "logout":
                    break;
                default:
                    System.out.println("Error, command doesnt exist");
                    break;
            }
        }
    }

    public static void menuCLI() {
        System.out.println("***********************************************");
        System.out.println("*- createuser <username> <name> <birthYear>   *");
        System.out.println("*- filter <contains> <word> / <lessthan> <n>  *");
        System.out.println("*- logas <username>                           *");
        System.out.println("*- exit                                       *");
        System.out.println("***********************************************");
    }

    public static void menuUserCLI() {
        System.out.println("*****************************************************************");
        System.out.println("*- send <to> \"subject\" \"body\"                                   *");
        System.out.println("*- update                                                       *");
        System.out.println("*- list                                                         *");
        System.out.println("*- sort <sender> / <receiver> / <sentTime> / <body> / <subject> *");
        System.out.println("*- filter <contains> <word> / <lessthan> <n>                    *");
        System.out.println("*- logout                                                       *");
        System.out.println("*****************************************************************");
    }
}
