package se.callista.cadec2025.product.application.port.in.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateProductCommand {

    @NotNull
    @Size(max = 255)
    private String articleId;

    @NotNull
    @Size(max = 255)
    private String name;

}
