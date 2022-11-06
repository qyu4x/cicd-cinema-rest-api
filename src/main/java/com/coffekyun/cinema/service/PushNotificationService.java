package com.coffekyun.cinema.service;

import com.coffekyun.cinema.model.request.PushNotificationRequest;

public interface PushNotificationService {

    void sendPushNotificationToToken(PushNotificationRequest pushNotificationRequest);

}
