package part1;

import java.util.Date;

public class Message {
    private final String sender;
    private final String receiver;
    private final Date sentTime;
    private final String subject;
    private String body;

    /**
     * Constructor
     * @param sender -> username of user that send message
     * @param receiver -> username of user that receive message
     * @param subject -> message subject
     * @param body -> message body
     */
    public Message(String sender, String receiver, String subject, String body) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.body = body;
        this.sentTime = new Date(System.currentTimeMillis());
    }

    /**
     * Constructor
     * @param sender -> username of user that send message
     * @param receiver -> username of user that receive message
     * @param sentTime -> time the message is send
     * @param subject -> message subject
     * @param body -> message body
     */
    public Message(String sender, String receiver, Date sentTime, String subject, String body) {
        this.subject = subject;
        this.body = body;
        this.sender = sender;
        this.receiver = receiver;
        this.sentTime = sentTime;
    }

    /**
     * Returns subject of message
     * @return subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Returns body of message
     * @return body
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns sender username
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns receiver username
     * @return receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Returns time the message is send
     * @return sent time
     */
    public Date getSentTime() {
        return sentTime;
    }

    /**
     * Method that print message
     * @return print message
     */
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

    /**
     * Method that print message in file structure
     * @return print message
     */
    public String toStringFile() {
        return sender + ';' + receiver + ';' + sentTime + ';' + subject + ";" + body + ";\n";
    }

    /**
     * Method that print message in redis structure
     * @return print message
     */
    public String toStringRedis() {
        return sender + ';' + receiver + ';' + sentTime + ';' + subject + ";" + body + ";";
    }

    /**
     * Set the message body with the string passed by parameter
     * @param body -> message body
     */
    public void setBody(String body) {
        this.body = body;
    }
}
