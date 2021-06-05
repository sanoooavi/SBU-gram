package Whatever;

import java.io.Serializable;

public class Comment implements Serializable {
    String writer;
    String text;
    public Comment(String writer, String text) {
        this.writer = writer;
        this.text = text;
    }
    @Override
    public String toString() {
        return  writer +" : "+text+"\n";
    }
}
