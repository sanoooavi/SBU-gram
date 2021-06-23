package Whatever;

import java.io.Serial;
import java.util.Arrays;
import java.util.Objects;

public class PhotoMessage extends Message {
    @Serial
    private static final long serialVersionUID = 8272320799477134019L;
    byte[] photo;

    public PhotoMessage(byte[] photo, Long timeMilli, String time) {
        this.photo = photo;
        super.timeMilli = timeMilli;
        super.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhotoMessage)) return false;
        PhotoMessage that = (PhotoMessage) o;
        return Arrays.equals(photo, that.photo) && Objects.equals(receiver, that.receiver);
    }

    @Override
    public int hashCode() {
        //return Arrays.hashCode(photo);
        return Objects.hash(Arrays.hashCode(photo), receiver);
    }

    public byte[] getPhoto() {
        return photo;
    }
}
