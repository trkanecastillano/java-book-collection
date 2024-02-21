package twist.resources.codingexercise.model;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
public class Author {
    private Integer id;
    private String name;

}
