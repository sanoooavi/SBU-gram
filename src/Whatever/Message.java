package Whatever;

import Client.Profile;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = -6263967550947218039L;
    String sender;
    String receiver;
    Long timeMilli;
    String time;
    boolean wasSeen = false;

    public void setWasSeen(boolean wasSeen) {
        this.wasSeen = wasSeen;
    }

    public boolean isWasSeen() {
        return wasSeen;
    }

    public String getTime() {
        return time;
    }

    public String getSender() {
        return sender;
    }

    public Long getTimeMilli() {
        return timeMilli;
    }


    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }


}
