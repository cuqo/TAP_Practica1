package part1;

import part4.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Config(
        store = "part1.FileMailStore",
        log = true
)
public class FileMailStore implements MailStore {

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

    public List<Message> getMail(String username) {
        return readFile()
                .stream()
                .filter(m -> m.getReceiver().equals(username))
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAllMessages() {

        return readFile();
    }

    private List<Message> readFile() {
        List<Message> messageList = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File("messages.txt"));
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String[] msg = line.split(";");


                DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
                Date date = format.parse(msg[2]);
                Message aux = new Message(msg[0], msg[1], date, msg[3], msg[4]);
                messageList.add(aux);

                //System.out.println(aux);

            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        return messageList;
    }
}
