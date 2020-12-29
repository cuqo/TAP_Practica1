package part3;

import part1.MailStore;
import part1.MemoryMailStore;

public class MemoryMailStoreFactory implements MailStoreFactory{
    public MailStore createMailStore() {
        return new MemoryMailStore();
    }
}
