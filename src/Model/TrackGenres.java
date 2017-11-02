package Model;

public class TrackGenres {
    private int trackId;
    private int genreId;

    public TrackGenres(int trackId, int genreId) {
        this.trackId = trackId;
        this.genreId = genreId;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
}
