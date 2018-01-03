package Model;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaylistsService {

    public static List<Playlists> selectAll(DatabaseConnection database) {

        PreparedStatement statement = database.newStatement("SELECT PlaylistID, PlaylistName FROM Playlists ORDER BY PlaylistID");
        List<Playlists> playlists = new ArrayList<>();

        try {
            if (statement != null) {
                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        playlists.add(new Playlists(results.getInt("PlaylistID"), results.getString("PlaylistName")));
                    }
                }
            }
        } catch (SQLException resultsException) {
            System.out.println("Database select all error: " + resultsException.getMessage());
        }
        return playlists;
    }

    public static Result<Playlists> CreatePlaylist(String name, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement(
                "INSERT INTO Playlists (PlaylistName) VALUES (?)");
        try {
            statement.setString(1, name);
            statement.execute();
            int id = DatabaseConnection.getGeneratedId(statement);
            return new Result(new Playlists(id, name));
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.Fail;
        }
    }

    public static Result AddTrackToPlaylist(Playlists playlist, Tracks track, DatabaseConnection database) {
        PreparedStatement statement = database.newStatement(
                "INSERT INTO 'Track Playlists' (TrackID, PlaylistID) VALUES (?, ?)");
        try {
            statement.setInt(1, track.getTrackId());
            statement.setInt(2, playlist.getPlaylistId());
            statement.execute();
            return Result.Success;
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.Fail;
        }
    }

}