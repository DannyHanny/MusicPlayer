package Model;

public class TrackPlaylists {

    private int TrackId;
    private int PlaylistId;

    public TrackPlaylists(int trackId, int playlistId) {
        TrackId = trackId;
        PlaylistId = playlistId;
    }

    public int getTrackId() {
        return TrackId;
    }

    public void setTrackId(int trackId) {
        TrackId = trackId;
    }

    public int getPlaylistId() {
        return PlaylistId;
    }

    public void setPlaylistId(int playlistId) {
        PlaylistId = playlistId;
    }

}
