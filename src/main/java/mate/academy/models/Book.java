package mate.academy.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    @NonNull
    private String isbn;
    @NonNull
    private BigDecimal price;
    private String description;
    private String coverImage;
}
