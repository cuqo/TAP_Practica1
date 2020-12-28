package part2;

import part1.Message;

import java.util.List;
import java.util.stream.Collectors;

public class TooLongFilter extends MailboxFilter{

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
