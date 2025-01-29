package se.callista.cadec2025.product.application.port.in.manage;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateProductCommand {

    @NotNull
    @Size(max = 255)
    private String articleId;

    @NotNull
    @Size(max = 255)
    private String name;

}
