package part3;

import part1.MailStore;

public interface MailStoreFactory {

    /**
     * Method that generate new instance of MailStore
     * @return new instance of MailStore
     */
    MailStore createMailStore();
}
