/*
 * package com.xuefeng.garbage;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.kafka.annotation.KafkaListener; import
 * org.springframework.stereotype.Service;
 * 
 * @Service public class KafkaConsumerService {
 * 
 * private final Logger logger =
 * LoggerFactory.getLogger(KafkaConsumerService.class);
 * 
 * @KafkaListener(topics="users", groupId="group_id") public void consume(String
 * message) { logger.info(String.format("$$ -> Consumed Message -> %s",
 * message)); }
 * 
 * }
 */