package twist.resources.codingexercise.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import twist.resources.codingexercise.entity.AuthorEntity;
import twist.resources.codingexercise.entity.BookEntity;
import twist.resources.codingexercise.model.Author;
import twist.resources.codingexercise.model.Book;
import twist.resources.codingexercise.repository.BookRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {


    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;



    @Test
    void getBooks_ShouldReturn_AllBooks() {

        when(bookRepository.findAll()).thenReturn(List.of(BookEntity.builder()
                .id(2)
                .name("One Piece")
                .author(AuthorEntity.builder()
                        .id(1)
                        .name("Kane Castillano")
                        .build())
                .build()));
        assertThat(bookService.getBooks()).containsExactlyInAnyOrder(Book.builder()
                .id(2)
                .name("One Piece")
                .author(Author.builder()
                        .id(1)
                        .name("Kane Castillano")
                        .build())
                .build());
    }
}
