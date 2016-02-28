package keymix;

import javafx.scene.media.AudioClip;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Created by Caleb on 2/28/2016.
 */
public class Sample implements Serializable{
    private String URI;
    private transient AudioClip clip;
    private transient boolean playing;

    public Sample (String uri) {
        this.URI = uri;
        this.clip = new AudioClip(uri);
        this.playing = false;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
    public String getURI() {
        return URI;
    }
    public AudioClip getClip() {
        if (clip == null) {
            clip = new AudioClip(URI);
        }
        return clip;
    }
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sample sample = (Sample) o;

        return URI.equals(sample.URI);
    }

    @Override
    public int hashCode() {
        return URI.hashCode();
    }

    public String toString() {
        return URI;
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.clip = new AudioClip(this.URI);
    }
}
