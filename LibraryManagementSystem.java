package schoolman;
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
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
        if (count < CAPACITY) {
            array[count++] = element;
        }
    }
 
    public T remove(int index) {
        if (index >= 0 && index < count) {
            T removed = array[index];
            for (int i = index; i < count - 1; i++) {
                array[i] = array[i + 1];
            }
            array[--count] = null;
            return removed;
        }
        return null;
    }
 
    public T get(int index) {
        if (index >= 0 && index < count) {
            return array[index];
        }
        return null;
    }
 
    public int size() {
        return count;
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
 
    public Book removeBook(int index) {
        return books.remove(index);
    }
 
    public Book getBook(int index) {
        return books.get(index);
    }
 
    public int getTotalBooks() {
        return books.size();
    }
}
 
public class LibraryManagementSystemGUI extends JFrame {
    private final Library library;
    private final JTextArea displayArea;
    private final JTextField titleField, authorField, priceField;
 
    public LibraryManagementSystemGUI() {
        library = new Library();
        titleField = new JTextField();
        authorField = new JTextField();
        priceField = new JTextField();
        displayArea = new JTextArea();
        initializeUI();
    }
 
    private void initializeUI() {
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
 
        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author:"));
        inputPanel.add(authorField);
        inputPanel.add(new JLabel("Price:"));
        inputPanel.add(priceField);
 
        JButton addButton = new JButton("Add Book");
        JButton displayButton = new JButton("Display Books");
 
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String author = authorField.getText();
                String price = priceField.getText();
                Book book = new Book(title, author, price);
                library.addBook(book);
                displayArea.setText("Book added successfully.");
            }
        });
 
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllBooks();
            }
        });
 
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
 
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
 
    private void displayAllBooks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < library.getTotalBooks(); i++) {
            sb.append(i).append(": ").append(library.getBook(i)).append("\n");
        }
        displayArea.setText(sb.toString());
    }
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibraryManagementSystemGUI().setVisible(true));
    }
}
