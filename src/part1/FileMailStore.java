package part1;

import part4.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Config(
        store = "part1.FileMailStore",
        log = true
)
public class FileMailStore implements MailStore {

    /**
     * Override method that receives a Message and append the message in file
     * @param msg -> Message to save in file
     */
    @Override
    public void sendMail(Message msg) {

        FileWriter myWriter;
        try {
            myWriter = new FileWriter("messages.txt", true);
            myWriter.write(msg.toStringFile());
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Override method that filters the messages of an specific user
     * @param username -> username of the specific user
     * @return list of messages of the specific user
     */
    @Override
    public List<Message> getMail(String username) {
        return readFile()
                .stream()
                .filter(m -> m.getReceiver().equals(username))
                .collect(Collectors.toList());
    }

    /**
     * Override method that catch all messages of the file
     * @return list of all messages of the file
     */
    @Override
    public List<Message> getAllMessages() {

        return readFile();
    }

    /**
     * Method to read the file
     * @return list of all messages read of the file
     */
    private List<Message> readFile() {
        List<Message> messageList = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File("messages.txt"));
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String[] msg = line.split(";");

                Date date = new Date(Long.parseLong(msg[2]));
                Message aux = new Message(msg[0], msg[1], date, msg[3], msg[4]);
                messageList.add(aux);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return messageList;
    }
}
