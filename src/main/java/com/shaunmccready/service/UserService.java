package com.shaunmccready.service;


import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.exception.EventException;

public interface UserService {

    /**
     * Create a new user
     *
     * @param {@link EventDTO} eventInformation
     * @param {@link AccountDTO} account
     * @return
     */
    public UserDTO createUser(EventDTO eventInformation, AccountDTO account) throws EventException;


    /**
     * Remove user from system
     *
     * @param eventInformation
     * @return
     * @throws EventException
     */
    public UserDTO deleteUser(EventDTO eventInformation) throws EventException;


    /**
     * Helper to check if the user already exists in the system
     *
     * @param uuid id of the user
     * @return {@link Boolean}
     */
    public Boolean userExists(String uuid);
}
