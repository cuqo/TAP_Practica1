package part2;

import part1.Message;

import java.util.List;
import java.util.stream.Collectors;

public class TooLongFilter extends MailboxFilter{

    /**
     * Method that puts the messages where the body is higher than 20 characters in the spam list
     * @param messages list of messages to be filtered
     * @return list of spam messages
     */
    @Override
    public List<Message> update(List<Message> messages){
        List<Message> spamList = messages
                .stream()
                .filter(m -> m.getBody().toCharArray().length > 20)
                .collect(Collectors.toList());
        messages.removeIf(m -> m.getBody().toCharArray().length > 20);

        return spamList;
    }
}
