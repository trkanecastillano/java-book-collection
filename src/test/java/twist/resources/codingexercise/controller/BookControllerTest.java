package twist.resources.codingexercise.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import twist.resources.codingexercise.model.Author;
import twist.resources.codingexercise.model.Book;
import twist.resources.codingexercise.service.BookService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class BookControllerTest {
    @Autowired
    BookController bookController;

    @Test
    void getBooks_ExistingBooks_ReturnListOfBooks() {
        assertThat(bookController.getBooks()).containsExactlyInAnyOrder(Book.builder()
                .id(2)
                .name("One Piece")
                .author(Author.builder()
                        .id(1)
                        .name("Kane Castillano")
                        .build())
        .build());
    }
}
