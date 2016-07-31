package com.shaunmccready.service.impl;

import com.shaunmccready.dto.*;
import com.shaunmccready.entity.Account;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.Status;
import com.shaunmccready.entity.User;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.AccountMapper;
import com.shaunmccready.mapper.UserMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.AppDirectConnectionService;
import com.shaunmccready.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AppDirectConnectionService appDirectConnectionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDTO createUser(EventDTO eventInformation, AccountDTO account) throws EventException {
        if(null == eventInformation || null == eventInformation.getCreator()){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Missing complete details for Account creation.");
        }

        User user = checkExistingInactiveUser(eventInformation);
        if(null == user){
            user = createNewUserFromCreatorInfo(eventInformation, account);
        }else{
            user.setAccountId(account.getId());
            user.setModified(new Date());
        }

        return userMapper.bindDTO(userDao.save(user));
    }


    public Boolean userExists(String uuid) {
        User userExistsCheck = userDao.findByUuid(uuid);
        return null != userExistsCheck;
    }


    public User checkExistingInactiveUser(EventDTO eventDTO) throws EventException {
        if(null == eventDTO.getCreator() || null == eventDTO.getCreator().getUuid()){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Missing complete details for Account creation.");
        }

        User userCheck = userDao.findByUuid(eventDTO.getCreator().getUuid());
        if(null != userCheck){
            Account existingAccount = accountDao.findOne(userCheck.getAccountId());
            Status accountStatus = existingAccount.getStatus();

            if(!accountStatus.getName().equals("CANCELLED")){
                throw new EventException(ErrorCodes.FORBIDDEN.getErrorCode(), "The user with UUID:[" + eventDTO.getCreator().getUuid() +
                        "] already exists in the system and is associated with account id:[" + existingAccount.getAccountIdentifier() + "] which is not cancelled");
            }
        }
        return userCheck;
    }

    @Override
    public ResponseDTO assignUser(String incomingUrl) throws EventException {
        ResponseDTO responseDTO;

        try {
            String eventDetailsString = appDirectConnectionService.getEventDetails(incomingUrl);
            EventDTO eventDTO = appDirectConnectionService.getEventDtoFromString(eventDetailsString);

            Account uuidCheck = accountDao.findByAccountIdentifierIgnoreCase(eventDTO.getPayload().getAccount().getAccountIdentifier());
            if (null == uuidCheck){
                LOGGER.info("The account with uuid:[" + eventDTO.getPayload().getAccount().getAccountIdentifier() +
                        "] does not exists in the system.");
                throw new EventException(ErrorCodes.ACCOUNT_NOT_FOUND.getErrorCode(),"The account with uuid:[" +
                        eventDTO.getPayload().getAccount().getAccountIdentifier() + "] does not exists in the system.");
            }

            User user = userDao.findByUuid(eventDTO.getPayload().getUser().getUuid());
            if(null == user){
                user = createNewUserFromUserInfo(eventDTO, accountMapper.bindDTO(uuidCheck));
            }

            user.setAccountId(uuidCheck.getId());
            user.setOwner(false);
            user.setModified(new Date());
            userDao.save(user);

            responseDTO = ResponseDTO.buildSuccessResponse(null, "User assigned!");
        } catch (EventException e) {
            responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(), e.getMessage());
        }

        return responseDTO;
    }


    @Override
    public ResponseDTO unassignUser(String incomingUrl) throws EventException {
        ResponseDTO responseDTO;

        try {
            String eventDetailsString = appDirectConnectionService.getEventDetails(incomingUrl);
            EventDTO eventDTO = appDirectConnectionService.getEventDtoFromString(eventDetailsString);

            Account uuidCheck = accountDao.findByAccountIdentifierIgnoreCase(eventDTO.getPayload().getAccount().getAccountIdentifier());
            if (null == uuidCheck){
                LOGGER.info("The account with uuid:[" + eventDTO.getPayload().getAccount().getAccountIdentifier() +
                        "] does not exists in the system.");
                throw new EventException(ErrorCodes.ACCOUNT_NOT_FOUND.getErrorCode(),"The account with uuid:[" +
                        eventDTO.getPayload().getAccount().getAccountIdentifier() + "] does not exists in the system.");
            }

            String userUUID = eventDTO.getPayload().getUser().getUuid();
            User user = userDao.findByUuid(userUUID);
            if(null == user){
                throw new EventException(ErrorCodes.ACCOUNT_NOT_FOUND.getErrorCode(),"The user with uuid:[" +
                        userUUID + "] does not exists in the system.");
            }

            user.setAccountId(null);
            user.setModified(new Date());
            userDao.save(user);

            responseDTO = ResponseDTO.buildSuccessResponse(null, "User unassigned!");
        } catch (EventException e) {
            responseDTO = ResponseDTO.buildFailedResponse(e.getErrorCode(), e.getMessage());
        }

        return responseDTO;
    }


    /**
     * To create and populate the new user from the Creator info
     *
     * @param {@link EventDTO} eventInformation
     * @param {@link AccountDTO} account
     * @return {@link User} New User Object
     */
    private User createNewUserFromCreatorInfo(EventDTO eventInformation, AccountDTO account) {
        User user = new User();

        CreatorDTO creatorDTO = eventInformation.getCreator();

        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getFirstName())
            user.setFirstName(creatorDTO.getAddress().getFirstName());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getLastName())
            user.setLastName(creatorDTO.getAddress().getLastName());
        if(null != creatorDTO.getLanguage())
            user.setLanguage(creatorDTO.getLanguage());
        if(null != creatorDTO.getOpenId())
            user.setOpenId(creatorDTO.getOpenId());
        if(null != creatorDTO.getAttributes())
            user.setAttributes(creatorDTO.getAttributes());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getCity())
            user.setCity(creatorDTO.getAddress().getCity());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getCountry())
            user.setCountry(creatorDTO.getAddress().getCountry());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getState())
            user.setState(creatorDTO.getAddress().getState());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getStreet1())
            user.setStreet1(creatorDTO.getAddress().getStreet1());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getStreet2())
            user.setStreet2(creatorDTO.getAddress().getStreet2());
        if(null != creatorDTO.getAddress() && null != creatorDTO.getAddress().getZip())
            user.setZip(creatorDTO.getAddress().getZip());
        if(null != account.getId())
            user.setAccountId(account.getId());
        if(null != creatorDTO.getUuid())
            user.setUuid(creatorDTO.getUuid());

        user.setOwner(true);

        user.setModified(new Date());
        user.setCreated(new Date());

        return user;
    }


    /**
     * To create and populate the new user from the User info
     *
     * @param {@link EventDTO} eventInformation
     * @param {@link AccountDTO} account
     * @return {@link User} New User Object
     */
    private User createNewUserFromUserInfo(EventDTO eventInformation, AccountDTO account) {
        User user = new User();

        UserDTO userDTO = eventInformation.getPayload().getUser();

        if(null != userDTO.getFirstName())
            user.setFirstName(userDTO.getFirstName());
        if(null != userDTO.getLastName())
            user.setLastName(userDTO.getLastName());
        if(null != userDTO.getLanguage())
            user.setLanguage(userDTO.getLanguage());
        if(null != userDTO.getOpenId())
            user.setOpenId(userDTO.getOpenId());
        if(null != userDTO.getAttributes())
            user.setAttributes(userDTO.getAttributes());
        if(null != userDTO.getCity())
            user.setCity(userDTO.getCity());
        if(null != userDTO.getCountry())
            user.setCountry(userDTO.getCountry());
        if(null != userDTO.getState())
            user.setState(userDTO.getState());
        if(null != userDTO.getStreet1())
            user.setStreet1(userDTO.getStreet1());
        if(null != userDTO.getStreet2())
            user.setStreet2(userDTO.getStreet2());
        if(null != userDTO.getZip())
            user.setZip(userDTO.getZip());
        if(null != account.getId())
            user.setAccountId(account.getId());
        if(null != userDTO.getUuid())
            user.setUuid(userDTO.getUuid());

        user.setOwner(true);

        user.setModified(new Date());
        user.setCreated(new Date());

        return user;
    }

}
