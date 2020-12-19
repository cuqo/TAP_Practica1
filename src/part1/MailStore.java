package part1;

import java.util.ArrayList;
import java.util.List;

public interface  MailStore {

    public void sendMail (Message msg);
    public abstract ArrayList getMail(String username);

}
