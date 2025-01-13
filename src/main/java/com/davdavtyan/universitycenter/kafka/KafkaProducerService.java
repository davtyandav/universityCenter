
package com.davdavtyan.universitycenter.kafka;

import com.kafka.avro.AssignmentNotification;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, AssignmentNotification> kafkaTemplate;

    private final String topic;

    public KafkaProducerService(KafkaTemplate<String, AssignmentNotification> kafkaTemplate,
                                @Value("${kafka.topic.assignment}")String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public void sendAssignmentNotification(AssignmentNotification notification) {
        kafkaTemplate.send(topic, notification);
    }

}