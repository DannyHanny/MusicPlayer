package Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PlaylistsService {

    public static void selectAll(List<Playlists> targetList, DatabaseConnection database) {

        PreparedStatement statement = database.newStatement("SELECT PlaylistID, PlaylistName FROM Playlists ORDER BY PlaylistID");

        try {
            if (statement != null) {

                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        targetList.add(new Playlists(results.getInt("PlaylistID"), results.getString("PlaylistName")));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
    }

    public static Result<Playlists> CreatePlaylist(String name) {
        return new Result(new Playlists(0, name));
    }

}