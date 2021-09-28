package iphonesubscription;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

import iphonesubscription.external.Payment;
import iphonesubscription.external.PaymentService;

import java.util.List;
import java.util.Date;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String customer;
    private String deviceName;
    private Integer quantity;

    @PostPersist
    public void onPostPersist(){

        //configMap
        String systemMode = System.getenv("SYSTEM_MODE");
        if (systemMode == null) systemMode="LOCAL";
        System.out.println("SYSTEM MODE: " + systemMode);

        System.out.println("\n\n================== ORDER 시작 ==================\n\n");

        OrderCompleted orderCompleted = new OrderCompleted();
        BeanUtils.copyProperties(this, orderCompleted);
        orderCompleted.publishAfterCommit();

        System.out.println("\n\n================== ORDER onPostPersist() - orderCompleted.publishAfterCommit() ==================\n\n");

        System.out.println("\n\n================== orderCompleted.getEventType() ==================" + orderCompleted.getEventType() + "\n\n");

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        //iphonesubscription.external.Payment payment = new iphonesubscription.external.Payment();
        // mappings goes here
        //OrderApplication.applicationContext.getBean(iphonesubscription.external.PaymentService.class).pay(payment);

        System.out.println("\n\n================== ORDER >> PAYMENT REST 호출 시작 ==================\n\n");
        Payment payment = new Payment();

        BeanUtils.copyProperties(this, payment);
        payment.setOrderId(this.getId());
        OrderApplication.applicationContext.getBean(PaymentService.class).pay(payment);

        System.out.println("\n\n================== ORDER >> PAYMENT REST 호출 끝 ==================\n\n");

    }
    
    @PostUpdate
    public void onPostUpdate(){
        System.out.println("\n\n================== Order onPostUpdate() 주문 수정(취소) 시작 ==================\n\n");
        OrderCancelled orderCancelled = new OrderCancelled();
        BeanUtils.copyProperties(this, orderCancelled);
        orderCancelled.publishAfterCommit();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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