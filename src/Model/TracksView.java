package Model;

import javafx.beans.property.SimpleStringProperty;

public class TracksView {
    private int trackId;
    private final SimpleStringProperty trackName;
    private final SimpleStringProperty albumName;
    private final SimpleStringProperty artistName;
    private final SimpleStringProperty length;

    public TracksView(int trackId, String trackName, String albumName, String artistName, String length) {
        this.trackId = trackId;
        this.trackName = new SimpleStringProperty(trackName);
        this.albumName = new SimpleStringProperty(albumName);
        this.artistName = new SimpleStringProperty(artistName);
        this.length = new SimpleStringProperty(length);
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
}
