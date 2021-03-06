package iphonesubscription.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name="Payment", url="${api.url.payment}")
public interface PaymentService {
    
    @RequestMapping(method = RequestMethod.POST, path = "/payments", consumes = "application/json")
    public void pay(@RequestBody Payment payment);

}

