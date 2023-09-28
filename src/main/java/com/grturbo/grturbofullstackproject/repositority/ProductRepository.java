package com.grturbo.grturbofullstackproject.repositority;

import com.grturbo.grturbofullstackproject.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long id, Pageable pageable);

    Product findProductById(Long id);
}
