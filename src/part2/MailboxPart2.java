package part2;

import part1.MailStore;
import part1.Mailbox;
import part1.Message;
import part1.User;

import java.util.ArrayList;
import java.util.List;

public class MailboxPart2 extends Mailbox {

    private List<MailboxFilter> observers = new ArrayList<MailboxFilter>();
    private List<Message> spamList = new ArrayList<Message>();

    public MailboxPart2(User account, MailStore mailStore) {
        super(account, mailStore);
    }

    public List<Message> listSpamMail(){
        return spamList;
    }

    public void attach(MailboxFilter observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (MailboxFilter observer : observers) {
            spamList.addAll(observer.update(messages));
        }
    }

    @Override
    public void updateMail() {
        super.updateMail();
        notifyAllObservers();
    }

    public List<MailboxFilter> getObservers() {
        return observers;
    }
}
