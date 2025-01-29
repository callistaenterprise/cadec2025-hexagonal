package se.callista.cadec2025.product.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    @Transactional(readOnly = true)
    Optional<ProductEntity> findByProductId(Long id);

    @Transactional(readOnly = true)
    Optional<ProductEntity> findByArticleId(String articleId);

    @Transactional(readOnly = true)
    List<ProductEntity> findAllByOrderByProductId();

}
