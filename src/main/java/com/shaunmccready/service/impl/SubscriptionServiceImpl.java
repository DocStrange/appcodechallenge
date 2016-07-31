package com.shaunmccready.service.impl;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.service.AccountService;
import com.shaunmccready.service.AppDirectConnectionService;
import com.shaunmccready.service.SubscriptionService;
import com.shaunmccready.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SubscriptionServiceImpl implements SubscriptionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubscriptionServiceImpl.class);

    @Autowired
    private AppDirectConnectionService appDirectConnectionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;


    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO createSubscription(String incomingUrl)  {
        ResponseDTO responseDTO;

        try {
            String eventDetailsString = appDirectConnectionService.getEventDetails(incomingUrl);
            EventDTO eventInformation = appDirectConnectionService.getEventDtoFromString(eventDetailsString);
            AccountDTO account = accountService.createAccount(eventInformation);
            userService.createUser(eventInformation, account);

            responseDTO = ResponseDTO.buildSuccessResponse(account.getAccountIdentifier(), "Subscription created!");
        } catch (EventException e) {
            responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(), e.getMessage());
        }

        return responseDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO cancelSubscription(String incomingUrl) {
        ResponseDTO responseDTO;

        try {
            String eventDetailsString = appDirectConnectionService.getEventDetails(incomingUrl);
            EventDTO eventInformation = appDirectConnectionService.getEventDtoFromString(eventDetailsString);
            AccountDTO account = accountService.cancelAccount(eventInformation);

            responseDTO = ResponseDTO.buildSuccessResponse(account.getAccountIdentifier().toString(), "Subscription cancelled!");
        } catch (EventException e) {
            responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(),e.getMessage());
        }

        return responseDTO;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO changeSubscription(String incomingUrl) {
        ResponseDTO responseDTO = null;

        try {
            String eventDetailsString = appDirectConnectionService.getEventDetails(incomingUrl);
            EventDTO eventInformation = appDirectConnectionService.getEventDtoFromString(eventDetailsString);
            AccountDTO account = accountService.changeAccount(eventInformation);

            responseDTO = ResponseDTO.buildSuccessResponse(account.getId().toString(), "Subscription created!");
        } catch (EventException e) {
            responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(),e.getMessage());
        }

        return responseDTO;
    }

}
