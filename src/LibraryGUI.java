import javax.swing.*;
import java.awt.*;

public class LibraryGUI extends JFrame {

    private LibraryManager manager;

    // Text fields
    private JTextField txtBookId, txtTitle, txtAuthor;
    private JTextField txtMemberId, txtMemberName, txtPhone;
    private JTextField txtBorrowBookId, txtBorrowMemberId;
    private JTextArea displayArea;

    public LibraryGUI() {
        manager = new LibraryManager(50);

        setTitle("Library Management System");
        setSize(700, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ========== TOP PANEL - Title ==========
        JLabel titleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(titleLabel, BorderLayout.NORTH);

        // ========== CENTER PANEL ==========
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        // ----- LEFT SIDE: Forms -----
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        // --- Add Book Section ---
        formPanel.add(createSectionLabel("Add New Book"));
        txtBookId = createTextField();
        txtTitle = createTextField();
        txtAuthor = createTextField();
        formPanel.add(createLabeledField("Book ID:", txtBookId));
        formPanel.add(createLabeledField("Title:", txtTitle));
        formPanel.add(createLabeledField("Author:", txtAuthor));
        JButton btnAddBook = new JButton("Add Book");
        formPanel.add(btnAddBook);
        formPanel.add(Box.createVerticalStrut(15));

        // --- Register Member Section ---
        formPanel.add(createSectionLabel("Register Member"));
        txtMemberId = createTextField();
        txtMemberName = createTextField();
        txtPhone = createTextField();
        formPanel.add(createLabeledField("Member ID:", txtMemberId));
        formPanel.add(createLabeledField("Name:", txtMemberName));
        formPanel.add(createLabeledField("Phone:", txtPhone));
        JButton btnRegister = new JButton("Register Member");
        formPanel.add(btnRegister);
        formPanel.add(Box.createVerticalStrut(15));

        // --- Borrow / Return Section ---
        formPanel.add(createSectionLabel("Borrow / Return Book"));
        txtBorrowBookId = createTextField();
        txtBorrowMemberId = createTextField();
        formPanel.add(createLabeledField("Book ID:", txtBorrowBookId));
        formPanel.add(createLabeledField("Member ID:", txtBorrowMemberId));

        JPanel borrowReturnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnBorrow = new JButton("Borrow Book");
        JButton btnReturn = new JButton("Return Book");
        borrowReturnPanel.add(btnBorrow);
        borrowReturnPanel.add(btnReturn);
        formPanel.add(borrowReturnPanel);

        centerPanel.add(formPanel);

        // ----- RIGHT SIDE: Display Area -----
        JPanel displayPanel = new JPanel(new BorderLayout(5, 5));
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        displayPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons under the display
        JPanel viewButtons = new JPanel(new FlowLayout());
        JButton btnViewBooks = new JButton("View All Books");
        JButton btnViewMembers = new JButton("View All Members");
        JButton btnViewAvailable = new JButton("View Available Books");
        viewButtons.add(btnViewBooks);
        viewButtons.add(btnViewMembers);
        viewButtons.add(btnViewAvailable);
        displayPanel.add(viewButtons, BorderLayout.SOUTH);

        centerPanel.add(displayPanel);
        add(centerPanel, BorderLayout.CENTER);

        // ========== EVENT LISTENERS ==========

        // Add Book
        btnAddBook.addActionListener(e -> {
            String id = txtBookId.getText().trim();
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();

            if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                showMessage("Please fill in all book fields.");
                return;
            }

            Book book = new Book(id, title, author);
            if (manager.addBook(book)) {
                showMessage("Book added successfully!");
                txtBookId.setText("");
                txtTitle.setText("");
                txtAuthor.setText("");
            } else {
                showMessage("Failed to add book. ID may already exist or library is full.");
            }
        });

        // Register Member
        btnRegister.addActionListener(e -> {
            String id = txtMemberId.getText().trim();
            String name = txtMemberName.getText().trim();
            String phone = txtPhone.getText().trim();

            if (id.isEmpty() || name.isEmpty() || phone.isEmpty()) {
                showMessage("Please fill in all member fields.");
                return;
            }

            Member member = new Member(id, name, phone);
            if (manager.registerMember(member)) {
                showMessage("Member registered successfully!");
                txtMemberId.setText("");
                txtMemberName.setText("");
                txtPhone.setText("");
            } else {
                showMessage("Failed to register member. ID may already exist or limit reached.");
            }
        });

        // Borrow Book
        btnBorrow.addActionListener(e -> {
            String bookId = txtBorrowBookId.getText().trim();
            String memberId = txtBorrowMemberId.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty()) {
                showMessage("Please enter both Book ID and Member ID.");
                return;
            }

            String result = manager.borrowBook(bookId, memberId);
            showMessage(result);
        });

        // Return Book
        btnReturn.addActionListener(e -> {
            String bookId = txtBorrowBookId.getText().trim();
            String memberId = txtBorrowMemberId.getText().trim();

            if (bookId.isEmpty() || memberId.isEmpty()) {
                showMessage("Please enter both Book ID and Member ID.");
                return;
            }

            String result = manager.returnBook(bookId, memberId);
            showMessage(result);
        });

        // View All Books
        btnViewBooks.addActionListener(e -> {
            Book[] books = manager.getAllBooks();
            if (books.length == 0) {
                displayArea.setText("No books in the library yet.");
                return;
            }
            StringBuilder sb = new StringBuilder("===== ALL BOOKS =====\n\n");
            for (Book b : books) {
                sb.append(b.toString()).append("\n");
            }
            displayArea.setText(sb.toString());
        });

        // View All Members
        btnViewMembers.addActionListener(e -> {
            Member[] members = manager.getAllMembers();
            if (members.length == 0) {
                displayArea.setText("No members registered yet.");
                return;
            }
            StringBuilder sb = new StringBuilder("===== ALL MEMBERS =====\n\n");
            for (Member m : members) {
                sb.append(m.toString()).append("\n");
            }
            displayArea.setText(sb.toString());
        });

        // View Available Books
        btnViewAvailable.addActionListener(e -> {
            displayArea.setText("===== AVAILABLE BOOKS =====\n\n" + manager.listAvailableBooks());
        });
    }

    // ========== HELPER METHODS ==========

    private JLabel createSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        return label;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField(15);
        return tf;
    }

    private JPanel createLabeledField(String labelText, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // ========== MAIN METHOD ==========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}