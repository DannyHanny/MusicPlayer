package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TracksService {

    public static void selectAll(List<Tracks> targetList, DatabaseConnection database) {

        PreparedStatement statement = database.newStatement("SELECT TrackID, AlbumID, TrackName, Length, Bitrate, TrackListing, Year FROM Tracks ORDER BY TrackID");

        try {
            if (statement != null) {

                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Tracks(results.getInt("TrackID"), results.getInt("AlbumID"), results.getString("TrackName"), results.getString("Length"), results.getInt("Bitrate"), results.getInt("TrackListing"), results.getString("Year")));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static void selectForTable(List<TracksView> targetList, DatabaseConnection database){
        PreparedStatement statement = database.newStatement(
            "SELECT Tracks.TrackID As 'id', Tracks.TrackName As 'track', Albums.AlbumName As 'album', Artists.ArtistName As 'artist', Tracks.Length FROM Tracks " +
                    "INNER JOIN Albums ON Albums.AlbumID = Tracks.AlbumID " +
                    "INNER JOIN Artists ON Artists.ArtistID = Albums.ArtistID"
        );

        try {
            if (statement != null) {

                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new TracksView(
                                results.getInt("id"),
                                results.getString("track"),
                                results.getString("album"),
                                results.getString("artist"),
                                results.getString("length")
                        ));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static Result<Tracks> CreateTrack(String absolutePath, String title, String album, String artist) {
        return new Result(new Tracks(100, 200, title, "unknown", 19200, 5, "2017"));
    }

}
