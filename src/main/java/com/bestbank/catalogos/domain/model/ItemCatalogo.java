package com.bestbank.catalogos.domain.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ItemCatalogo {
  
  @NotEmpty
  private String codItem;
  
  @NotEmpty
  private String descItem;

}
