import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/*
-----------------------------------------(iOS Frosted Glass Edition)---------------------------------------
We can transform your Java Swing application into a modern frosted 
glass (glassmorphic) interface with iOS-style notification cards, rounded corners, 
soft gradients, and status pill badges—without installing any external libraries or 
build tools!
 */

public class LibraryGUI extends JFrame {
    private LibraryManager manager;
    private JTextField txtId, txtTitle, txtAuthor, txtSearchId;
    private JPanel cardContainer; // Holds iOS notification-style cards

    public LibraryGUI() {
        manager = new LibraryManager(50); // Array capacity of 50

        setTitle("Library Management System - iOS Glass Edition");
        setSize(750, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 1. Soft Gradient Background Panel
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Sleek iOS dark mode gradient
                GradientPaint gp = new GradientPaint(0, 0, new Color(30, 35, 45), getWidth(), getHeight(), new Color(60, 70, 85));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout(15, 15));
        backgroundPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(backgroundPanel);

        // 2. Title Header
        JLabel lblHeader = new JLabel("📚 Library Management", SwingConstants.CENTER);
        lblHeader.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblHeader.setForeground(Color.WHITE);
        backgroundPanel.add(lblHeader, BorderLayout.NORTH);

        // 3. Main Center Split
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        centerPanel.setOpaque(false);

        // --- LEFT SIDE: Frosted Glass Form ---
        GlassPanel inputGlass = new GlassPanel(20);
        inputGlass.setLayout(new BoxLayout(inputGlass, BoxLayout.Y_AXIS));
        inputGlass.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel lblInputTitle = new JLabel("Add New Book");
        lblInputTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblInputTitle.setForeground(Color.WHITE);
        lblInputTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputGlass.add(lblInputTitle);
        inputGlass.add(Box.createRigidArea(new Dimension(0, 10)));

        txtId = createGlassTextField("Book ID (e.g. B001)");
        txtTitle = createGlassTextField("Book Title");
        txtAuthor = createGlassTextField("Author Name");

        inputGlass.add(txtId);
        inputGlass.add(Box.createRigidArea(new Dimension(0, 8)));
        inputGlass.add(txtTitle);
        inputGlass.add(Box.createRigidArea(new Dimension(0, 8)));
        inputGlass.add(txtAuthor);
        inputGlass.add(Box.createRigidArea(new Dimension(0, 12)));

        JButton btnAdd = createiOSButton("➕ Add Book Card", new Color(52, 199, 89));
        btnAdd.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputGlass.add(btnAdd);

        inputGlass.add(Box.createRigidArea(new Dimension(0, 20)));

        // Borrow / Return Action Section
        JLabel lblActionTitle = new JLabel("Quick Actions");
        lblActionTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblActionTitle.setForeground(Color.WHITE);
        lblActionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        inputGlass.add(lblActionTitle);
        inputGlass.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel searchRow = new JPanel(new BorderLayout(8, 0));
        searchRow.setOpaque(false);
        searchRow.setMaximumSize(new Dimension(300, 35));
        searchRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtSearchId = createGlassTextField("Book ID");
        JButton btnToggle = createiOSButton("🔄 Borrow/Return", new Color(0, 122, 255));
        searchRow.add(txtSearchId, BorderLayout.CENTER);
        searchRow.add(btnToggle, BorderLayout.EAST);

        inputGlass.add(searchRow);
        centerPanel.add(inputGlass);

        // --- RIGHT SIDE: iOS Notification Stack ---
        cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.Y_AXIS));
        cardContainer.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(cardContainer);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);

        centerPanel.add(scrollPane);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        // --- EVENT LISTENERS ---
        btnAdd.addActionListener(e -> {
            String id = txtId.getText().trim();
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();

            if (id.isEmpty() || title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Book newBook = new Book(id, title, author);
            if (manager.addBook(newBook)) {
                txtId.setText("");
                txtTitle.setText("");
                txtAuthor.setText("");
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Library capacity full!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnToggle.addActionListener(e -> {
            String searchId = txtSearchId.getText().trim();
            if (searchId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter a Book ID!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (manager.toggleBorrowStatus(searchId)) {
                txtSearchId.setText("");
                updateDisplay();
            } else {
                JOptionPane.showMessageDialog(this, "Book ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateDisplay();
    }

    private void updateDisplay() {
        cardContainer.removeAll();
        Book[] books = manager.getAllBooks();

        if (books.length == 0) {
            GlassPanel emptyCard = new GlassPanel(16);
            emptyCard.setLayout(new FlowLayout(FlowLayout.CENTER));
            JLabel lblEmpty = new JLabel("No books added yet.");
            lblEmpty.setForeground(new Color(255, 255, 255, 160));
            lblEmpty.setFont(new Font("SansSerif", Font.ITALIC, 13));
            emptyCard.add(lblEmpty);
            cardContainer.add(emptyCard);
        } else {
            for (Book b : books) {
                cardContainer.add(createNotificationCard(b));
                cardContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Gap between iOS notification cards
            }
        }

        cardContainer.revalidate();
        cardContainer.repaint();
    }

    // Creates an iOS Notification Card for each Book
    private JPanel createNotificationCard(Book book) {
        GlassPanel card = new GlassPanel(16);
        card.setLayout(new BorderLayout(10, 5));
        card.setBorder(new EmptyBorder(10, 12, 10, 12));
        card.setMaximumSize(new Dimension(320, 68));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Left Icon Badge
        JLabel lblIcon = new JLabel(book.isBorrowed() ? "📕" : "📖");
        lblIcon.setFont(new Font("SansSerif", Font.PLAIN, 22));

        // Center Content Area
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 2, 2));
        textPanel.setOpaque(false);

        JLabel lblTitle = new JLabel(book.getTitle() + " (" + book.getBookId() + ")");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitle.setForeground(Color.WHITE);

        JLabel lblAuthor = new JLabel("By " + book.getAuthor());
        lblAuthor.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblAuthor.setForeground(new Color(210, 215, 225, 200));

        textPanel.add(lblTitle);
        textPanel.add(lblAuthor);

        // Right Status Pill Capsule
        JLabel lblStatus = new JLabel(book.isBorrowed() ? "Borrowed" : "Available");
        lblStatus.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblStatus.setOpaque(true);
        lblStatus.setForeground(Color.WHITE);
        lblStatus.setBackground(book.isBorrowed() ? new Color(255, 59, 48, 200) : new Color(52, 199, 89, 200));
        lblStatus.setBorder(BorderFactory.createEmptyBorder(3, 8, 3, 8));

        card.add(lblIcon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);
        card.add(lblStatus, BorderLayout.EAST);

        return card;
    }

    // Helper: Glassmorphic Input Text Field
    private JTextField createGlassTextField(String placeholder) {
        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBackground(new Color(255, 255, 255, 25));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        tf.setMaximumSize(new Dimension(300, 35));
        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        return tf;
    }

    // Helper: iOS Pill Button
    private JButton createiOSButton(String text, Color accentColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(accentColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryGUI().setVisible(true));
    }

    // Custom Glass Panel Class with Anti-Aliased Rounded Corners & Translucency
    static class GlassPanel extends JPanel {
        private int cornerRadius;

        public GlassPanel(int radius) {
            this.cornerRadius = radius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Frosted glass background fill
            g2.setColor(new Color(255, 255, 255, 30));
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

            // Glowing glass border highlight
            g2.setColor(new Color(255, 255, 255, 60));
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius));
        }
    }
}