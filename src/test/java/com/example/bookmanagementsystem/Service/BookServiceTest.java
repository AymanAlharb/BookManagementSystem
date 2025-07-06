package com.example.bookmanagementsystem.Service;

import com.example.bookmanagementsystem.DtoIn.BookDtoIn;
import com.example.bookmanagementsystem.Model.Book;
import com.example.bookmanagementsystem.Model.Role;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Repository.BookRepository;
import com.example.bookmanagementsystem.Repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BookService bookService;

    private List<Book> bookList;

    private BookDtoIn bookDtoIn;
    private BookDtoIn bookDtoIn2;

    private Book book1;

    private User user;

    @BeforeEach
    void init(){
        Role role = Role.builder().role("AUTHOR").build();
        user = User.builder()
                .username("ayman")
                .email("ayman@gmail.com")
                .password("12345678aA")
                .role(role)
                .build();
        role.setUser(user);
        userRepository.save(user);

        bookList = new ArrayList<>();
        book1 = Book.builder()
                .title("test1")
                .authorName("t1")
                .description("welcome to test1")
                .createdBy(user)
                .build();

        bookRepository.save(book1);

        Book book2 = Book.builder()
                .title("test2")
                .authorName("t2")
                .description("welcome to test2")
                .build();

        bookRepository.save(book2);

        Book book3 = Book.builder()
                .title("test3")
                .authorName("t3")
                .description("welcome to test3")
                .build();

        bookRepository.save(book3);

        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        bookDtoIn = BookDtoIn.builder()
                .title("test1")
                .authorName("t1")
                .description("welcome to test1")
                .build();

        bookDtoIn2 = BookDtoIn.builder()
                .title("test3")
                .authorName("t1")
                .description("welcome to test1")
                .build();
    }

    @Test
    public void BookService_getAllBooks_ReturnAllBooks(){
        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> books = bookService.getAllBooks();

        Assertions.assertThat(books).isNotNull();
        Assertions.assertThat(books.size()).isEqualTo(3);
    }


    @Test
    public void BookService_AddBook_ReturnsBook(){
        bookService.addBook(user, bookDtoIn);

        verify(bookRepository, times(1)).save(book1);
        assertEquals(user, book1.getCreatedBy());
        assertEquals(bookDtoIn.getTitle(), book1.getTitle());
    }

    @Test
    public void BookService_UpdateBook_ChangedBookTitle(){
        when(bookRepository.findBookById(book1.getId())).thenReturn(book1);
        bookService.updateBook(user, book1.getId(), bookDtoIn2);

        verify(bookRepository, times(2)).save(book1);
        assertEquals(book1.getTitle(), bookDtoIn2.getTitle());
    }

    @Test
    public void BookService_DeleteBook_BookDeleted(){
        when(bookRepository.findBookById(book1.getId())).thenReturn(book1);
        bookService.deleteBook(user, book1.getId());

        verify(bookRepository, times(1)).delete(book1);
    }


}
