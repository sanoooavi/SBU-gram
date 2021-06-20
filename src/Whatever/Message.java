package Whatever;

import Client.Profile;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -6263967550947218039L;
    String sender;
    String receiver;
    String textMessage;
    String time;
    public Message(String textMessage, String time) {
        this.textMessage = textMessage;
        this.time = time;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public String getTime() {
        return time;
    }

    public String getReceiver() {
        return receiver;
    }

    @Override
    public String toString() {
        return "Message{" + "sender='" + sender + '\'' + ", receiver='" + receiver + '\'' + ", textMessage='" + textMessage + '\'' + ", time='" + time + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(sender, message.sender) && Objects.equals(receiver, message.receiver) && Objects.equals(textMessage, message.textMessage) && Objects.equals(time, message.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, textMessage, time);
    }
}
