package Whatever;

import Client.Profile;

import java.io.Serial;
import java.io.Serializable;

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
}
