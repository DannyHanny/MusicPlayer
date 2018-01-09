package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.media.MediaPlayer;

import java.util.Optional;

public class TracksView {
    private int trackId;
    private final SimpleStringProperty trackName;
    private final SimpleStringProperty albumName;
    private final SimpleStringProperty artistName;
    private final SimpleStringProperty length;
    private final SimpleStringProperty fileUri;

    private MediaPlayer mediaPlayer;

    public TracksView(int trackId, String trackName, String albumName, String artistName, String length, String fileUri) {
        this.trackId = trackId;
        this.trackName = new SimpleStringProperty(trackName);
        this.albumName = new SimpleStringProperty(albumName);
        this.artistName = new SimpleStringProperty(artistName);
        this.length = new SimpleStringProperty(length);
        this.fileUri = new SimpleStringProperty(fileUri);
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public String getTrackName() {
        return trackName.get();
    }

    public void setTrackName(String trackName) {
        this.trackName.set(trackName);
    }

    public String getAlbumName() {
        return albumName.get();
    }


    public void setAlbumName(String albumName) {
        this.albumName.set(albumName);
    }

    public String getArtistName() {
        return artistName.get();
    }

    public void setArtistName(String artistName) {
        this.artistName.set(artistName);
    }

    public String getLength() {
        return length.get();
    }

    public void setLength(String length) {
        this.length.set(length);
    }

    public String getFileUri() { return fileUri.get(); }

    public void setFileUri(String fileUri) {
        this.fileUri.set(fileUri);
    }

    public Optional<MediaPlayer> getMediaPlayer() { return Optional.ofNullable(this.mediaPlayer); }

    public void setMediaPlayer(MediaPlayer mediaPlayer) { this.mediaPlayer = mediaPlayer; }
}
