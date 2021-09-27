package iphonesubscription;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Subscription_table")
public class Subscription {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String customer;
    private String deviceName;
    private Integer quantity;
    private String subscriptionStatus;
    private String paymentStatus;

    @PostPersist
    public void onPostPersist(){
        System.out.println("\n\n==================== Subscription onPostPersist() >> 예약 호출 ====================\n\n");
        Subscribed subscribed = new Subscribed();
        BeanUtils.copyProperties(this, subscribed);
        subscribed.setSubscriptionStatus("subscribed");
        subscribed.publishAfterCommit();

        System.out.println("\n\n==================== Subscription onPostPersist() >> publishAfterCommit() 호출 ====================\n\n");
    }
    @PostUpdate
    public void onPostUpdate(){
        System.out.println("\n\n==================== Subscription onPostUpdate() >> 예약취소 호출 this.getId(): " + this.getId() + "====================\n\n");
        System.out.println("\n\n==================== Subscription onPostUpdate() >> this.getOrderId(): " + this.getOrderId() + "====================\n\n");
        SubscriptionCancelled subscriptionCancelled = new SubscriptionCancelled();
        BeanUtils.copyProperties(this, subscriptionCancelled);
        subscriptionCancelled.setSubscriptionStatus("subscriptionCancelled");
        System.out.println("\n\n==================== Subscription onPostUpdate() >> subscriptionCancelled.toJson()" + subscriptionCancelled.toJson() + "====================\n\n");
        
        subscriptionCancelled.publishAfterCommit();
        System.out.println("\n\n==================== Subscription onPostUpdate() >> publishAfterCommit() 호출 ====================\n\n");
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
    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }




}