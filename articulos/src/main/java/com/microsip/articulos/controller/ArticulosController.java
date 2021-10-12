package com.microsip.articulos.controller;

import com.microsip.articulos.config.KafkaProducer;
import com.microsip.articulos.model.ArticulosDto;
import com.microsip.articulos.repository.IArticulosRepository;
import com.microsip.articulos.response.GenericResponse;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
@CrossOrigin(
        origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE}
)
@RequestMapping("/api/articulos")
@Slf4j
public class ArticulosController {

    @Autowired
    IArticulosRepository articulosRepository;

    @Autowired
    KafkaProducer producer;

    @ApiOperation(
            value = "Crea Articulos",
            notes = "Servicio para crear articulos",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            response = ResponseEntity.class)
    @PostMapping("/articulo")
    public ResponseEntity<GenericResponse> crear(
            @Validated @RequestBody ArticulosDto articuloRequest) {
        try {
            log.info("-- Metodo crear");

             var result = articulosRepository.save(articuloRequest);

            producer.sendMessage("Se creo el artículo: "+ result.toString());

            return new ResponseEntity<>(GenericResponse.getOkResponse(articuloRequest), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(GenericResponse.getErrorResponse(e.hashCode(), e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Lista de Articulos",
            notes = "Servicio para consultar todos los articulos",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            response = ResponseEntity.class)
    @GetMapping("/")
    public ResponseEntity<GenericResponse> listarActivos() {
        try {
            log.info("-- Metodo listarActivos");

            var result = articulosRepository.findAll();

            producer.sendMessage("Se listaron los Articulos "+ result.toString());

            return new ResponseEntity<>(GenericResponse.getOkResponse(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(GenericResponse.getErrorResponse(e.hashCode(), e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Busca articulos por ID",
            notes = "Servicio para buscar articulos por ID",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            response = ResponseEntity.class)
    @GetMapping("/articulo/{id}")
    public ResponseEntity<GenericResponse> buscarArticuloPorID(@PathVariable String id) {
        try {
            log.info("-- Metodo actualizar");

            var result = articulosRepository.findById(id);

            producer.sendMessage("Se modificó el artículo: " + result.toString());

            return new ResponseEntity<>(GenericResponse.getOkResponse(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(GenericResponse.getErrorResponse(e.hashCode(), e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Borra articulos por ID",
            notes = "Servicio para borrar articulos por ID",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            response = ResponseEntity.class)
    @DeleteMapping("/articulo/{id}")
    public ResponseEntity<GenericResponse> borrarArticuloPorID(@PathVariable String id) {
        try {
            log.info("-- Metodo Borrar");

            articulosRepository.deleteById(id);

            producer.sendMessage("Se borro el artículo con id "+id);

            return new ResponseEntity<>(GenericResponse.getOkResponse(""), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(GenericResponse.getErrorResponse(e.hashCode(), e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Actualiza Articulos",
            notes = "Servicio para actualizar los articulos",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            response = ResponseEntity.class)
    @PutMapping("/articulo/{id}")
    public ResponseEntity<GenericResponse> actualizarArticulos(@PathVariable String id, @Validated @RequestBody ArticulosDto articuloRequest) {
        try {
            log.info("-- Metodo actualizar");

            var result = articulosRepository.save(articuloRequest);

            producer.sendMessage("Se modificó el artículo: "+ result.toString());

            return new ResponseEntity<>(GenericResponse.getOkResponse(result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(GenericResponse.getErrorResponse(e.hashCode(), e.getMessage()), HttpStatus.OK);
        }
    }

    @ApiOperation(
            value = "Test Kafka",
            notes = "test de kafka",
            consumes = MediaType.APPLICATION_JSON,
            produces = MediaType.APPLICATION_JSON,
            response = ResponseEntity.class)
    @PostMapping("/testKafka")
    public void testKafka(@RequestParam("msg") String msg) {
        try {
            log.info("-- Metodo test de kafka");

            producer.sendMessage("mensaje: " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
