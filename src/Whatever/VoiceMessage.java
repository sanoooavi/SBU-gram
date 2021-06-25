package Whatever;

import java.io.File;
import java.io.Serial;

public class VoiceMessage extends Message {
    @Serial
    private static final long serialVersionUID = -1680096020448840780L;
    File voice;
    String path;

    public VoiceMessage(File voice, Long timeMilli, String time) {
        super.time = time;
        this.voice = voice;
        super.timeMilli = timeMilli;
        path = voice.getAbsolutePath();
    }

    public String getPath() {
        return path;
    }

    public File getVoice() {
        return voice;
    }
}
