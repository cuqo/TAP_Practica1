package part3;

import part1.MailStore;
import part1.MemoryMailStore;

public class MemoryMailStoreFactory implements MailStoreFactory {

    /**
     * Method that generate new instance of MemoryMailStore
     * @return new instance of MemoryMailStore
     */
    public MailStore createMailStore() {
        return new MemoryMailStore();
    }
}
