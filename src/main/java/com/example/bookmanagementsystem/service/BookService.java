package com.example.bookmanagementsystem.service;

import com.example.bookmanagementsystem.exception.ApiException;
import com.example.bookmanagementsystem.model.RoleEnum;
import com.example.bookmanagementsystem.model.dto.CreatingBookRequest;
import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.model.User;
import com.example.bookmanagementsystem.repository.BookRepository;
import com.example.bookmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(BookService.class);

    // Return all the books in the system.
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(CreatingBookRequest bookDto) {
        // Get the user
        User user = getUser();

        // Create the book and sava it in the database.
        Book book = new Book(null, bookDto.getTitle(), bookDto.getAuthorName(), bookDto.getDescription(), user);
        bookRepository.save(book);
        logger.info("Book '{}' added successfully by user [{}]", book.getTitle(), user.getUsername());
    }

    public void updateBook(Long bookId, CreatingBookRequest bookDto) {
        // Get the book and check if it's in the database.
        Book book = bookRepository.findBookById(bookId);
        if(book == null)
            throw new ApiException("The requested book was not found in the database.");


        // Get the user
        User user = getUser();

        // If the user is an author check if the book belongs to the user
        if(user.getRole().getRole() == RoleEnum.AUTHOR)
            if(book.getCreatedBy() != user)
                throw new ApiException("You are not authorized to update this book. Authors can only update their own books.");

        // Update the book and save it in the database.
        book.setAuthorName(bookDto.getAuthorName());
        book.setDescription(bookDto.getDescription());
        book.setTitle(bookDto.getTitle());
        bookRepository.save(book);
        logger.info("Book '{}' updated successfully by user [{}]", book.getTitle(), user.getUsername());

    }

    public void deleteBook(Long bookId) {
        // Get the book and check if it's in the database.
        Book book = bookRepository.findBookById(bookId);
        if(book == null)
            throw new ApiException("The requested book was not found in the database.");

        // Get the user
        User user = getUser();

        // If the user is an author check if the book belongs to the user
        if(user.getRole().getRole() == RoleEnum.AUTHOR)
            if(book.getCreatedBy() != user)
                throw new ApiException("You are not authorized to delete this book. Authors can only delete their own books.");

        // delete the book from the database;
        bookRepository.delete(book);
        logger.info("Book '{}' deleted successfully by user [{}]", book.getTitle(), user.getUsername());
    }

    public List<Book> getBooksByAuthor(String authorName){
        return bookRepository.getBooksByAuthor(authorName);
    }

    public User getUser() {
        // Get the authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = null;

        // Check if the authentication and the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {

            Object principal = authentication.getPrincipal();
            // Get the user username
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            } else if (principal instanceof String) {
                username = (String) principal;
            }
        }
        // Return
        return userRepository.findUserByUsername(username);
    }
}
