package com.shaunmccready.service;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.exception.EventException;

public interface AccountService {

    /**
     * Used to create new accounts
     *
     * @param eventDTO the event object containing the details
     * @return the created account details
     * @throws
     */
    public AccountDTO createAccount(EventDTO eventDTO) throws EventException;

    /**
     * Cancel the subscription to the service
     *
     * @param eventDTO
     * @return
     * @throws EventException
     */
    public AccountDTO cancelAccount(EventDTO eventDTO) throws EventException;

    /**
     * Change/modify the subscription
     *
     * @param eventDTO
     * @return
     * @throws EventException
     */
    public AccountDTO changeAccount(EventDTO eventDTO) throws EventException;


}
