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

}
