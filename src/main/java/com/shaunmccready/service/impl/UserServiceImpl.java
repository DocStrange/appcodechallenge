package com.shaunmccready.service.impl;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.CreatorDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.User;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.UserMapper;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.UserService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserMapper userMapper;


    @Override
    public UserDTO createUser(EventDTO eventInformation, AccountDTO account) throws EventException {
        if(eventDTOContainsNulls(eventInformation)){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "No details were given for Account creation");
        }

        User userExistsCheck = userDao.findByUuid(eventInformation.getCreator().getUuid());
        if(null != userExistsCheck){
            throw new EventException(ErrorCodes.USER_ALREADY_EXISTS.getErrorCode(), "The user with UUID:[" + eventInformation.getCreator().getUuid() +
                    "] already exists in the system.");
        }

        User newUser = createNewUser(eventInformation, account);
        return userMapper.bindDTO(userDao.save(newUser));
    }


    /**
     * To create and populate the new user
     *
     * @param eventInformation {@link EventDTO}
     * @param account {@link AccountDTO}
     * @return {@link User} New User Object
     */
    private User createNewUser(EventDTO eventInformation, AccountDTO account) {
        User user = new User();
        CreatorDTO creatorDTO = eventInformation.getCreator();

        user.setFirstName(Validate.notNull(creatorDTO.getAddress().getFirstName()));
        user.setLastName(Validate.notNull(creatorDTO.getAddress().getLastName()));
        user.setLanguage(Validate.notNull(creatorDTO.getLanguage()));
        user.setOpenId(Validate.notNull(creatorDTO.getOpenId()));
        user.setAttributes(Validate.notNull(creatorDTO.getAttributes()));
        user.setCity(Validate.notNull(creatorDTO.getAddress().getCity()));
        user.setCountry(Validate.notNull(creatorDTO.getAddress().getCountry()));
        user.setState(Validate.notNull(creatorDTO.getAddress().getState()));
        user.setStreet1(Validate.notNull(creatorDTO.getAddress().getStreet1()));
        user.setStreet2(Validate.notNull(creatorDTO.getAddress().getStreet2()));
        user.setZip(Validate.notNull(creatorDTO.getAddress().getZip()));
        user.setAccountId(Validate.notNull(account.getId()));
        user.setUuid(Validate.notNull(creatorDTO.getUuid()));

        user.setModified(new Date());
        user.setCreated(new Date());

        return user;
    }


    /**
     * Helper method to make sure there are no null fields which cause exceptions
     *
     * @param eventDTO  the event object containing the details
     * @return boolean  whether it contains null objects or not
     */
    private boolean eventDTOContainsNulls(EventDTO eventDTO) {
        if(null == eventDTO || null == eventDTO.getCreator() || null == eventDTO.getMarketplace() || null == eventDTO.getPayload() ||
                null == eventDTO.getCreator().getAddress() || null == eventDTO.getPayload().getCompany() || null == eventDTO.getPayload().getOrder()){
            return true;
        }else{
            return false;
        }
    }
}
