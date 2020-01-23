/*
 * package com.xuefeng.garbage;
 * 
 * import org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * @RestController
 * 
 * @RequestMapping(value="/message") public class KafkaController {
 * 
 * private final KafkaProducerService producer;
 * 
 * KafkaController(KafkaProducerService producer){ this.producer = producer; }
 * 
 * @RequestMapping(value="/publish") public void
 * sendMessageToKafkaTopic(@RequestParam("message") String message) {
 * this.producer.sendMessage(message); }
 * 
 * } //TODO partitoning Asycron
 */