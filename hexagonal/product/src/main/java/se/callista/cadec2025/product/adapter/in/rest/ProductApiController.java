package se.callista.cadec2025.product.adapter.in.rest;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.callista.cadec2025.product.adapter.in.rest.exception.NotFoundException;
import se.callista.cadec2025.product.adapter.in.rest.exception.NotUniqueException;
import se.callista.cadec2025.product.adapter.in.rest.model.ProductValue;
import se.callista.cadec2025.product.application.domain.Product;
import se.callista.cadec2025.product.application.port.in.create.CreateProductCommand;
import se.callista.cadec2025.product.application.port.in.create.ForCreatingProducts;
import se.callista.cadec2025.product.application.port.in.manage.ForManagingProducts;
import se.callista.cadec2025.product.application.port.in.manage.GetProductCommand;
import se.callista.cadec2025.product.application.port.in.manage.UpdateProductCommand;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class ProductApiController {

    private final ForCreatingProducts productCreationPort;
    private final ForManagingProducts productManagementPort;

    private final ProductValueMapper productValueMapper;

    @GetMapping(
        value = "/products/{articleId}",
        produces = { "application/json" })
    public ProductValue getProduct(@PathVariable("articleId") String articleId) {
        try {
            Product product = productManagementPort.getProduct(new GetProductCommand(articleId));
            return productValueMapper.fromModel(product);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @GetMapping(
            value = "/products",
            produces = { "application/json" })
    public ResponseEntity<List<ProductValue>> getProducts() {
        List<ProductValue> products =
            productManagementPort.getProducts().stream()
                .map(product -> productValueMapper.fromModel(product))
                .toList();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(
        value = "/products",
        consumes = { "application/json" },
        produces = { "application/json" })
    public ResponseEntity<ProductValue> createProduct(@RequestBody ProductValue product) {
        try {
            productCreationPort.createProduct(new CreateProductCommand(product.getArticleId(), product.getName()));
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            throw new NotUniqueException(e.getMessage());
        }
    }

    @PatchMapping(
            value = "/products/{articleId}",
            produces = { "application/json" })
    public ResponseEntity<ProductValue> updateProduct(@PathVariable("articleId") String articleId, @RequestBody ProductValue product) {
        try {
            productManagementPort.updateProduct(new UpdateProductCommand(product.getArticleId(), product.getName()));
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }


}
