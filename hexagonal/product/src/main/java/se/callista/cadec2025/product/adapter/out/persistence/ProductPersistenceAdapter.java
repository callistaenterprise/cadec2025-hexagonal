package se.callista.cadec2025.product.adapter.out.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.callista.cadec2025.product.application.domain.ProductVariant;
import se.callista.cadec2025.product.application.port.out.persistence.NoSuchProductException;
import se.callista.cadec2025.product.application.port.out.persistence.ForProductPersistence;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductPersistenceAdapter implements ForProductPersistence {

    final ProductRepository repository;
    final ProductEntityMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductVariant> getProducts() {
        return repository.findAllByOrderByProductId().stream()
            .map(mapper::fromEntity)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductVariant getProduct(String articleId) {
        var product = repository.findByArticleId(articleId).orElseThrow(NoSuchProductException::new);
        return mapper.fromEntity(product);
    }

    @Override
    @Transactional
    public Long createProduct(ProductVariant product) {
        var entity = repository.save(mapper.toEntity(product));
        return entity.getProductId();
    }

    @Override
    @Transactional
    public void updateProduct(ProductVariant product) {
        var entity = repository.findByProductId(product.getProductId()).orElseThrow(NoSuchProductException::new);
        entity = mapper.updateWith(entity, product);
        repository.save(entity);
    }
}
