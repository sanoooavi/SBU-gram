package Whatever;

import java.io.Serializable;

/**
 * comment class is a simple class which just contains of the text of comment and the writer and just it
 */
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
