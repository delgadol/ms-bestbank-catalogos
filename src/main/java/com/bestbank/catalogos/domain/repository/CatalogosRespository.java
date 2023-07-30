package com.bestbank.catalogos.domain.repository;

import com.bestbank.catalogos.domain.model.Catalogo;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CatalogosRespository extends ReactiveMongoRepository<Catalogo, String>{

}
