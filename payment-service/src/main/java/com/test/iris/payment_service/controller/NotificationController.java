/*
package com.test.iris.payment_service.controller;

import com.test.iris.payment_service.service.SnsNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private SnsNotificationService snsNotificationService;

    @PostMapping("/payment-confirmation")
    public String sendPaymentConfirmation(@RequestParam String email, @RequestParam String message) {
        return snsNotificationService.sendPaymentConfirmation(email, message);
    }

}
*/
