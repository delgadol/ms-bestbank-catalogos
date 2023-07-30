package com.bestbank.catalogos.bussiness.dto.req;

import com.bestbank.catalogos.domain.model.ItemCatalogo;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Data;


@Data
public class CatalogoReq {
  
  @NotEmpty()
  private String descCatalogo;
  
  @NotEmpty
  private List<ItemCatalogo> valores;
  

}
