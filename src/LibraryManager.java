public class LibraryManager {

    private Book[] books;
    private Member[] members;
    private int bookCount;
    private int memberCount;
    private int capacity;

    // Constructor (this)
    public LibraryManager(int capacity) {
        this.capacity = capacity;
        this.books = new Book[capacity];
        this.members = new Member[capacity];
        this.bookCount = 0;
        this.memberCount = 0;
    }

    // ==================== BOOK METHODS ====================
// 1. Addition of a book
    public boolean addBook(Book book) {
        if (bookCount >= capacity) {
            return false;
        }
        if (findBookById(book.getBookId()) != null) {
            return false;
        }
        books[bookCount] = book;
        bookCount++;
        return true;
    }
// 2. Look for the book by ID 
    public Book findBookById(String bookId) {
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getBookId().equalsIgnoreCase(bookId)) {
                return books[i];
            }
        }
        return null;
    }
// 3. Go through the list of books 
    public Book[] getAllBooks() {
        Book[] result = new Book[bookCount];
        for (int i = 0; i < bookCount; i++) {
            result[i] = books[i];
        }
        return result;
    }
// 4. The list of books available 
    public String listAvailableBooks() {
        if (bookCount == 0) {
            return "No books in the library.";
        }
        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for (int i = 0; i < bookCount; i++) {
            if (!books[i].isBorrowed()) {
                sb.append(books[i].toString()).append("\n");
                found = true;
            }
        }
        return found ? sb.toString() : "No available books at the moment.";
    }

    // ==================== MEMBER METHODS ====================

    public boolean registerMember(Member member) {
        if (memberCount >= capacity) {
            return false;
        }
        if (findMemberById(member.getMemberId()) != null) {
            return false;
        }
        members[memberCount] = member;
        memberCount++;
        return true;
    }

    public Member findMemberById(String memberId) {
        for (int i = 0; i < memberCount; i++) {
            if (members[i].getMemberId().equalsIgnoreCase(memberId)) {
                return members[i];
            }
        }
        return null;
    }

    public Member[] getAllMembers() {
        Member[] result = new Member[memberCount];
        for (int i = 0; i < memberCount; i++) {
            result[i] = members[i];
        }
        return result;
    }

    // ==================== BORROW & RETURN ====================

    public String borrowBook(String bookId, String memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book == null) {
            return "Error: Book with ID " + bookId + " not found.";
        }
        if (member == null) {
            return "Error: Member with ID " + memberId + " not found.";
        }
        if (book.isBorrowed()) {
            return "Error: This book is already borrowed.";
        }

        book.setBorrowed(true);
        member.incrementBorrowed();
        return "Success: " + member.getName() + " borrowed \"" + book.getTitle() + "\".";
    }

    public String returnBook(String bookId, String memberId) {
        Book book = findBookById(bookId);
        Member member = findMemberById(memberId);

        if (book == null) {
            return "Error: Book with ID " + bookId + " not found.";
        }
        if (member == null) {
            return "Error: Member with ID " + memberId + " not found.";
        }
        if (!book.isBorrowed()) {
            return "Error: This book is not currently borrowed.";
        }

        book.setBorrowed(false);
        member.decrementBorrowed();
        return "Success: \"" + book.getTitle() + "\" has been returned by " + member.getName() + ".";
    }

    // Optional simple toggle (kept for compatibility)
    public boolean toggleBorrowStatus(String bookId) {
        Book book = findBookById(bookId);
        if (book == null) {
            return false;
        }
        book.setBorrowed(!book.isBorrowed());
        return true;
    }

    public int getBookCount() {
        return bookCount;
    }

    public int getMemberCount() {
        return memberCount;
    }
}