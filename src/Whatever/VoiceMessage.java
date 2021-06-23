package Whatever;

import java.io.Serial;

public class VoiceMessage extends Message{
    @Serial
    private static final long serialVersionUID = -1680096020448840780L;
    byte[]voice;
    public VoiceMessage(byte[]voice,Long timeMilli,String time) {
        super.time=time;
        this.voice=voice;
        super.timeMilli=timeMilli;
    }

    public byte[] getVoice() {
        return voice;
    }
}
