package com.code.bankapp.kafka.producer;

import com.code.bankapp.kafka.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

  public final KafkaTemplate<String, String> kafkaTemplate;

  public void sendMessage(String message) {
    try {
      log.info("message sent to bank-topic : {}", message);
      kafkaTemplate.send(AppConstant.TOPIC_NAME, message);
    } catch (KafkaException e) {
      log.error("error in producer : {}", e.getMessage());
      throw e;
    }
  }
}
