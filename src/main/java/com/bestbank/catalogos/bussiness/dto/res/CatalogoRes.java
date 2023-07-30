package com.bestbank.catalogos.bussiness.dto.res;

import com.bestbank.catalogos.domain.model.ItemCatalogo;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CatalogoRes {

  private String id;
  
  private String codContrl;
  
  private String descCatalogo;
  
  private List<ItemCatalogo> valores;
  
  
}