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
    @Autowired SubscriptionRepository subscriptionRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCompleted_Subscribe(@Payload PaymentCompleted paymentCompleted){
        System.out.println("\n\n============== Subscription wheneverPaymentCompleted_Subscribe() : " + paymentCompleted.toJson() + "==============\n\n");

        System.out.println("\n\n============== paymentCompleted.getEventType() : " + paymentCompleted.getEventType() + "==============\n\n");

        if ("PaymentCompleted".equals(paymentCompleted.getEventType())) {
        	
        	System.out.println("\n\n============== Subscription / PolicyHandler >> PUB/SUB 예약 호출 처리 paymetCompleted.toJson() ==============\n\n" + paymentCompleted.toJson());
        	
        	if(!paymentCompleted.validate()) return;

            Subscription subscription = new Subscription();
            subscription.setOrderId(paymentCompleted.getOrderId());
            subscription.setCustomer(paymentCompleted.getCustomer());
            subscription.setDeviceName(paymentCompleted.getDeviceName());
            subscription.setQuantity(paymentCompleted.getQuantity());
            subscription.setPaymentStatus(paymentCompleted.getPaymentStatus());
            subscription.setSubscriptionStatus("PaymentCompleted > Subscribed");
            subscriptionRepository.save(subscription);
            
        } else {
        	System.out.println("\n\n============== Subscription / PolicyHandler >> PUB/SUB 예약 호출 pass ==============\n\n");
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}