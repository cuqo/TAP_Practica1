package part3;

import part1.FileMailStore;
import part1.MailStore;
import part1.MemoryMailStore;

public class FileMailStoreFactory implements MailStoreFactory{
    public MailStore createMailStore() {
        return new FileMailStore();
    }
}
