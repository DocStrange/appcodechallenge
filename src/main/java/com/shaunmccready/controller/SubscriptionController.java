package com.shaunmccready.controller;

import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;


    @RequestMapping(value = "/create", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO createSubscription(
            @RequestParam (value = "url" , required = true , defaultValue = "none") String incomingUrl) throws EventException {
        return subscriptionService.createSubscription(incomingUrl);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO cancelSubscription(
            @RequestParam (value = "url" , required = true , defaultValue = "none") String incomingUrl) throws EventException {
        return subscriptionService.cancelSubscription(incomingUrl);
    }

    @RequestMapping(value = "/change", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO changeSubscription(
            @RequestParam (value = "url" , required = true , defaultValue = "none") String incomingUrl) throws EventException {
        return subscriptionService.changeSubscription(incomingUrl);
    }

}
