package dao;


import entity.Book;
import util.App;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends AbstractDAO<Long, Book>{
    private static final String SELECT_BID = "SELECT BId FROM Book WHERE BName = ?";
    private static final String INSERT_BOOK = "INSERT INTO Book (BName,Pages,Date,Genre_Id,Author_Id) VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_BOOKS = "SELECT * FROM Book WHERE Bname IS NOT NULL";
    private static final String SELECT_BY_ID = "SELECT * FROM Book WHERE BId = ?";
    private static final String UPDATE_BOOK = "UPDATE Book SET BName = ?, Pages = ?, Date = ? WHERE BId = ?";
    private static final String DELETE_BOOK = "DELETE FROM Book WHERE BId = ?";


    public void create(Book book) throws SQLException{
        Connection connection = App.getCp().retrieve();

        PreparedStatement ps = null;
        try {

            GenreDAO genreDAO = new GenreDAO();
            genreDAO.create(book.getGenre());

            AuthorDAO authorDAO = new AuthorDAO();
            authorDAO.create(book.getAuthor());

/** Checking the existence of the book in the database.
 *  If such a book already exists, then the book is not added, if not, then a new book is created.
 */
            ps = connection.prepareStatement(SELECT_BID);
            ps.setString(1,book.getName());
            ResultSet selectBIdResult = ps.executeQuery();
            int i = 0;
            while(selectBIdResult.next()) {
                i++;
            }
            if(i > 0){
                System.out.println("This book already exists!");
            } else {
                ps = connection.prepareStatement(INSERT_BOOK);
                ps.setString(1,book.getName());
                ps.setInt(2,book.getPages());
                ps.setString(3,book.getDate());
                ps.setLong(4,book.getGenre().getId());
                ps.setLong(5,book.getAuthor().getId());
                ps.executeUpdate();
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
    }

    public List<Book> getAll() throws SQLException {
        Connection connection = App.getCp().retrieve();
        List<Book> bookList = new ArrayList<Book>();

        Statement s = null;
        try {
            s = connection.createStatement();
            ResultSet resultSet = s.executeQuery(SELECT_ALL_BOOKS);
            while (resultSet.next()){
                Book book = new Book();
                book.setId(resultSet.getLong(1));
                book.setName(resultSet.getString(2));
                book.setPages(resultSet.getInt(3));
                book.setDate(resultSet.getString(4));
                bookList.add(book);
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
        return bookList;
    }

    public Book getById(Long id) throws SQLException {
        Connection connection = App.getCp().retrieve();
        PreparedStatement ps = null;
        Book book = new Book();
        try {
            ps = connection.prepareStatement(SELECT_BY_ID);
            ps.setLong(1,id);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()){
                book.setId(resultSet.getLong(1));
                book.setName(resultSet.getString(2));
                book.setPages(resultSet.getInt(3));
                book.setDate(resultSet.getString(4));
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
        return book;
    }

    public void update(Book book) throws SQLException {
        Connection connection = App.getCp().retrieve();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(UPDATE_BOOK);
            ps.setString(1,book.getName());
            ps.setInt(2,book.getPages());
            ps.setString(3,book.getDate());
            ps.setLong(4, book.getId());
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

    public void delete(Book book) throws SQLException {
        Connection connection = App.getCp().retrieve();
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(DELETE_BOOK);
            ps.setLong(1,book.getId());
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
}
