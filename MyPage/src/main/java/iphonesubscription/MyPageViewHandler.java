package iphonesubscription;

import iphonesubscription.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MyPageViewHandler {


    @Autowired
    private MyPageRepository myPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenOrderCompleted_then_CREATE_1 (@Payload OrderCompleted orderCompleted) {
        System.out.println("\n\n============== MyPageViewHandler whenOrderCompleted_then_CREATE_1() 시작 ===========================\n\n");
        System.out.println("\n\n============== orderCompleted.getEventType(): " + orderCompleted.getEventType() + "===========================\n\n");

        try {

            if (!orderCompleted.validate()) return;

            // view 객체 생성
            MyPage myPage = new MyPage();
            // view 객체에 이벤트의 Value 를 set 함
            myPage.setOrderId(orderCompleted.getId());
            myPage.setCustomer(orderCompleted.getCustomer());
            myPage.setDeviceName(orderCompleted.getDeviceName());
            myPage.setQuantity(orderCompleted.getQuantity());
            // view 레파지 토리에 save
            myPageRepository.save(myPage);

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCompleted_then_UPDATE_1(@Payload PaymentCompleted paymentCompleted) {
        System.out.println("\n\n============== MyPageViewHandler whenPaymentCompleted_then_UPDATE_1() 시작 ===========================\n\n");

        try {
            //if (!paymentCompleted.validate()) return;
                // view 객체 조회
                System.out.println("\n\n============== paymentCompleted.getEventType(): " + paymentCompleted.getEventType() + "===========================\n\n");

                    List<MyPage> myPageList = myPageRepository.findByOrderId(paymentCompleted.getOrderId());
                    for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setPaymentStatus(paymentCompleted.getPaymentStatus());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscribed_then_UPDATE_2(@Payload Subscribed subscribed) {
        System.out.println("\n\n============== MyPageViewHandler whenSubscribed_then_UPDATE_2() 시작 ===========================\n\n");
        System.out.println("\n\n============== subscribed.getEventType(): " + subscribed.getEventType() + "===========================\n\n");

        try {
            if (!subscribed.validate()) return;
                // view 객체 조회

                    List<MyPage> myPageList = myPageRepository.findByOrderId(subscribed.getOrderId());
                    for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setSubscriptionStatus(subscribed.getSubscriptionStatus());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCancelled_then_UPDATE_3(@Payload PaymentCancelled paymentCancelled) {
        System.out.println("\n\n============== MyPageViewHandler whenPaymentCancelled_then_UPDATE_3() 시작 ===========================\n\n");
        System.out.println("\n\n============== paymentCancelled.getEventType(): " + paymentCancelled.getEventType() + "===========================\n\n");

        try {
            if (!paymentCancelled.validate()) return;
                // view 객체 조회

                    List<MyPage> myPageList = myPageRepository.findByOrderId(paymentCancelled.getOrderId());
                    for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setPaymentStatus(paymentCancelled.getPaymentStatus());
                    myPage.setQuantity(0);
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenSubscriptionCancelled_then_UPDATE_4(@Payload SubscriptionCancelled subscriptionCancelled) {
        System.out.println("\n\n============== MyPageViewHandler whenSubscriptionCancelled_then_UPDATE_4() 시작 ===========================\n\n");
        System.out.println("\n\n============== subscriptionCancelled.getEventType(): " + subscriptionCancelled.getEventType() + "===========================\n\n");

        try {
            if (!subscriptionCancelled.validate()) return;
                // view 객체 조회

                    List<MyPage> myPageList = myPageRepository.findByOrderId(subscriptionCancelled.getOrderId());
                    for(MyPage myPage : myPageList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    myPage.setSubscriptionStatus(subscriptionCancelled.getSubscriptionStatus());
                // view 레파지 토리에 save
                myPageRepository.save(myPage);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

