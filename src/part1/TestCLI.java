package part1;

public class TestCLI {

    public static void main(String[] args) {
        CLI cli = new CLI(new MailSystem(new MemoryMailStore()));
        cli.cliOperations();
    }
}
