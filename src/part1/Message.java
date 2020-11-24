package part1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
    private String subject;
    private String body;
    private String sender;
    private String receiver;
    private Date sentTime;

    public Message(String subject, String body, String sender, String receiver) {
        this.subject = subject;
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        this.sentTime = new Date(System.currentTimeMillis());
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public Date getSentTime() {
        return sentTime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "subject='" + subject + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sentTime=" + sentTime +
                ", body='" + body + '\'' +
                '}';
    }
}
