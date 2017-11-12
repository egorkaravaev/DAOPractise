package dao;

import entity.Genre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenreDAO extends AbstractDAO<Long, Genre>{
    private static final String SELECT_GID = "SELECT GId FROM Genre WHERE GName = ?";
    private static final String INSERT_GENRE = "INSERT INTO Genre (GName) VALUES (?)";
    private static final String SELECT_ALL_GENRES = "SELECT * FROM Genre";
    private static final String SELECT_BY_ID = "SELECT * FROM Genre WHERE GId = ?";
    private static final String UPDATE_GENRE = "UPDATE Genre SET GName = ? WHERE GId = ?";

    Connection connection = getConnection();

    public void create(Genre genre) throws SQLException {

/** Checking the existence of the author in the database.
 *  If such a author already exists, then the author is not added, if not, then a new author is created.
 */
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = connection.prepareStatement(SELECT_GID);
            ps.setString(1, genre.getName());
            ResultSet selectGIdResult = ps.executeQuery();
            int i = 0;
            int GId = 0;
            while (selectGIdResult.next()) {
                GId = selectGIdResult.getInt("GId");
                i++;
            }
            if (i > 0) {
                System.out.println("Такой жанр уже есть");
                genre.setId(GId);
            } else {
                System.out.println("Такого жанра ещё нет");
                ps2 = connection.prepareStatement(INSERT_GENRE);
                ps2.setString(1, genre.getName());
                ps2.executeUpdate();
                ResultSet generatedKey = ps2.getGeneratedKeys();
                if (generatedKey.next()) {
                    genre.setId(generatedKey.getLong(1));
                }
            }
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (ps2 != null) {
                ps2.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<Genre> getAll() throws SQLException {
        List<Genre> genreList = new ArrayList<Genre>();

        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet resultSet = s.executeQuery(SELECT_ALL_GENRES);
            while (resultSet.next()){
                Genre genre = new Genre();
                genre.setId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));
                genreList.add(genre);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return genreList;
    }

    public Genre getById(Long id) throws SQLException {
        PreparedStatement ps = null;
        Genre genre = new Genre();
        try {
            ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                genre.setId(resultSet.getLong(1));
                genre.setName(resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        return genre;
    }

    public void update(Genre genre) throws SQLException {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(UPDATE_GENRE);
            ps.setString(1,genre.getName());
            ps.setLong(2,genre.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void delete(Genre genre) throws SQLException {

    }
}
