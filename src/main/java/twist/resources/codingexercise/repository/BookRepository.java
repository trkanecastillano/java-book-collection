package twist.resources.codingexercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import twist.resources.codingexercise.entity.BookEntity;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    Optional<BookEntity> findByName(String name);
    Optional<BookEntity> findById(Integer id);
}
