package com.shaunmccready.service.impl;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.entity.ErrorCodes;
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

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;

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
            EventDTO eventInformation = getEventDtoFromString(eventDetailsString);
            AccountDTO account = accountService.createAccount(eventInformation);
            userService.createUser(eventInformation, account);

            responseDTO = ResponseDTO.buildSuccessResponse(account.getAccountIdentifier(), "Subscription created!");
        } catch (EventException e) {
                responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(), e.getMessage());
        }

        return responseDTO;
    }


    private EventDTO getEventDtoFromString(String eventDetails) throws EventException {
        EventDTO value;
        try {
            StringReader stringReader = new StringReader(eventDetails);
            JAXBContext jaxbContext = JAXBContext.newInstance(EventDTO.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement<EventDTO> eventDtoElement = unmarshaller.unmarshal(new StreamSource(stringReader), EventDTO.class);
            value = eventDtoElement.getValue();
        } catch (JAXBException e) {
            LOGGER.info("There was an issue with processing the data. Please try again later." , e);
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "There was an issue with processing the data. Please try again later." , e);
        }

        return value;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO cancelSubscription(String incomingUrl) {
        ResponseDTO responseDTO;

        try {
            String eventDetailsString = appDirectConnectionService.getEventDetails(incomingUrl);
            EventDTO eventInformation = getEventDtoFromString(eventDetailsString);
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
            EventDTO eventInformation = getEventDtoFromString(eventDetailsString);
            AccountDTO account = accountService.changeAccount(eventInformation);

            responseDTO = ResponseDTO.buildSuccessResponse(account.getId().toString(), "Subscription created!");
        } catch (EventException e) {
            responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(),e.getMessage());
        }

        return responseDTO;
    }

}
