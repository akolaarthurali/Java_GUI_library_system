import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    private LibraryManager manager;

    private JTextField txtId, txtTitle, txtAuthor, txtSearchId;
    private JTextArea displayArea;

    public LibraryGUI() {
        manager = new LibraryManager(50); // Array capacity of 50

        setTitle("Library Management System - CSC 1102");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Top Panel - Input Form
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Add New Book"));

        inputPanel.add(new JLabel("Book ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        inputPanel.add(txtTitle);

        inputPanel.add(new JLabel("Author:"));
        txtAuthor = new JTextField();
        inputPanel.add(txtAuthor);

        JButton btnAdd = new JButton("Add Book");
        inputPanel.add(btnAdd);

        add(inputPanel, BorderLayout.NORTH);

        // Center Panel - Collection Display
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Library Collection"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel - Search & Borrow Action
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.add(new JLabel("Book ID:"));
        txtSearchId = new JTextField(8);
        actionPanel.add(txtSearchId);

        JButton btnToggle = new JButton("Borrow / Return");
        actionPanel.add(btnToggle);

        add(actionPanel, BorderLayout.SOUTH);

        // Event Listener - Add Book Button
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText().trim();
                String title = txtTitle.getText().trim();
                String author = txtAuthor.getText().trim();

                if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Book newBook = new Book(id, title, author);
                if (manager.addBook(newBook)) {
                    JOptionPane.showMessageDialog(null, "Book added successfully!");
                    txtId.setText("");
                    txtTitle.setText("");
                    txtAuthor.setText("");
                    updateDisplay();
                } else {
                    JOptionPane.showMessageDialog(null, "Library array is full!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Event Listener - Borrow/Return Toggle Button
        btnToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchId = txtSearchId.getText().trim();
                if (searchId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Enter a Book ID!", "Warning", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (manager.toggleBorrowStatus(searchId)) {
                    JOptionPane.showMessageDialog(null, "Status updated for Book ID: " + searchId);
                    txtSearchId.setText("");
                    updateDisplay();
                } else {
                    JOptionPane.showMessageDialog(null, "Book ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void updateDisplay() {
        displayArea.setText("");
        Book[] books = manager.getAllBooks();
        if (books.length == 0) {
            displayArea.setText("No books added yet.");
        } else {
            for (Book b : books) {
                displayArea.append(b.toString() + "\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}