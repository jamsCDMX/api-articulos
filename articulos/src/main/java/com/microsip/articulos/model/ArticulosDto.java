package com.microsip.articulos.model;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articulos")
@Data
@ToString
public class ArticulosDto {

    @Id
    private String _id;
    private String nombre;
    private String unidadMedida;
    private String clave;
    private Double precio;


}

