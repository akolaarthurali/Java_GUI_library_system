public class LibraryManager {
    private Book[] books;
    private int count;

    public LibraryManager(int capacity) {
        books = new Book[capacity]; // Array of fixed size
        count = 0;
    }

    // Add a new book to the array
    public boolean addBook(Book book) {
        if (count < books.length) {
            books[count] = book;
            count++;
            return true;
        }
        return false; // Array full
    }

    // Return current active books
    public Book[] getAllBooks() {
        Book[] currentBooks = new Book[count];
        for (int i = 0; i < count; i++) {
            currentBooks[i] = books[i];
        }
        return currentBooks;
    }

    // Search for a book by ID using a loop
    public Book searchBookById(String bookId) {
        for (int i = 0; i < count; i++) {
            if (books[i].getBookId().equalsIgnoreCase(bookId)) {
                return books[i];
            }
        }
        return null;
    }

    // Toggle borrow/return status
    public boolean toggleBorrowStatus(String bookId) {
        Book book = searchBookById(bookId);
        if (book != null) {
            book.setBorrowed(!book.isBorrowed());
            return true;
        }
        return false;
    }

    public int getCount() { return count; }
}