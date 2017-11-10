package Model;


public class Tracks {
    private int trackId;
    private int albumId;
    private String trackName;
    private String length;
    private int bitrate;
    private int trackListing;
    private String year;

    public Tracks(int trackId, int albumId, java.lang.String trackName, java.lang.String length, int bitrate, int trackListing, java.lang.String year) {
        this.trackId = trackId;
        this.albumId = albumId;
        this.trackName = trackName;
        this.length = length;
        this.bitrate = bitrate;
        this.trackListing = trackListing;
        this.year = year;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public java.lang.String getTrackName() {
        return trackName;
    }

    public void setTrackName(java.lang.String trackName) {
        this.trackName = trackName;
    }

    public java.lang.String getLength() {
        return length;
    }

    public void setLength(java.lang.String length) {
        this.length = length;
    }

    public int getBitrate() {
        return bitrate;
    }

    public void setBitrate(int bitrate) {
        this.bitrate = bitrate;
    }

    public int getTrackListing() {
        return trackListing;
    }

    public void setTrackListing(int trackListing) {
        this.trackListing = trackListing;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public java.lang.String toString() {
        return trackName ;
    }
}