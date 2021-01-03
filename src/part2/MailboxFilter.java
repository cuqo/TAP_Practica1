package part2;

import part1.Message;

import java.util.List;

public abstract class MailboxFilter {

    /**
     * Method that will filter the list of messages that have to be marked as spam
     * @param messages list of messages to be filtered
     * @return list of messages marked as spam
     */
    public abstract List<Message> update(List<Message> messages);
}