package com.code.bankapp.kafka.consumer;

import com.code.bankapp.kafka.constant.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.KafkaException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumer {
  @KafkaListener(topics = AppConstant.TOPIC_NAME, groupId = AppConstant.GROUP_ID)
  public void consumer(String message) {
    try {
      log.info("message received from bank-topic: {}", message);
    } catch (KafkaException e) {
      log.error("error in consumer : {}", e.getMessage());
      throw e;
    }
  }
}
