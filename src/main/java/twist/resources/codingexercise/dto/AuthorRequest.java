package twist.resources.codingexercise.dto;

import lombok.Data;

@Data
public class AuthorRequest {
    private String name;
    private Integer authorId;
}