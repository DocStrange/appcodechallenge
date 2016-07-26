package com.shaunmccready.service;

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


}
