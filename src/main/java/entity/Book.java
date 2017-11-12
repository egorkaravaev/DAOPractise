package entity;

public class Book extends Entity{
    private String name;
    private int pages;
    private String date;
    private Genre genre;
    private Author author;

    public Book(){}

    public Book(long id, String name, int pages, String date, Genre genre, Author author) {
        super(id);
        this.name = name;
        this.pages = pages;
        this.date = date;
        this.genre = genre;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Book book = (Book) o;

        if (pages != book.pages) return false;
        if (name != null ? !name.equals(book.name) : book.name != null) return false;
        if (date != null ? !date.equals(book.date) : book.date != null) return false;
        if (genre != null ? !genre.equals(book.genre) : book.genre != null) return false;
        return author != null ? author.equals(book.author) : book.author == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + pages;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (genre != null ? genre.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", pages=" + pages +
                ", date='" + date + '\'' +
                '}';
    }
}
