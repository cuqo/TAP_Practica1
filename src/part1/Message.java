package part1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
    private String sender;
    private String receiver;
    private Date sentTime;
    private String subject;
    private String body;

    public Message(String sender, String receiver, String subject, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
        this.sentTime = new Date(System.currentTimeMillis());
    }

    public Message(String sender, String receiver, Date sentTime, String subject, String body) {
        this.subject = subject;
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        this.sentTime = sentTime;
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
                "sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", sentTime=" + sentTime +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                '}';
    }

    public String toStringFile() {
        return sender + ';' + receiver + ';' + sentTime + ';' + subject + ";" + body + ";\n";
    }
}
