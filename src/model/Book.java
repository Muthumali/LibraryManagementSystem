package model;

public class Book {
    private String bookId;
    private String bookName ;
    private String  writterName ;
    private String bookLanguage ;
    private Double price ;
    private String type ;
    private String addDate ;

    public Book() {
    }

    public Book(String bookId, String bookName, String writterName, String bookLanguage, Double price, String type, String addDate) {
        this.setBookId(bookId);
        this.setBookName(bookName);
        this.setWritterName(writterName);
        this.setBookLanguage(bookLanguage);
        this.setPrice(price);
        this.setType(type);
        this.setAddDate(addDate);
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getWritterName() {
        return writterName;
    }

    public void setWritterName(String writterName) {
        this.writterName = writterName;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId='" + bookId + '\'' +
                ", bookName='" + bookName + '\'' +
                ", writterName='" + writterName + '\'' +
                ", bookLanguage='" + bookLanguage + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", addDate='" + addDate + '\'' +
                '}';
    }
}
