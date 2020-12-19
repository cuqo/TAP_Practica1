package part1;

public class Test {

    public static void main(String[] args){
        Message m = new Message("persona 1", "persona 2", "test", "Hola soc un test");
        MailStore ms = new MemoryMailStore();

        ms.sendMail(m);

        ms.getMail("persona 2");
    }
}
