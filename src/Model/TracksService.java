package Model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TracksService {

    public static void selectAll(List<Tracks> targetList, DatabaseConnection database) {

        PreparedStatement statement = database.newStatement("SELECT TrackID, AlbumID, TrackName, Length, Bitrate, TrackListing, Year FROM main.Tracks ORDER BY TrackID");

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

    public static List<TracksView> selectForTable(Playlists playlist, DatabaseConnection database){
        PreparedStatement statement = database.newStatement(
            "SELECT Tracks.TrackID As 'id', Tracks.TrackName As 'track', Albums.AlbumName As 'album', Artists.ArtistName As 'artist', Tracks.Length FROM Tracks " +
                    "INNER JOIN Albums ON Albums.AlbumID = Tracks.AlbumID " +
                    "INNER JOIN Artists ON Artists.ArtistID = Albums.ArtistID " +
                    "INNER JOIN 'Track Playlists' ON 'Track Playlists'.TrackID = Tracks.TrackID " +
                    "WHERE 'Track Playlists'.PlaylistID = ?"
        );
        List<TracksView> tracks = new ArrayList<>();
        try {
            if (statement != null) {

                statement.setInt(1, playlist.getPlaylistId());
                ResultSet results = database.executeQuery(statement);

                if (results != null) {
                    while (results.next()) {
                        tracks.add(new TracksView(
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
        return tracks;
    }

    public static Result<Tracks> CreateTrack(String absolutePath, String title, String albumName, String artistName, DatabaseConnection database) {
        try {
            Artists artist = GetArtistByName(artistName, database);
            Albums album = GetAlbumByArtistAndName(artist.getArtistId(), albumName, database);
            PreparedStatement insert = database.newStatement(
                    "INSERT INTO Tracks (AlbumID, TrackName, Length, Bitrate, TrackListing, Year) VALUES (?, ?, ?, ?, ?, ?)");
            insert.setInt(1,  album.getAlbumId());
            insert.setString(2, title);
            insert.setString(3, "unknown");
            insert.setInt(4, 0);
            insert.setInt(5, 0);
            insert.setString(6, "2017");
            insert.execute();
            int id = DatabaseConnection.getGeneratedId(insert);
            return new Result(new Tracks(id, album.getAlbumId(), title, "unknown", 0, 0, "2017"));
        } catch (SQLException e) {
            e.printStackTrace();
            return Result.Fail;
        }

    }

    private static Artists GetArtistByName(String name, DatabaseConnection database) throws SQLException {
        PreparedStatement query = database.newStatement(
                "SELECT ArtistID, ArtistName FROM Artists where lower(ArtistName) = lower(?)");
        query.setString(1, name);
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
            return new Artists(rs.getInt("ArtistId"), rs.getString("ArtistName"));
        } else {
            PreparedStatement insert = database.newStatement(
                    "INSERT INTO Artists (ArtistName) VALUES (?)");
            insert.setString(1, name);
            insert.execute();
            int id = DatabaseConnection.getGeneratedId(insert);
            return new Artists(id, name);
        }
    }

    private static Albums GetAlbumByArtistAndName(int artistId, String name, DatabaseConnection database) throws SQLException {
        PreparedStatement query = database.newStatement(
                "SELECT AlbumID, AlbumName FROM Albums where ArtistID = ? AND  lower(AlbumName) = lower(?)");
        query.setInt(1, artistId);
        query.setString(2, name);
        ResultSet rs = query.executeQuery();
        if (rs.next()) {
            return new Albums(artistId, rs.getInt("AlbumID"), rs.getString("AlbumName"));
        } else {
            PreparedStatement insert = database.newStatement(
                    "INSERT INTO Albums (ArtistID, AlbumName) VALUES (?, ?)");
            insert.setInt(1, artistId);
            insert.setString(2, name);
            insert.execute();
            int id = DatabaseConnection.getGeneratedId(insert);
            return new Albums(artistId, id, name);
        }
    }

}
