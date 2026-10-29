public class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isBorrowed;

    // Constructor
    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isBorrowed = false; // Default status is available
    }

    // Getters and Setters (Encapsulation)
    public String getBookId() { return bookId; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isBorrowed() { return isBorrowed; }

    public void setBorrowed(boolean borrowed) { 
        this.isBorrowed = borrowed; 
    }

    @Override
    public String toString() {
        return "ID: " + bookId + " | Title: " + title + " | Author: " + author + 
               " | Status: " + (isBorrowed ? "Borrowed" : "Available");
    }
}