# 📚 Library Management & Member Registration System

A desktop-based Java application built using **Java Swing** and **Object-Oriented Programming (OOP)** principles. Developed by Makerere University COMPUTER SCIENCE Students , GROUP 1 AFTERNOON academic year 2026/27 for **CSC 1102: Structured Programming & OOP Practical Group Exam Assignment**, this system manages library inventory and member registrations using in-memory array data structures without external database dependencies.

---

## 🌟 Key Features

- 📖 **Book Management (`Book.java`)**: Add new books with unique IDs, titles, and author details; track availability status (`Available` / `Borrowed`).
- 👤 **Member Registration (`Member.java`)**: Register library members with Member IDs, names, and contact phone numbers; track borrowed book counts.
- 🔄 **Borrow & Return Operations (`LibraryManager.java`)**: Dedicated logic to issue books to registered members and process returns with status updates.
- 🖥️ **Graphical User Interface (`LibraryGUI.java`)**: Clean Java Swing dashboard featuring input forms, interactive action buttons, popup notifications (`JOptionPane`), and a scrollable text display (`JTextArea`).
- ⚡ **In-Memory Storage**: Uses custom arrays (`Book[]` and `Member[]`) with linear search algorithms for data manipulation.

---

## 👥 Group Members & Contributors

| Student Name              | Registration Number |
| :------------------------ | :------------------ |
| **AKOLA ARTHUR ALAALI**   | `26/U/30158/PSA`    |
| **MAISHANO HALIMA MPIMA** | `26/U/01878/PS`     |
| **WILLIS MAK GOLD**       | `26/U/03498/PSA`    |
| **KEMIGISHA MELLISA**     | `26/U/03532/PSA`    |
| **JASON MUDUSU**          | `26/U/03569/PSA`    |

---

## 🏛️ Project Architecture & OOP Structure

The application follows a modular **Model-View-Controller (MVC)** architecture consisting of 4 core Java classes:

| Class File                | OOP Layer / Role            | Description & Primary Responsibilities                                                                                                                                                                                                                          |
| :------------------------ | :-------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`Book.java`**           | Data Model                  | Encapsulates book attributes (`bookId`, `title`, `author`, `isBorrowed`) using `private` fields, constructor initialization, getter/setter methods, and `toString()` formatting.                                                                                |
| **`Member.java`**         | Data Model                  | Encapsulates member details (`memberId`, `name`, `phone`, `borrowedCount`) with `private` fields, getters/setters, and logic guards (`incrementBorrowed()`, `decrementBorrowed()`).                                                                             |
| **`LibraryManager.java`** | Business Logic / Controller | Controls in-memory array storage (`Book[]` and `Member[]`), executes search algorithms (`searchBookById()`), validates capacity limits, and manages `borrowBook()` and `returnBook()` operations.                                                               |
| **`LibraryGUI.java`**     | Presentation / View         | Implements the Java Swing user interface (`JFrame`), managing layout panels (`BorderLayout`, `BoxLayout`, `GridLayout`), input text fields (`JTextField`), action buttons (`JButton`), scrollable display (`JTextArea`), and event handlers (`ActionListener`). |

---

## 📁 Repository Structure

```text
LibraryManagementSystem/
├── src/
│   ├── Book.java            # Model: Encapsulates book data and status
│   ├── Member.java          # Model: Encapsulates member details and borrowing count
│   ├── LibraryManager.java  # Controller: Handles array storage and business logic
│   └── LibraryGUI.java      # View: Java Swing graphical user interface & main entry point
├── bin/                     # Compiled bytecode (.class files)
├── README.md                # Project documentation
└── .gitignore               # Git ignore configuration
```

---

## 🚀 Getting Started

### **Prerequisites**

- **Java Development Kit (JDK 8 or higher)** installed on your system.
- **Git** installed for version control.

### **Installation & Compilation**

1. **Clone the repository:**

   ```bash
   git clone https://github.com/akolaarthurali/Java_GUI_library_system.git
   cd Java_GUI_library_system
   ```

2. **Compile the Java source files:**

   ```bash
   javac -d bin src/*.java
   ```

3. **Run the application:**
   ```bash
   java -cp bin LibraryGUI
   ```

---

## 🎓 Course Information

- **Course Title**: CSC 1102 - Structured Programming & Object-Oriented Programming
- **Project Type**: Practical Group Exam Assignment
