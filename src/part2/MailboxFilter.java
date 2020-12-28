package part2;

import part1.Message;

import java.util.List;

public abstract class MailboxFilter {
    public abstract List<Message> update(List<Message> messages);
}