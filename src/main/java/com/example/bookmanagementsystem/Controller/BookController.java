package com.example.bookmanagementsystem.Controller;

import com.example.bookmanagementsystem.Api.ApiResponse;
import com.example.bookmanagementsystem.DtoIn.BookDtoIn;
import com.example.bookmanagementsystem.Model.Book;
import com.example.bookmanagementsystem.Model.User;
import com.example.bookmanagementsystem.Service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @PostMapping("/add-book")
    public ResponseEntity<ApiResponse> addBook(@AuthenticationPrincipal User user, @RequestBody @Valid BookDtoIn bookDtoIn) {
        bookService.addBook(user, bookDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book added successfully."));
    }

    @PutMapping("/update-book/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@AuthenticationPrincipal User user, @PathVariable Long bookId,
                                                  @RequestBody @Valid BookDtoIn bookDtoIn) {
        bookService.updateBook(user, bookId, bookDtoIn);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book updated successfully."));
    }

    @PutMapping("/delete-book/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@AuthenticationPrincipal User user, @PathVariable Long bookId) {
        bookService.deleteBook(user, bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book deleted successfully."));
    }
}
