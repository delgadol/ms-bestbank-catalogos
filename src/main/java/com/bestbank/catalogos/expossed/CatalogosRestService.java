package com.bestbank.catalogos.expossed;

import com.bestbank.catalogos.bussiness.dto.req.CatalogoReq;
import com.bestbank.catalogos.bussiness.dto.res.CatalogoRes;
import com.bestbank.catalogos.bussiness.services.CatalogosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/catalogos")
@Validated
public class CatalogosRestService {
  
  @Autowired
  private CatalogosService servCatalogos;
  
  @PostMapping("")
  public Mono<CatalogoRes> postCatalogo(@Valid @RequestBody CatalogoReq catalogo) {
    return servCatalogos.postCatalogo(catalogo);
  }
  
  @GetMapping("/{id}")
  public Mono<CatalogoRes> getCatalogoById(@PathVariable(name = "id") String idCatalogo) {
    return servCatalogos.getCatalogoById(idCatalogo);
  }
  
  @PutMapping("/{id}")
  public Mono<CatalogoRes> putCatalogoById(@PathVariable(name = "id") String idCatalogo, 
       @Valid @RequestBody CatalogoReq catalogo) {
    return servCatalogos.getCatalogoById(idCatalogo);
  }

}
