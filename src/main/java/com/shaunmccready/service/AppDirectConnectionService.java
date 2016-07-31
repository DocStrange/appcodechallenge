package com.shaunmccready.service;

import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.exception.EventException;
import org.springframework.stereotype.Service;

/**
 * Service for Connecting to AppDirect's API
 */
@Service
public interface AppDirectConnectionService {

    /**
     * Method used to connected to AppDirect and get the details of the event for processing
     *
     * @param incomingUrl
     * @return
     * @throws Exception
     */
    public String getEventDetails(String incomingUrl) throws EventException;

    /**
     * Marshall a string representing an event to an EventDTO object
     *
     * @param eventDetails
     * @return
     * @throws EventException
     */
    public EventDTO getEventDtoFromString(String eventDetails) throws EventException;
}
