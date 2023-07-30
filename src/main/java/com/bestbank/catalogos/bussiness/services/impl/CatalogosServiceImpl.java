package com.bestbank.catalogos.bussiness.services.impl;


import com.bestbank.catalogos.bussiness.dto.req.CatalogoReq;
import com.bestbank.catalogos.bussiness.dto.res.CatalogoRes;
import com.bestbank.catalogos.bussiness.services.CatalogosService;
import com.bestbank.catalogos.domain.model.Catalogo;
import com.bestbank.catalogos.domain.repository.CatalogosRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CatalogosServiceImpl implements CatalogosService{
  
  @Qualifier("ReactiveHashOperationsCustom")
  private ReactiveHashOperations<String, String, CatalogoRes> hashOperations;
  
  private static final String KEY = "catalogos";
  

  @Autowired
  private CatalogosRespository repoCatalogos;
  
  private Catalogo dtoCatalogoReqToEntity(CatalogoReq catalogo) {
    log.info("Preprarndo Catalogo Nuevp");
    Catalogo catalogoItem = ModelMapperUtils.map(catalogo, Catalogo.class);
    catalogoItem.setCodContrl(BankFnUtils.uniqueProductCode());
    catalogoItem.setIndEliminado(0);
    catalogoItem.setFechaCreacion(BankFnUtils.getLegacyDateTimeNow());
    catalogoItem.setFechaModificacion(BankFnUtils.getLegacyDateTimeNow());
    return catalogoItem;
    
  }
  
  private Mono<CatalogoRes> getCatalogoFromDbAndCahe(String idCatalogo) {
    log.info("Obteniendo de Base de Datos y Escribiendo en Cache");
    return repoCatalogos.findById(idCatalogo)
        .flatMap(item -> Mono.just(ModelMapperUtils.map(item, CatalogoRes.class)))
        .flatMap(itemRes -> 
          this.hashOperations.put(KEY,idCatalogo,itemRes).thenReturn(itemRes)
        );
    
  }
  
  @Override
  public Mono<CatalogoRes> getCatalogoById(String idCatalogo) {
    log.info("Solicitando Datos de catalogo");
    return Mono.just(new CatalogoRes());
  }

  @Override
  public Mono<CatalogoRes> postCatalogo(CatalogoReq catalogo) {
    return Mono.just(new CatalogoRes());
  }

  @Override
  public Mono<CatalogoRes> putCatalogo(String idCatalogo, CatalogoReq catalogo) {
    return Mono.just(new CatalogoRes());
  }

}
