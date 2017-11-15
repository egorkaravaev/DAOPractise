package dao;


import entity.Author;
import util.App;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends AbstractDAO<Long,Author>{
    private static final String SELECT_AID = "SELECT AId FROM Author WHERE Firstname = ? AND Lastname = ?";
    private static final String INSERT_AUTHOR = "INSERT INTO Author (Firstname,Lastname) VALUES (?,?)";
    private static final String SELECT_ALL_AUTHORS = "SELECT * FROM Author WHERE Firstname IS NOT NULL";
    private static final String SELECT_BY_ID = "SELECT * FROM Author WHERE AId = ?";
    private static final String UPDATE_AUTHOR = "UPDATE Author SET Firstname = ?, Lastname = ? WHERE AId = ?";


    public void create(Author author) throws SQLException {

/** Checking the existence of the author in the database.
 *  If such a author already exists, then the author is not added, if not, then a new author is created.
 */
        Connection connection = App.getCp().retrieve();
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            ps = connection.prepareStatement(SELECT_AID);
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ResultSet selectAIdResult = ps.executeQuery();
            int i = 0;
            int AId = 0;
            while (selectAIdResult.next()) {
                AId = selectAIdResult.getInt("AId");
                i++;
            }
            if (i > 0) {
                System.out.println("This author already exists!");
                author.setId(AId);
            } else {
                ps2 = connection.prepareStatement(INSERT_AUTHOR);
                ps2.setString(1, author.getFirstName());
                ps2.setString(2, author.getLastName());
                ps2.executeUpdate();
                ResultSet generatedKey = ps2.getGeneratedKeys();
                if (generatedKey.next()) {
                    author.setId(generatedKey.getLong(1));
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
                App.getCp().putBack(connection);
            }
        }
    }

    public List<Author> getAll() throws SQLException {
        Connection connection = App.getCp().retrieve();
        List<Author> authorList = new ArrayList<Author>();

        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet resultSet = s.executeQuery(SELECT_ALL_AUTHORS);
            while (resultSet.next()){
                Author author = new Author();
                author.setId(resultSet.getLong(1));
                author.setFirstName(resultSet.getString(2));
                author.setLastName(resultSet.getString(3));
                authorList.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (s != null) {
                s.close();
            }
            if (connection != null) {
                App.getCp().putBack(connection);
            }
        }
        return authorList;
    }

    public Author getById(Long id) throws SQLException {
        Connection connection = App.getCp().retrieve();
        PreparedStatement ps = null;
        Author author = new Author();
        try {
            ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                author.setId(resultSet.getLong(1));
                author.setFirstName(resultSet.getString(2));
                author.setLastName(resultSet.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                App.getCp().putBack(connection);
            }
        }
        return author;
    }

    public void update(Author author) throws SQLException {
        Connection connection = App.getCp().retrieve();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(UPDATE_AUTHOR);
            ps.setString(1,author.getFirstName());
            ps.setString(2,author.getLastName());
            ps.setLong(3, author.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                App.getCp().putBack(connection);
            }
        }
    }

    public void delete(Author author) throws SQLException {

    }
}
