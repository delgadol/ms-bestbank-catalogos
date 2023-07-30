package com.bestbank.catalogos.domain.model;

import java.util.Date;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "catalogos")
@Data
public class Catalogo {
  
  @Id
  private String id;
  
  private String codContrl;
  
  private String descCatalogo;
  
  private List<ItemCatalogo> valores;
  
  private Integer indEliminado;
  
  private Date fechaCreacion;
  
  private Date fechaModificacion;
  
  
}
