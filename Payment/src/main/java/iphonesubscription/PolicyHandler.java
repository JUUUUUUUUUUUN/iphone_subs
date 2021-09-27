package iphonesubscription;

import iphonesubscription.config.kafka.KafkaProcessor;

import java.util.Optional;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @Autowired PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_CancelPayment(@Payload OrderCancelled orderCancelled){
        System.out.println("\n\n===================== PAYMENT PolicyHandler wheneverOrderCancelled_CancelPayment() =====================\n\n");
        System.out.println("\n\n===================== PAYMENT PolicyHandler orderCancelled.getEventType()" + orderCancelled.getEventType() + " =====================\n\n");

        if ("OrderCancelled".equals(orderCancelled.getEventType())) {
    	
    		System.out.println("\n\n===================== PAYMENT PolicyHandler >> PUB/SUB 예약취소 호출 처리 진입 =====================\n\n");
    		
    		try {
                if(!orderCancelled.validate()) return;

                // 객체 조회
                Optional<Payment> Optional = paymentRepository.findById(orderCancelled.getId());

                if( Optional.isPresent()) {
                    Payment payment = Optional.get();

                    // 객체에 이벤트의 eventDirectValue 를 set 함
                    payment.setCustomer(orderCancelled.getCustomer());
                    payment.setDeviceName(orderCancelled.getDeviceName());
                    payment.setQuantity(orderCancelled.getQuantity());
                    // 레파지토리에 save
                    paymentRepository.save(payment);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    	} else {
    		System.out.println("\n\n===================== PAYMENT PolicyHandler >> PUB/SUB 예약취소 로직 pass  =====================\n\n");
    	}
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}

}