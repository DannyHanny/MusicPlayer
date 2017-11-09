package Model;

public class Albums {
    private int trackId;
    private int albumId;
    private String albumName;

    public Albums(int trackId, int albumId, String albumName) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public int getTrackId() {
        return trackId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "Albums{" +
                "trackId=" + trackId +
                ", albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                '}';
    }
}


