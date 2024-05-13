package com.code.bankapp.kafka.topic;

import com.code.bankapp.kafka.constant.AppConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopic {

  @Bean
  public NewTopic newTopic(){
    return TopicBuilder
            .name(AppConstant.TOPIC_NAME)
            .build();
  }
}
