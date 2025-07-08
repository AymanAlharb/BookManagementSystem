package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.exception.ApiResponse;
import com.example.bookmanagementsystem.model.dto.CreatingBookRequest;
import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.model.User;
import com.example.bookmanagementsystem.service.BookService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @PostMapping("/add-book")
    public ResponseEntity<ApiResponse> addBook(@RequestBody @Valid CreatingBookRequest creatingBookRequest) {
        bookService.addBook(creatingBookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book added successfully."));
    }

    @Hidden
    @PutMapping("/update-book/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@PathVariable Long bookId,
                                                  @RequestBody @Valid CreatingBookRequest creatingBookRequest) {
        bookService.updateBook(bookId, creatingBookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book updated successfully."));
    }

    @DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book deleted successfully."));
    }

    @GetMapping("/get-/{authorName}/-books")
    public ResponseEntity<List<Book>> getBooksByAuthorName(@PathVariable String authorName){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooksByAuthor(authorName));
    }
}
