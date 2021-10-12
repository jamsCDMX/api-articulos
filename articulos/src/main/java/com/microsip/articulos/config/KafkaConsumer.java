package com.microsip.articulos.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @KafkaListener(topics="topic-articulos", groupId="group-eventos")
    public void consumeFromTopic(String mensaje) {
        log.info("Mensaje de api-articulos(articulos): "+mensaje);
    }

}