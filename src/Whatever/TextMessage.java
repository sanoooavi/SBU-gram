package Whatever;

import java.io.Serial;
import java.util.Objects;

public class TextMessage extends Whatever.Message {
    @Serial
    private static final long serialVersionUID = 1579731121541116147L;
    String text;

    public TextMessage(String text, Long timeMilli, String time) {
        this.text = text;
        super.time = time;
        super.timeMilli = timeMilli;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextMessage)) return false;
        TextMessage that = (TextMessage) o;
        return Objects.equals(text, that.text) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, receiver);
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return   sender +" : " + text ;
    }
}
