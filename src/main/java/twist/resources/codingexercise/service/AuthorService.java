package twist.resources.codingexercise.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import twist.resources.codingexercise.dto.AuthorRequest;
import twist.resources.codingexercise.entity.AuthorEntity;
import twist.resources.codingexercise.model.Author;
import twist.resources.codingexercise.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll().stream()
                .map(this::mapAuthorEntityToAuthor)
                .collect(Collectors.toList());
    }

    public Optional<Author> findAuthorByName(String name) {
        return authorRepository.findByName(name).map(this::mapAuthorEntityToAuthor);
    }

    public Optional<Author> findAuthorById(Integer id) {
        return authorRepository.findById(id).map(this::mapAuthorEntityToAuthor);
    }

    public Author createAuthor(AuthorRequest authorRequest) {
        try {
            AuthorEntity authorEntity = AuthorEntity.builder()
                    .name(authorRequest.getName())
                    .build();
            AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
            return mapAuthorEntityToAuthor(savedAuthorEntity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create author: " + e.getMessage());
        }
    }

    public Author updateAuthor(Integer id, AuthorRequest authorRequest) {
        Optional<AuthorEntity> optionalAuthorEntity = authorRepository.findById(id);
        if (optionalAuthorEntity.isPresent()) {
            AuthorEntity authorEntity = optionalAuthorEntity.get();
            authorEntity.setName(authorRequest.getName());
            AuthorEntity updatedAuthorEntity = authorRepository.save(authorEntity);
            return mapAuthorEntityToAuthor(updatedAuthorEntity);
        } else {
            throw new NotFoundException("Author not found with ID: " + id);
        }
    }

    private Author mapAuthorEntityToAuthor(AuthorEntity authorEntity) {
        return Author.builder()
                .id(authorEntity.getId())
                .name(authorEntity.getName())
                .build();
    }

    public void deleteAuthor(Integer id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
        } else {
            throw new NotFoundException("Author not found with ID: " + id);
        }
    }

}
