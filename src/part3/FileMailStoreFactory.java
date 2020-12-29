package part3;

import part1.FileMailStore;
import part1.MailStore;
import part1.MemoryMailStore;
import part2.BodyDecorator;
import part2.CipherBody;
import part2.Context;
import part2.ReverseBody;

public class FileMailStoreFactory implements MailStoreFactory{
    public MailStore createMailStore() {
        return new BodyDecorator(new FileMailStore(), new Context(new ReverseBody()), new Context(new CipherBody()));
        /*return new FileMailStore();*/
    }
}
