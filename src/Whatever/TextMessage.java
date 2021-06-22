package Whatever;

import java.io.Serial;

public class TextMessage extends Whatever.Message {
    @Serial
    private static final long serialVersionUID = 1579731121541116147L;
    String text;
    public TextMessage(String text,Long timeMilli) {
        this.text = text;
        super.timeMilli=timeMilli;
    }

    public String getText() {
        return text;
    }
}
