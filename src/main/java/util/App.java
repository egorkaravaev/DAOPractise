package util;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.GenreDAO;
import entity.Author;
import entity.Book;
import entity.Genre;

import java.sql.SQLException;

public class App {
    private static Configuration config;
    private static ConnectionPool cp;

    public static void main(String args[]) throws SQLException {

        config = new Configuration();
        config.setMinPoolSize(5);
        config.setMaxPoolSize(20);
        config.setIdleTimeLimit(10000);
        config.setLoadFactor(20);
        config.setIncrementPortion(5);
        cp = new ConnectionPool(config);

        BookDAO bookDAO = new BookDAO();
        AuthorDAO authorDAO = new AuthorDAO();
        GenreDAO genreDAO = new GenreDAO();

        Author author = new Author(1L,"Фёдор","Достоевский");
        Genre genre = new Genre(1L,"Философия");
        Book book = new Book(1L,"Идиот", 640, "1869", genre, author);

        Book updateBook = new Book(1l,"Война и мир", 980, "1820", genre, author);
        Author updateAuthor = new Author(1L,"Лев", "Толстой");
        Genre updateGenre = new Genre(1L,"Исторический");

        bookDAO.create(book);
        for (Book b:  bookDAO.getAll()) {
            System.out.println(b.toString());
        }
        System.out.println(bookDAO.getById(6l).toString());
        bookDAO.update(updateBook);


        authorDAO.create(author);
        for (Author a: authorDAO.getAll()) {
            System.out.println(a.toString());
        }
        System.out.println(authorDAO.getById(4l).toString());
        authorDAO.update(updateAuthor);

        genreDAO.create(genre);
        for (Genre g: genreDAO.getAll()) {
            System.out.println(g.toString());
        }
        System.out.println(genreDAO.getById(4L).toString());
        genreDAO.update(updateGenre);
    }

    public static ConnectionPool getCp() {
        return cp;
    }
}
