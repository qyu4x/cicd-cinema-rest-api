package com.coffekyun.cinema.controller;

import com.coffekyun.cinema.model.request.PushNotificationRequest;
import com.coffekyun.cinema.model.response.PushNotificationResponse;
import com.coffekyun.cinema.model.response.WebResponse;
import com.coffekyun.cinema.service.PushNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification/")
public class PushNotificationController {

    private final static Logger log = LoggerFactory.getLogger(PushNotificationController.class);

    private final PushNotificationService pushNotificationService;

    @Autowired
    public PushNotificationController(PushNotificationService pushNotificationService) {
        this.pushNotificationService = pushNotificationService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/token")
    public ResponseEntity<?> pushNotificationWithToken(@RequestBody PushNotificationRequest pushNotificationRequest) {
        log.info("#calling controller pushNotificationWithToken");
        try {
            pushNotificationService.sendPushNotificationToToken(pushNotificationRequest);
            log.info("Successfully send notification to device token {} ", pushNotificationRequest.getToken());
            return new ResponseEntity<>(
                    new WebResponse<>(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase(),
                            new PushNotificationResponse(
                                    pushNotificationRequest.getTitle(),
                                    pushNotificationRequest.getMessage(),
                                    pushNotificationRequest.getToken()
                            )
                    ),
                    HttpStatus.OK
            );
        }catch (Exception exception) {
            log.info("Failed to send notification to device token {} ", pushNotificationRequest.getToken());
            return new ResponseEntity<>(
                    new WebResponse<>(
                            HttpStatus.INTERNAL_SERVER_ERROR.value(),
                            HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                            exception.getMessage()

                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
