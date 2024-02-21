package twist.resources.codingexercise.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String name;
    private Integer authorId;
}