CREATE TABLE Albums
(
  AlbumID   INTEGER
    PRIMARY KEY
  AUTOINCREMENT,
  AlbumName VARCHAR,
  ArtistID  INTEGER
);

CREATE TABLE Artists
(
  ArtistID   INTEGER
    PRIMARY KEY
  AUTOINCREMENT,
  ArtistName VARCHAR
);

ALTER TABLE Albums
  ADD foreign KEY(ArtistID) REFERENCES Artists
ON UPDATE cascade ON DELETE CASCADE;

CREATE TABLE Genres
(
  GenreID   INTEGER
    PRIMARY KEY
  AUTOINCREMENT,
  GenreName VARCHAR
);

CREATE TABLE Playlists
(
  PlaylistID   INTEGER
    PRIMARY KEY
  AUTOINCREMENT,
  PlaylistName VARCHAR
);

CREATE TABLE "Track Genres"
(
  TrackID INTEGER,
  GenreID INTEGER
    REFERENCES Genres
      ON UPDATE CASCADE
      ON DELETE CASCADE,
  PRIMARY KEY (TrackID, GenreID)
);

CREATE TABLE "Track Playlists"
(
  TrackID    INTEGER,
  PlaylistID INTEGER
    REFERENCES Playlists
      ON UPDATE CASCADE
      ON DELETE CASCADE,
  PRIMARY KEY (TrackID, PlaylistID)
);

CREATE TABLE Tracks
(
  TrackID      INTEGER
    PRIMARY KEY
  AUTOINCREMENT,
  AlbumID      INTEGER
    REFERENCES Albums
      ON UPDATE CASCADE
      ON DELETE CASCADE,
  TrackName    VARCHAR,
  Length       VARCHAR,
  Bitrate      INTEGER,
  TrackListing INTEGER,
  Year         VARCHAR,
  FileUri      VARCHAR
);

ALTER TABLE "Track Genres"
  ADD foreign KEY(TrackID) REFERENCES Tracks
ON UPDATE cascade ON DELETE CASCADE;

ALTER TABLE "Track Playlists"
  ADD foreign KEY(TrackID) REFERENCES Tracks
ON UPDATE cascade ON DELETE CASCADE;


