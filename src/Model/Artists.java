package Model;

public class Artists {
    private int ArtistId;
    private String ArtistName;

    public Artists(int artistId, String artistName) {
        ArtistId = artistId;
        ArtistName = artistName;
    }

    public int getArtistId() {
        return ArtistId;
    }

    public void setArtistId(int artistId) {
        ArtistId = artistId;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    @Override
    public String toString() {
        return "Artists{" +
                "ArtistId=" + ArtistId +
                ", ArtistName='" + ArtistName + '\'' +
                '}';
    }
}
