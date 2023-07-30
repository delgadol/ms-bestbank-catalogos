package com.bestbank.catalogos.bussiness.services;

import com.bestbank.catalogos.bussiness.dto.req.CatalogoReq;
import com.bestbank.catalogos.bussiness.dto.res.CatalogoRes;
import reactor.core.publisher.Mono;

public interface CatalogosService {
  
  Mono<CatalogoRes> getCatalogoById(String idCatalogo);
  
  Mono<CatalogoRes> postCatalogo(CatalogoReq catalogo);
  
  Mono<CatalogoRes> putCatalogo(String idCatalogo, CatalogoReq catalogo);

}
