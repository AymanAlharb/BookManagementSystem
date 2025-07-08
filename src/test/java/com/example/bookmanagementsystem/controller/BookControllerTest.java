package com.example.bookmanagementsystem.controller;

import com.example.bookmanagementsystem.model.RoleEnum;
import com.example.bookmanagementsystem.model.dto.CreatingBookRequest;
import com.example.bookmanagementsystem.model.Book;
import com.example.bookmanagementsystem.model.Role;
import com.example.bookmanagementsystem.model.User;
import com.example.bookmanagementsystem.service.BookService;
import com.example.bookmanagementsystem.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JWTService jwtService;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<Book> bookList;

    private CreatingBookRequest creatingBookRequest;

    private Book book1;

    private User user;

    @BeforeEach
    void init(){
        Role role = Role.builder().role(RoleEnum.USER).build();
        user = User.builder()
                .username("ayman")
                .email("ayman@gmail.com")
                .password("12345678aA")
                .role(role)
                .build();

        bookList = new ArrayList<>();
        book1 = Book.builder()
                .title("test1")
                .authorName("t1")
                .description("welcome to test1")
                .build();

        Book book2 = Book.builder()
                .title("test2")
                .authorName("t2")
                .description("welcome to test2")
                .build();

        Book book3 = Book.builder()
                .title("test3")
                .authorName("t3")
                .description("welcome to test3")
                .build();

        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);

        creatingBookRequest = CreatingBookRequest.builder()
                .title("test1")
                .authorName("t1")
                .description("welcome to test1")
                .build();
    }

    @Test
    public void BookController_GetAllBooks_ReturnAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(bookList);

        ResultActions response = mockMvc.perform(get("/api/book/get-all-books"));

        response.andExpect(status().isOk());
    }

    @Test
    public void BookController_AddBook_ReturnSuccess() throws Exception {
        doNothing().when(bookService).addBook(any(CreatingBookRequest.class));

        ResultActions response = mockMvc.perform(post("/api/book/add-book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creatingBookRequest)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book added successfully."));
    }

    @Test
    public void BookController_UpdateBook_ReturnSuccess() throws Exception {
        doNothing().when(bookService).updateBook(any(), any(CreatingBookRequest.class));

        ResultActions response = mockMvc.perform(put("/api/book/update-book/{bookId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creatingBookRequest)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book updated successfully."));
    }

    @Test
    public void BookController_DeleteBook_ReturnSuccess() throws Exception {
        doNothing().when(bookService).deleteBook(any());

        ResultActions response = mockMvc.perform(delete("/api/book/delete-book/{bookId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(creatingBookRequest)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Book deleted successfully."));
    }



}
