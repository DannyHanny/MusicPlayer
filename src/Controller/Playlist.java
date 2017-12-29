package Controller;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Dan on 08/10/2017.
 */
public class Playlist {
    private final SimpleStringProperty playlistName;
    private final SimpleStringProperty playlistDuration;

    public Playlist(String playlistName, String playlistDuration) {
        this.playlistName = new SimpleStringProperty(playlistName);
        this.playlistDuration = new SimpleStringProperty(playlistDuration);
    }

    public String getplaylistName() { return playlistName.get(); }
    public void setplaylistName(String playlistName) { this.playlistName.set(playlistName); }

    public String getplaylistDuration() { return playlistDuration.get(); }
    public void setPlaylistDuration(String playlistDuration) {this.playlistDuration.set(playlistDuration);}
}
