package Model;

import javafx.beans.property.SimpleStringProperty;

public class TracksView {
    private int trackId;
    private final SimpleStringProperty trackName;
    private final SimpleStringProperty albumName;
    private final SimpleStringProperty artistName;

    public TracksView(int trackId, SimpleStringProperty trackName, SimpleStringProperty albumName, SimpleStringProperty artistName) {
        this.trackId = trackId;
        this.trackName = trackName;
        this.albumName = albumName;
        this.artistName = artistName;
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
}
