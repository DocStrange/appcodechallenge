package com.shaunmccready.service;


import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.ResponseDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.entity.User;
import com.shaunmccready.exception.EventException;

public interface UserService {

    /**
     * Create a new user
     *
     * @param {@link EventDTO} eventInformation
     * @param {@link AccountDTO} account
     * @return {@link UserDTO}
     */
    public UserDTO createUser(EventDTO eventInformation, AccountDTO account) throws EventException;


    /**
     * Remove user from system
     *
     * @param eventInformation
     * @return {@link UserDTO}
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


    /**
     * Checks to see if a user exists in the system with a cancelled account
     *
     * @param {@link EvenDTO} eventDTO
     * @return {@link User}
     * @throws EventException
     */
    public User checkExistingInactiveUser(EventDTO eventDTO) throws EventException;

    /**
     * Assigns a user to an account
     *
     * @param eventUrl
     * @return {@link ResponseDTO}
     * @throws EventException
     */
    public ResponseDTO assignUser(String eventUrl) throws EventException;

    /**
     * Unassigns a user from an account
     *
     * @param eventUrl
     * @return {@link ResponseDTO}
     * @throws EventException
     */
    public ResponseDTO unassignUser(String eventUrl) throws EventException;


}
