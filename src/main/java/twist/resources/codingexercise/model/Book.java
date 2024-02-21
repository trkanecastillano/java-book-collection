package twist.resources.codingexercise.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class Book {
    private Integer id;
    private String name;
    private Author author;
}
