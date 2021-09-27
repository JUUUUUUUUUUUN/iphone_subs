package iphonesubscription;

import iphonesubscription.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCompleted_UpdateMyPage(@Payload OrderCompleted orderCompleted){

        if(!orderCompleted.validate()) return;

        System.out.println("\n\n##### listener UpdateMyPage : " + orderCompleted.toJson() + "\n\n");



        // Sample Logic //

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCompleted_UpdateMyPage(@Payload PaymentCompleted paymentCompleted){

        if(!paymentCompleted.validate()) return;

        System.out.println("\n\n##### listener UpdateMyPage : " + paymentCompleted.toJson() + "\n\n");



        // Sample Logic //

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_UpdateMyPage(@Payload PaymentCancelled paymentCancelled){

        if(!paymentCancelled.validate()) return;

        System.out.println("\n\n##### listener UpdateMyPage : " + paymentCancelled.toJson() + "\n\n");



        // Sample Logic //

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverSubscribed_UpdateMyPage(@Payload Subscribed subscribed){

        if(!subscribed.validate()) return;

        System.out.println("\n\n##### listener UpdateMyPage : " + subscribed.toJson() + "\n\n");



        // Sample Logic //

    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverSubscriptionCancelled_UpdateMyPage(@Payload SubscriptionCancelled subscriptionCancelled){

        if(!subscriptionCancelled.validate()) return;

        System.out.println("\n\n##### listener UpdateMyPage : " + subscriptionCancelled.toJson() + "\n\n");



        // Sample Logic //

    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}