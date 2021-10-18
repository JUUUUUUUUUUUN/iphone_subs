package iphonesubscription;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import iphonesubscription.external.Subscription;
import iphonesubscription.external.SubscriptionService;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String customer;
    private String deviceName;
    private Integer quantity;

    @PostPersist
    public void onPostPersist(){
        System.out.println("\n\n============== PAYMENT onPostPersist() >> REST 결제 호출 ()==============\n\n  " + this.getId());
        PaymentCompleted paymentCompleted = new PaymentCompleted();
        BeanUtils.copyProperties(this, paymentCompleted);
        paymentCompleted.setPaymentStatus("paymentCompleted");
        System.out.println("\n\n============== PAYMENT onPostPersist() paymentCompleted.toJson() ()==============\n\n  " + paymentCompleted.toJson());

        paymentCompleted.publishAfterCommit();

        System.out.println("\n\n============== PAYMENT onPostPersist() paymentCompleted.publishAfterCommit()==============\n\n");
    
        
        try {
        	System.out.println("\n\n============== PAYMENT onPostPersist() 지연설정 ==============\n\n  ");
            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
        } catch (InterruptedException e) {
        	System.out.println("\n\n============== PAYMENT onPostPersist() 지연설정 에러 처리 ==============\n\n  ");
            e.printStackTrace();
        }
        
    }

    @PostUpdate
    public void onPostUpdate(){
        System.out.println("\n\n============== PAYMENT onPostUpdate() - 계약 취소 / 결제취소 호출 ============== \n\n");
        System.out.println("\n\n============== PAYMENT onPostUpdate() - this.getId() ============== \n\n" + this.getId());
        System.out.println("\n\n============== PAYMENT onPostUpdate() - this.getOrderId() ============== \n\n" + this.getOrderId());
        
        this.setOrderId(this.getId());

        PaymentCancelled paymentCancelled = new PaymentCancelled();
        BeanUtils.copyProperties(this, paymentCancelled);
        paymentCancelled.setPaymentStatus("paymentCancelled");

        //paymentCancelled.publish();
        paymentCancelled.publishAfterCommit();

        System.out.println("\n\n============== PAYMENT onPostUpdate() > paymentCancelled.publishAfterCommit()==============\n\n");
        
        Subscription subscription = new Subscription();
        BeanUtils.copyProperties(this, subscription);
        
        subscription.setPaymentStatus("paymentCancelled");
        
        System.out.println("============== PAYMENT onPostUpdate() - subscription.getOrderId()) ============== " + subscription.getOrderId());
        System.out.println("============== PAYMENT onPostUpdate() - subscription.getSubscriptionStatus() ============== " + subscription.getSubscriptionStatus());
        System.out.println("============== PAYMENT onPostUpdate() - subscription.getPaymentStatus() ============== " + subscription.getPaymentStatus() );

        PaymentApplication.applicationContext.getBean(SubscriptionService.class).cancelSubscription(subscription);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }




}