package se.callista.cadec2025.product.adapter.out.persistence;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
public class ProductEntity {

    @Id
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq")
    protected Long productId;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "int default 0")
    protected Integer version;

    @NotNull
    @Size(max = 255)
    @JsonProperty("articleId")
    private String articleId;

    @Column(name = "name", length = 255, nullable = false)
    @NotNull
    @Size(max = 255)
    private String name;

    @Builder
    public ProductEntity(Long productId, String name, String articleId, Integer version) {
        this.productId = productId;
        this.articleId = articleId;
        this.name = name;
        this.version = version;
    }
}
