package com.rbryan21.pswebflux;

import com.rbryan21.pswebflux.model.Product;
import com.rbryan21.pswebflux.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@Profile("data_seed")
public class DataSeed implements CommandLineRunner {

    private ProductRepository productRepository;
    private ReactiveMongoOperations reactiveMongoOperations;

    @Autowired
    public DataSeed(ProductRepository productRepository, ReactiveMongoOperations reactiveMongoOperations) {
        this.productRepository = productRepository;
        this.reactiveMongoOperations = reactiveMongoOperations;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Initializing seed data");
        this.initializeFluxProducts();
    }

    private void initializeFluxProducts() {
        Flux<Product> productFlux = Flux.just(
                Product.builder().name("Nintendo Switch").price(299.99).build(),
                Product.builder().name("Rubix Cude").price(10.00).build(),
                Product.builder().name("Comic Books").price(5.00).build(),
                Product.builder().name("Hot Sauce").price(12.00).build())
                .flatMap(productRepository::save);

        productFlux
                .then(productRepository.count())
                .subscribe(count -> System.out.println("Adding " + count + " products to data seed."));

//        reactiveMongoOperations.collectionExists(Product.class)
//                .flatMap(exists -> exists ? reactiveMongoOperations.dropCollection(Product.class) : Mono.just(exists))
//                .thenMany(v -> reactiveMongoOperations.createCollection(Product.class))
//                .thenMany(productFlux)
//                .thenMany(productRepository.findAll())
//                .subscribe(count -> System.out.println("Count = " + count));
    }

}
