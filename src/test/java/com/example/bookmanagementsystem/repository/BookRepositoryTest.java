package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.model.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    private Book book1;

    @BeforeEach
    void init(){
        book1 = Book.builder()
                .title("test1")
                .authorName("t1")
                .description("welcome to test1")
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
    }

    @Test
    public void BookRepository_FindBookById_ReturnBook() {
        Book returnedBook = bookRepository.findBookById(book1.getId());

        Assertions.assertThat(returnedBook).isNotNull();
        Assertions.assertThat(returnedBook.getTitle()).isEqualTo("test1");
    }

    @Test
    public void BookRepository_FindAll_ReturnAllBooks(){
        List<Book> allBooks = bookRepository.findAll();

        Assertions.assertThat(allBooks).isNotNull();
        Assertions.assertThat(allBooks.size()).isEqualTo(3);

    }
}
