package com.bestbank.catalogos.bussiness.services.impl;


import com.bestbank.catalogos.bussiness.dto.req.CatalogoReq;
import com.bestbank.catalogos.bussiness.dto.res.CatalogoRes;
import com.bestbank.catalogos.bussiness.services.CatalogosService;
import com.bestbank.catalogos.domain.model.Catalogo;
import com.bestbank.catalogos.domain.repository.CatalogosRespository;
import com.bestbank.commons.utils.BankFnUtils;
import com.bestbank.commons.utils.ModelMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveHashOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class CatalogosServiceImpl implements CatalogosService{
  
  @Autowired
  private ReactiveHashOperations<String, String, CatalogoRes> hashOperations;
  
  private static final String KEY = "catalogos";
  

  @Autowired
  private CatalogosRespository repoCatalogos;
  
  
  private Catalogo dtoCatalogoReqToEntity(CatalogoReq catalogo) {
    log.info("Preprarando Nuevo Catalogo");
    Catalogo catalogoItem = ModelMapperUtils.map(catalogo, Catalogo.class);
    catalogoItem.setCodContrl(BankFnUtils.uniqueProductCode());
    catalogoItem.setIndEliminado(0);
    catalogoItem.setFechaCreacion(BankFnUtils.getLegacyDateTimeNow());
    catalogoItem.setFechaModificacion(BankFnUtils.getLegacyDateTimeNow());
    return catalogoItem;
    
  }
  
  private Mono<CatalogoRes> getCatalogoFromDbAndCahe(String idCatalogo) {
    return repoCatalogos.findById(idCatalogo)
        .flatMap(item -> {
            log.info("Retornando Valor de Base de Datos");
            return Mono.just(ModelMapperUtils.map(item, CatalogoRes.class));
          })
        .flatMap(itemRes -> 
          this.hashOperations.put(KEY,idCatalogo,itemRes).thenReturn(itemRes)
        );    
  }
  
  @Override
  public Mono<CatalogoRes> getCatalogoById(String idCatalogo) {
    log.info("Solicitando Datos de catalogo de REDIS");
    return hashOperations.get(KEY, idCatalogo)
        .switchIfEmpty(this.getCatalogoFromDbAndCahe(idCatalogo));
  }

  @Override
  public Mono<CatalogoRes> postCatalogo(CatalogoReq catalogo) {
    log.info("Escribiendo nuevo Catalogo");
    return repoCatalogos.save(dtoCatalogoReqToEntity(catalogo))
        .flatMap(item -> 
          Mono.just(ModelMapperUtils.map(item, CatalogoRes.class))
        );
  }

  @Override
  public Mono<CatalogoRes> putCatalogo(String idCatalogo, CatalogoReq catalogo) {
    log.info("Modificando Catalogo y Borrando del Cache");
    return repoCatalogos.findById(idCatalogo)
        .flatMap(itemDb -> {
          Catalogo modItem = ModelMapperUtils.map(itemDb, Catalogo.class);
          modItem.setDescCatalogo(catalogo.getDescCatalogo());
          modItem.setValores(catalogo.getValores());
          modItem.setFechaModificacion(BankFnUtils.getLegacyDateTimeNow());
          return repoCatalogos.save(modItem)
              .flatMap(itemUpd -> {
                return hashOperations.remove(KEY, idCatalogo)
                    .flatMap(cont -> { 
                      return Mono.just(ModelMapperUtils.map(itemUpd, CatalogoRes.class));
                    });
              });
        });
  }

}
