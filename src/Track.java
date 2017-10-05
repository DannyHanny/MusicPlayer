import javafx.beans.property.SimpleStringProperty;

public class Track {
    private final SimpleStringProperty trackTitle;
    private final SimpleStringProperty album;
    private final SimpleStringProperty artist;

    public Track(String trackTitle, String album, String artist) {
        this.trackTitle = new SimpleStringProperty(trackTitle);
        this.album = new SimpleStringProperty(album);
        this.artist = new SimpleStringProperty(artist);
    }

    public String getTrackTitle() { return trackTitle.get(); }
    public void setTrackTitle(String trackTitle) { this.trackTitle.set(trackTitle); }

    public String getAlbum() { return album.get(); }
    public void setAlbum(String album) { this.album.set(album); }

    public String getArtist() { return artist.get(); }
    public void setArtist(String artist) { this.artist.set(artist); }
}