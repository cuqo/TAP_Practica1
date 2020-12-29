package part3;

import part1.MailStore;

public interface MailStoreFactory {
    public abstract MailStore createMailStore();
}
