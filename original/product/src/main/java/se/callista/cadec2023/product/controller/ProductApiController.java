package se.callista.cadec2023.product.controller;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.callista.cadec2023.product.domain.model.Product;
import se.callista.cadec2023.product.services.ProductService;

@RestController
@RequestMapping("/")
public class ProductApiController {

    private final ProductService productService;

    @Autowired
    public ProductApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(
        value = "/products/{articleId}",
        produces = { "application/json", "text/xml" })
    public Product getProduct(@PathVariable("articleId") String articleId) {
        try {
            return productService.getProduct(articleId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

    @GetMapping(
            value = "/products",
            produces = { "application/json" })
    public ResponseEntity<List<Product>> getProducts() {
        List<Product> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(
        value = "/products",
        consumes = { "application/json" },
        produces = { "application/json" })
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            Product result = productService.createProduct(product);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (DuplicateKeyException e) {
            throw new NotUniqueException(e.getMessage());
        }
    }

    @PatchMapping(
            value = "/products/{articleId}",
            produces = { "application/json" })
    public ResponseEntity<Product> updateProduct(@PathVariable("articleId") String articleId, @RequestBody Product product) {
        try {
            Product result = productService.updateProduct(product);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }


    @DeleteMapping(
        value = "/products/{articleId}",
        produces = { "application/json" })
    public ResponseEntity<Void> deleteProduct(@PathVariable("articleId") String articleId) {
        try {
            productService.deleteProduct(articleId);
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException(e.getMessage());
        }
    }

}
