package Library;

import java.util.*;

class LibraryManagementSystem {
    private static final Map<String, Book> bookCatalog = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            displayMenu();
            int choice = getUserChoice();
            handleUserChoice(choice);
        }
    }

    private static void displayMenu() {
        System.out.println("\nLibrary Management System");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Book by ID or Title");
        System.out.println("4. Update Book Details");
        System.out.println("5. Delete a Book Record");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 6.");
            return -1;
        }
    }

    private static void handleUserChoice(int choice) {
        switch (choice) {
            case 1: addBook(); break;
            case 2: viewBooks(); break;
            case 3: searchBook(); break;
            case 4: updateBook(); break;
            case 5: deleteBook(); break;
            case 6: System.out.println("Exiting..."); System.exit(0);
            default: System.out.println("Invalid choice. Try again.");
        }
    }

    private static void addBook() {
        System.out.print("Enter Book ID: ");
        String bookID = scanner.nextLine().trim();
        if (bookCatalog.containsKey(bookID)) {
            System.out.println("Book ID already exists!");
            return;
        }
        
        String title = getValidatedInput("Enter Title: ");
        String author = getValidatedInput("Enter Author: ");
        System.out.print("Enter Genre: ");
        String genre = scanner.nextLine().trim();
        String availability = getValidatedAvailability();
        
        bookCatalog.put(bookID, new Book(bookID, title, author, genre, availability));
        System.out.println("Book added successfully.");
    }

    private static void viewBooks() {
        if (bookCatalog.isEmpty()) {
            System.out.println("No books in the catalog.");
            return;
        }
        bookCatalog.values().forEach(System.out::println);
    }

    private static void searchBook() {
        System.out.print("Enter Book ID or Title: ");
        String query = scanner.nextLine().trim();
        bookCatalog.values().stream()
                .filter(book -> book.getBookID().equalsIgnoreCase(query) || book.getTitle().equalsIgnoreCase(query))
                .findFirst()
                .ifPresentOrElse(System.out::println, () -> System.out.println("Book not found."));
    }

    private static void updateBook() {
        System.out.print("Enter Book ID to update: ");
        String bookID = scanner.nextLine().trim();
        
        Book book = bookCatalog.get(bookID);
        if (book == null) {
            System.out.println("Book not found.");
            return;
        }
        
        String title = getOptionalInput("Enter new Title (leave empty to keep current): ", book.getTitle());
        String author = getOptionalInput("Enter new Author (leave empty to keep current): ", book.getAuthor());
        String availability = getOptionalInput("Enter new Availability (Available/Checked Out, leave empty to keep current): ", book.getAvailability());
        
        book.updateDetails(title, author, availability);
        System.out.println("Book updated successfully.");
    }

    private static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        String bookID = scanner.nextLine().trim();
        if (bookCatalog.remove(bookID) != null) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    private static String getValidatedInput(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This field cannot be empty.");
            }
        } while (input.isEmpty());
        return input;
    }

    private static String getValidatedAvailability() {
        String availability;
        do {
            System.out.print("Enter Availability (Available/Checked Out): ");
            availability = scanner.nextLine().trim();
            if (!availability.equalsIgnoreCase("Available") && !availability.equalsIgnoreCase("Checked Out")) {
                System.out.println("Invalid availability status. Please enter 'Available' or 'Checked Out'.");
            }
        } while (!availability.equalsIgnoreCase("Available") && !availability.equalsIgnoreCase("Checked Out"));
        return availability;
    }

    private static String getOptionalInput(String prompt, String currentValue) {
        System.out.print(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? currentValue : input;
    }
}

class Book {
    private final String bookID;
    private String title;
    private String author;
    private final String genre;
    private String availability;

    public Book(String bookID, String title, String author, String genre, String availability) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.availability = availability;
    }

    public String getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getAvailability() {
        return availability;
    }

    public void updateDetails(String title, String author, String availability) {
        this.title = title;
        this.author = author;
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookID + ", Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Availability: " + availability;
    }
}
