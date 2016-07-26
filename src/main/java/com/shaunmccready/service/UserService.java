package com.shaunmccready.service;


import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.exception.EventException;

public interface UserService {

    /**
     * Create a new user
     *
     * @param eventInformation
     * @param account
     * @return
     */
    public UserDTO createUser(EventDTO eventInformation, AccountDTO account) throws EventException;


}
