package schoolman;
 
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
 
class DynamicArray<T> {
    private final T[] array;
    private int count;
    private static final int CAPACITY = 100;
 
 
    @SuppressWarnings("unchecked")
    public DynamicArray() {
        array = (T[]) new Object[CAPACITY];
        count = 0;
    }
 
    public void add(T element) {
        for (int i = 0; i < CAPACITY; i++) {
            if (array[i] == null) {
                array[i] = element;
                count++;
                break;
            }
        }
    }
 
    public void add(int index, T element) {
        if (index >= 0 && index < CAPACITY) {
            if (array[index] == null && element != null) {
                count++;
            } else if (array[index] != null && element == null) {
                count--;
            }
            array[index] = element;
        }
    }
 
    public T remove(int index) {
        if (index >= 0 && index < CAPACITY) {
            T removed = array[index];
            if (removed != null) {
                array[index] = null;
                count--;
            }
            return removed;
        }
        return null;
    }
 
    public T get(int index) {
        if (index >= 0 && index < CAPACITY) {
            return array[index];
        }
        return null;
    }
 
    public int size() {
        return count;
    }
 
    public boolean isEmpty() {
        return count == 0;
    }
 
    public int capacity() {
        return CAPACITY;
    }
}
 
class Book {
    private final String title;
    private final String author;
    private final String price;
 
    public Book(String title, String author, String price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }
 
    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Price: " + price;
    }
}
 
class Library {
    private final DynamicArray<Book> books;
 
    public Library() {
        books = new DynamicArray<>();
    }
 
    public void addBook(Book book) {
        books.add(book);
    }
 
    public void insertBook(int index, Book book) {
        books.add(index, book);
    }
 
    public Book removeBook(int index) {
        return books.remove(index);
    }
 
    public Book searchBook(int index) {
        return books.get(index);
    }
 
    public int getTotalBooks() {
        return books.size();
    }
 
    public String getAllBooks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < books.capacity(); i++) {
            Book book = books.get(i);
            if (book != null) {
                sb.append(i).append(": ").append(book).append("\n");
            }
        }
        return sb.toString();
    }
 
    public boolean isEmpty() {
        return books.isEmpty();
    }
}
 
@SuppressWarnings("serial")
public class LibraryManagementSystemGUI extends JFrame {
    private final Library library;
    private final JTextArea displayArea;
    private final JTextField titleField, authorField, priceField, indexField;
 
    public LibraryManagementSystemGUI() {
        library = new Library();
        titleField = new JTextField();
        authorField = new JTextField();
        priceField = new JTextField();
        indexField = new JTextField();
        displayArea = new JTextArea();
        initializeUI();
    }
 
    private void initializeUI() {
        setTitle("Library Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
 
        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
        inputPanel.add(new JLabel("Index:"));
        inputPanel.add(indexField);
 
        // Buttons
        JButton addButton = new JButton("Add Book");
        JButton removeButton = new JButton("Remove Book");
        JButton searchButton = new JButton("Search Book");
        JButton displayAllButton = new JButton("Display All Books");
        JButton totalBooksButton = new JButton("Total Books");
 
        inputPanel.add(addButton);
        inputPanel.add(removeButton);
 
        add(inputPanel, BorderLayout.NORTH);
 
        // Display Area
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);
 
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(searchButton);
        buttonPanel.add(displayAllButton);
        buttonPanel.add(totalBooksButton);
        add(buttonPanel, BorderLayout.SOUTH);
 
        // Action Listeners
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String price = priceField.getText();
                String indexText = indexField.getText();
 
                if (!title.isEmpty() && !author.isEmpty() && !price.isEmpty()) {
                    Book book = new Book(title, author, price);
 
                    if (!indexText.isEmpty()) {
                        try {
                            int index = Integer.parseInt(indexText);
                            library.insertBook(index, book);
                            displayArea.setText("Book inserted at index " + index + " successfully.");
                        } catch (NumberFormatException ex) {
                            library.addBook(book);
                            displayArea.setText("Invalid index. Book added to the end.");
                        }
                    } else {
                        library.addBook(book);
                        displayArea.setText("Book added to the end successfully.");
                    }
 
                    clearInputFields();
                    updateDisplay();
                } else {
                    displayArea.setText("Please fill all fields.");
                }
            }
        });
 
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(indexField.getText());
                    Book removedBook = library.removeBook(index);
                    if (removedBook != null) {
                        displayArea.setText("Book removed: " + removedBook);
                    } else {
                        displayArea.setText("No book found at this index.");
                    }
                    updateDisplay();
                } catch (NumberFormatException ex) {
                    displayArea.setText("Please enter a valid index.");
                }
            }
        });
 
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(indexField.getText());
                    Book book = library.searchBook(index);
                    if (book != null) {
                        displayArea.setText("Book found: " + book);
                    } else {
                        displayArea.setText("No book found at this index.");
                    }
                } catch (NumberFormatException ex) {
                    displayArea.setText("Please enter a valid index.");
                }
            }
        });
 
        displayAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDisplay();
            }
        });
 
        totalBooksButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayArea.setText("Total number of books: " + library.getTotalBooks());
            }
        });
    }
 
    private void clearInputFields() {
        titleField.setText("");
        authorField.setText("");
        priceField.setText("");
        indexField.setText("");
    }
 
    private void updateDisplay() {
        String allBooks = library.getAllBooks();
        if (allBooks.isEmpty()) {
            displayArea.setText("The library is empty.");
        } else {
            displayArea.setText(allBooks);
        }
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystemGUI().setVisible(true));
    }
}
