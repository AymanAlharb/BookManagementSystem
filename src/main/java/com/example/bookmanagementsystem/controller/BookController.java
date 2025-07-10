package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.exception.ApiResponse;
import com.example.bookmanagementsystem.model.dto.CreatingBookRequest;
import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @Operation(
            summary = "Returns a list of all the books",
            description = "Public endpoint"
    )
    @GetMapping("/get-all-books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }
    @Operation(
            summary = "Adds a book",
            description = "Only admins and authors can add books",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Book details required for book creation"
            )
    )
    @PostMapping("/add-book")
    public ResponseEntity<ApiResponse> addBook(@RequestBody @Valid CreatingBookRequest creatingBookRequest) {
        bookService.addBook(creatingBookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book added successfully."));
    }

    @Operation(
            summary = "Updates a book",
            description = "Only admins and authors who own the book can update it",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Book details required for book update"
            )
    )
    @PutMapping("/update-book/{bookId}")
    public ResponseEntity<ApiResponse> updateBook(@Parameter(description = "Book id required for book update", required = true)
                                                      @PathVariable Long bookId, @RequestBody @Valid CreatingBookRequest creatingBookRequest) {
        bookService.updateBook(bookId, creatingBookRequest);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book updated successfully."));
    }

    @Operation(
            summary = "Deletes a book",
            description = "Only admins and authors who own the book can delete it"
    )
    @DeleteMapping("/delete-book/{bookId}")
    public ResponseEntity<ApiResponse> deleteBook(@Parameter(description = "Book id required for book deletion", required = true)
                                                      @PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Book deleted successfully."));
    }

    @GetMapping("/get-/{authorName}/-books")
    public ResponseEntity<List<Book>> getBooksByAuthorName(@PathVariable String authorName){
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBooksByAuthor(authorName));
    }
}
