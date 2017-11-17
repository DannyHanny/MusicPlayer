package Model;

public class Albums {
    private int artistId;
    private int albumId;
    private String albumName;

    public Albums(int artistId, int albumId, String albumName) {
        this.artistId = artistId;
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }


    @Override
    public String toString() {
        return albumName;

    }
}


