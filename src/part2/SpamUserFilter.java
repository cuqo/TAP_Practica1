package part2;

import part1.Message;

import java.util.List;
import java.util.stream.Collectors;

public class SpamUserFilter extends MailboxFilter {

    /**
     * Method that puts the messages where the sender contains the word 'spam' in the spam list
     * @param messages list of messages to be filtered
     * @return list of spam messages
     */
    @Override
    public List<Message> update(List<Message> messages) {
        List<Message> spamList = messages
                .stream()
                .filter(m -> m.getSender().contains("spam"))
                .collect(Collectors.toList());
        messages.removeIf(m -> m.getSender().contains("spam"));

        return spamList;
    }
}