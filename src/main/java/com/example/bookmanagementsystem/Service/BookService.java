package com.example.bookmanagementsystem.Service;

import com.example.bookmanagementsystem.Api.ApiException;
import com.example.bookmanagementsystem.DtoIn.BookDtoIn;
import com.example.bookmanagementsystem.Model.Book;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private static final Logger logger = LogManager.getLogger(BookService.class);

    // Return all the books in the system.
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(User user, BookDtoIn bookDto) {
        // Check the role of the user since only authors and admins can add books.
        if (!((user.getRole().getRole().equals("AUTHOR")) || user.getRole().getRole().equals("ADMIN"))){
            logger.warn("Unauthorized book addition attempt by user [{}] with role [{}]", user.getUsername(), user.getRole().getRole());
            throw new ApiException("Only Admins And Authors can add books");
        }

        // Create the book and sava it in the database.
        Book book = new Book(null, bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getDescription(), user);
        bookRepository.save(book);
        logger.info("Book '{}' added successfully by user [{}]", book.getTitle(), user.getUsername());
    }

    public void updateBook(User user, Long bookId, BookDtoIn bookDto) {
        // Get the book and check if it's in the database.
        Book book = bookRepository.findBookById(bookId);

        // Validate the user or throw.
        validateUser(user, book, "update");

        // Update the book and save it in the database.
        book.setAuthorName(bookDto.getAuthorName());
        book.setDescription(bookDto.getDescription());
        book.setTitle(bookDto.getTitle());
        bookRepository.save(book);
        logger.info("Book '{}' updated successfully by user [{}]", book.getTitle(), user.getUsername());

    }

    public void deleteBook(User user, Long bookId) {
        // Get the book and check if it's in the database.
        Book book = bookRepository.findBookById(bookId);

        // Validate the user or throw.
        validateUser(user, book, "delete");

        // delete the book from the database;
        bookRepository.delete(book);
        logger.info("Book '{}' deleted successfully by user [{}]", book.getTitle(), user.getUsername());
    }

    // Validate the user or throw.
    public void validateUser(User user, Book book, String operation) {
        if (book == null)
            throw new ApiException("The book not found in the database");

        // Check if user is not an admin.
        if (!user.getRole().getRole().equals("ADMIN")) {

            // Check if the user is an author and the book belong to him/her.
            if (!((user.getRole().getRole().equals("AUTHOR"))) ||
                    !Objects.equals(book.getCreatedBy().getId(), user.getId())) {
                logger.warn("Unauthorized book " + operation + " attempt by user [{}] with role [{}]", user.getUsername(), user.getRole().getRole());
                throw new ApiException("The user can not " + operation + " this book.");
            }
        }
    }
}
