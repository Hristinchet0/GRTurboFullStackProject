package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.dto.ProductRecentDto;
import com.grturbo.grturbofullstackproject.model.dto.ProductViewDto;
import com.grturbo.grturbofullstackproject.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    Product findProductById(Long id);

    @Query(value = "SELECT new com.grturbo.grturbofullstackproject.model.dto.ProductRecentDto(" +
            "p.name, p.brand, p.price) FROM Product p " +
            "ORDER BY p.id DESC")
    List<ProductRecentDto> findRecentProducts(Pageable pageable);

    @Query("select p from Product p where p.name like %?1% or p.description like %?1%")
    List<Product> findAllByNameOrDescription(String keyword);
}
