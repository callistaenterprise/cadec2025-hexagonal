package se.callista.cadec2023.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import se.callista.cadec2023.product.domain.entity.ProductVariant;

public interface ProductVariantRepository extends CrudRepository<ProductVariant, Long> {

    @Transactional(readOnly = true)
    Optional<ProductVariant> findByarticleId(String articleId);

    @Transactional(readOnly = true)
    List<ProductVariant> findAllByOrderByProductId();

}
