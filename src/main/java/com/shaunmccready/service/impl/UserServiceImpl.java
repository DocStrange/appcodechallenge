package com.shaunmccready.service.impl;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.CreatorDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.entity.Account;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.Status;
import com.shaunmccready.entity.User;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.UserMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AccountDao accountDao;


    @Override
    @Transactional(rollbackFor = EventException.class)
    public UserDTO createUser(EventDTO eventInformation, AccountDTO account) throws EventException {
        if(EventDTO.eventDTOContainsNulls(eventInformation, eventInformation.getCreator())){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "No details were given for Account creation");
        }

       if(userExists(eventInformation.getCreator().getUuid())){
           throw new EventException(ErrorCodes.USER_ALREADY_EXISTS.getErrorCode(), "The user with UUID:[" + eventInformation.getCreator().getUuid() +
                   "] already exists in the system.");
       }

        User user = checkExistingUser(eventInformation);
        if(null == user){
            user = createNewUser(eventInformation, account);
        }else{
            user.setAccountId(account.getId());
        }

        return userMapper.bindDTO(userDao.save(user));
    }

    @Override
    @Transactional(rollbackFor = EventException.class)
    public UserDTO deleteUser(EventDTO eventInformation) throws EventException {
        if(EventDTO.eventDTOContainsNulls(eventInformation, eventInformation.getCreator())){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "No details were given for Account creation");
        }

        User user = userDao.findByUuid(eventInformation.getCreator().getUuid());
        if(null == user){
            throw new EventException(ErrorCodes.USER_NOT_FOUND.getErrorCode(), "The user with UUID:[" + eventInformation.getCreator().getUuid() +
                    "] does not exists in the system.");
        }

        userDao.delete(user.getId());

        return userMapper.bindDTO(user);
    }


    public Boolean userExists(String uuid) {
        User userExistsCheck = userDao.findByUuid(uuid);
        if(null != userExistsCheck){
            return true;
        }
        return false;
    }


    public User checkExistingUser(EventDTO eventDTO) throws EventException {
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

    /**
     * To create and populate the new user
     *
     * @param {@link EventDTO} eventInformation
     * @param {@link AccountDTO} account
     * @return {@link User} New User Object
     */
    private User createNewUser(EventDTO eventInformation, AccountDTO account) {
        User user = new User();
        CreatorDTO creatorDTO = eventInformation.getCreator();

        if(null != creatorDTO.getAddress().getFirstName())
            user.setFirstName(creatorDTO.getAddress().getFirstName());
        if(null != creatorDTO.getAddress().getLastName())
            user.setLastName(creatorDTO.getAddress().getLastName());
        if(null != creatorDTO.getLanguage())
            user.setLanguage(creatorDTO.getLanguage());
        if(null != creatorDTO.getOpenId())
            user.setOpenId(creatorDTO.getOpenId());
        if(null != creatorDTO.getAttributes())
            user.setAttributes(creatorDTO.getAttributes());
        if(null != creatorDTO.getAddress().getCity())
            user.setCity(creatorDTO.getAddress().getCity());
        if(null != creatorDTO.getAddress().getCountry())
            user.setCountry(creatorDTO.getAddress().getCountry());
        if(null != creatorDTO.getAddress().getState())
            user.setState(creatorDTO.getAddress().getState());
        if(null != creatorDTO.getAddress().getStreet1())
            user.setStreet1(creatorDTO.getAddress().getStreet1());
        if(null != creatorDTO.getAddress().getStreet2())
            user.setStreet2(creatorDTO.getAddress().getStreet2());
        if(null != creatorDTO.getAddress().getZip())
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


}
