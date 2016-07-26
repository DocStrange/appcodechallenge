package com.shaunmccready.service;

import com.shaunmccready.dto.StatusDTO;
import com.shaunmccready.exception.EventException;

public interface StatusService {


    /**
     * Get a single Subscription type status
     *
     * @param id
     * @return
     */
    public StatusDTO getStatus(final Integer id) throws EventException;


}
