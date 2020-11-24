package part1;

public interface UserMailingOperations {

    public void updateMail();
    public void listMail();
    public void sendMail(String destination, String subject, String body);
    public void getMail();
    public void filterUserMail();
}
