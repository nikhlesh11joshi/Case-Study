/*
package com.test.iris.payment_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
public class SnsNotificationService {

    @Value("${aws.sns.topic.arn}")
    private String snsTopicArn;

    private final SnsClient snsClient;

    public SnsNotificationService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public String sendPaymentConfirmation(String email, String message) {
        String subject = "Payment Confirmation";

        PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(snsTopicArn)
                .subject(subject)
                .message(String.format("Dear %s,\n\n%s\n\nThank you!", email, message))
                .build();

        PublishResponse response = snsClient.publish(publishRequest);
        return response.messageId();
    }
}
*/
