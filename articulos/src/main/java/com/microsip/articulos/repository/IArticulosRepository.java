package com.microsip.articulos.repository;

import com.microsip.articulos.model.ArticulosDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IArticulosRepository extends MongoRepository<ArticulosDto,String> {

}
