package Whatever;

import java.io.Serial;

public class PhotoMessage extends Message{
    @Serial
    private static final long serialVersionUID = 8272320799477134019L;
     byte[]photo;
    public PhotoMessage(byte[] photo,Long timeMilli) {
        this.photo = photo;
        super.timeMilli=timeMilli;
    }

    public byte[] getPhoto() {
        return photo;
    }
}
