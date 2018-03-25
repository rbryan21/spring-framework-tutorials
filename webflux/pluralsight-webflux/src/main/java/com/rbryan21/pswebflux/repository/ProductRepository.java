package com.rbryan21.pswebflux.repository;

import com.rbryan21.pswebflux.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
