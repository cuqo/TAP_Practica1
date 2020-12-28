package part2;

import part1.Message;

import java.util.List;
import java.util.stream.Collectors;

public class SpamUserFilter extends MailboxFilter{

    @Override
    public List<Message> update(List<Message> messages){
        List<Message> spamList = messages
                .stream()
                .filter(m -> m.getSender().contains("spam"))
                .collect(Collectors.toList());
        messages.removeIf(m -> m.getSender().contains("spam"));

        return spamList;
    }
}