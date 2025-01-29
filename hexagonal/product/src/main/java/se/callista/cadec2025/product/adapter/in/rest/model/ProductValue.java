package se.callista.cadec2025.product.adapter.in.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductValue {

    @NotNull
    @Size(max = 255)
    @JsonProperty("articleId")
    private String articleId;

    @NotNull
    @Size(max = 255)
    @JsonProperty("name")
    private String name;

    @NotNull
    @JsonProperty("inventory")
    private Long inventory;

}
