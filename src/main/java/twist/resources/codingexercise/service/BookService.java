package twist.resources.codingexercise.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twist.resources.codingexercise.dto.BookRequest;
import twist.resources.codingexercise.entity.AuthorEntity;
import twist.resources.codingexercise.entity.BookEntity;
import twist.resources.codingexercise.model.Author;
import twist.resources.codingexercise.model.Book;
import twist.resources.codingexercise.repository.AuthorRepository;
import twist.resources.codingexercise.repository.BookRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;


    public List<Book> getBooks() {
        return bookRepository.findAll().stream().map(bookEntity -> Book.builder()
                        .id(bookEntity.getId())
                        .name(bookEntity.getName())
                        .author(Author.builder()
                                .id(bookEntity.getAuthor().getId())
                                .name(bookEntity.getAuthor().getName())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    public Book findBookByName(String name) {
        Optional<BookEntity> bookEntityOptional = bookRepository.findByName(name);
        if (bookEntityOptional.isPresent()) {
            BookEntity bookEntity = bookEntityOptional.get();
            return Book.builder()
                    .id(bookEntity.getId())
                    .name(bookEntity.getName())
                    .author(Author.builder()
                            .id(bookEntity.getAuthor().getId())
                            .name(bookEntity.getAuthor().getName())
                            .build())
                    .build();
        } else {
            return null;
        }
    }

    public Book findBookById(Integer id) {
        Optional<BookEntity> bookEntityOptional = bookRepository.findById(id);
        return bookEntityOptional.map(this::mapBookEntityToBook).orElse(null);
    }



    public Book createBook(BookRequest bookRequest) {
        BookEntity bookEntity = BookEntity.builder()
                .name(bookRequest.getName())
                .author(AuthorEntity.builder().id(bookRequest.getAuthorId()).build())
                .build();

        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        AuthorEntity authorEntity = savedBookEntity.getAuthor();

        return Book.builder()
                .id(savedBookEntity.getId())
                .name(savedBookEntity.getName())
                .author(Author.builder().id(authorEntity.getId()).name(authorEntity.getName()).build())
                .build();
    }

    public Book updateBook(Integer id, BookRequest bookRequest) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if (optionalBookEntity.isPresent()) {
            BookEntity bookEntity = optionalBookEntity.get();

            if (bookRequest.getName() != null) {
                bookEntity.setName(bookRequest.getName());
            }

            if (bookRequest.getAuthorId() != null) {
                Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(bookRequest.getAuthorId());
                if (optionalAuthorEntity.isPresent()) {
                    AuthorEntity authorEntity = optionalAuthorEntity.get();
                    bookEntity.setAuthor(authorEntity);
                } else {
                    throw new NotFoundException("Author not found with ID: " + bookRequest.getAuthorId());
                }
            }

            BookEntity updatedBookEntity = bookRepository.save(bookEntity);

            return mapBookEntityToBook(updatedBookEntity);
        } else {
            throw new NotFoundException("Book not found with ID: " + id);
        }
    }



    private Book mapBookEntityToBook(BookEntity bookEntity) {
        if (bookEntity == null) {
            return null;
        }

        AuthorEntity authorEntity = bookEntity.getAuthor();

        return Book.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .author(Author.builder()
                        .id(authorEntity.getId())
                        .name(authorEntity.getName())
                        .build())
                .build();
    }

    public void deleteBook(Integer id) {
        Optional<BookEntity> optionalBookEntity = bookRepository.findById(id);
        if (optionalBookEntity.isPresent()) {
            bookRepository.deleteById(id);
        } else {
            throw new NotFoundException("Book not found with ID: " + id);
        }
    }







}
