package com.coffekyun.cinema.service;

import com.coffekyun.cinema.firebase.FirebaseCloudMessagingService;
import com.coffekyun.cinema.model.request.PushNotificationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationServiceServiceImpl implements PushNotificationService {

    private final static Logger log = LoggerFactory.getLogger(PushNotificationServiceServiceImpl.class);

    private final FirebaseCloudMessagingService firebaseCloudMessagingService;

    @Autowired
    public PushNotificationServiceServiceImpl(FirebaseCloudMessagingService firebaseCloudMessagingService) {
        this.firebaseCloudMessagingService = firebaseCloudMessagingService;
    }

    @Override
    public void sendPushNotificationToToken(PushNotificationRequest pushNotificationRequest) {
        try {
            firebaseCloudMessagingService.sendMessageToToken(pushNotificationRequest);
        } catch (Exception exception) {
            log.error("Opps {}", exception.getMessage());
        }
    }
}
