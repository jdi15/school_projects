package schoolman;
 
import java.util.Scanner;
 
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
 
public class LibraryManagementSystem {
    private static final Library library = new Library();
    private static final Scanner scanner = new Scanner(System.in);
 
    public static void main(String[] args) {
        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Search Book");
            System.out.println("4. Display All Books");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
 
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
 
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    displayAllBooks();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
 
    private static void addBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter price: ");
        String price = scanner.nextLine();
 
        Book book = new Book(title, author, price);
        library.addBook(book);
        System.out.println("Book added successfully.");
    }
 
    private static void removeBook() {
        System.out.print("Enter index to remove: ");
        int index = scanner.nextInt();
        Book removed = library.removeBook(index);
        if (removed != null) {
            System.out.println("Removed book: " + removed);
        } else {
            System.out.println("No book found at this index.");
        }
    }
 
    private static void searchBook() {
        System.out.print("Enter index to search: ");
        int index = scanner.nextInt();
        Book book = library.getBook(index);
        if (book != null) {
            System.out.println("Found book: " + book);
        } else {
            System.out.println("No book found at this index.");
        }
    }
 
    private static void displayAllBooks() {
        for (int i = 0; i < library.getTotalBooks(); i++) {
            System.out.println(i + ": " + library.getBook(i));
        }
    }
}
