package com.example.bookmanagementsystem.repository;

import com.example.bookmanagementsystem.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findBookById(Long id);

    @Query("select b from Book b where b.authorName = ?1")
    List<Book> getBooksByAuthor(String authorName);
}
