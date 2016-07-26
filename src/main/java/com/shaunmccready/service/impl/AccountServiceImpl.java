package com.shaunmccready.service.impl;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.entity.Account;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.Status;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.AccountMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.StatusDao;
import com.shaunmccready.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private  AccountDao accountDao;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StatusDao statusDao;


    public AccountDTO createAccount(EventDTO eventDTO) throws EventException {
        if(eventDTOContainsNulls(eventDTO)){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "No details were given for Account creation");
        }

        Account uuidCheck = accountDao.findByUuidIgnoreCase(eventDTO.getPayload().getCompany().getUuid());
        if (null != uuidCheck){
            LOGGER.info("The account with uuid:[" + uuidCheck.getUuid() +
                    "] already exists in the system. Instead of creating a subscription, you should assign this user to the account");
            throw new EventException(ErrorCodes.USER_ALREADY_EXISTS.getErrorCode(),"The account with uuid:[" + uuidCheck.getUuid() +
                    "] already exists in the system. Instead of creating a subscription, you should assign this user to the account");
        }

        Status status = statusDao.findByName("ACTIVE");
        Account createdAccount = createNewAccount(status.getId());
        accountDao.save(createdAccount);

        return accountMapper.bindDTO(createdAccount);
    }


    public AccountDTO cancelAccount(EventDTO eventDTO) throws EventException {
        if(eventDTOContainsNulls(eventDTO)){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "No details were given for Account creation");
        }

        Account uuidCheck = accountDao.findByUuidIgnoreCase(eventDTO.getPayload().getCompany().getUuid());
        if (null == uuidCheck){
            LOGGER.info("The account with uuid:[" + eventDTO.getPayload().getCompany().getUuid() +
                    "] does not exists in the system.");
            throw new EventException(ErrorCodes.ACCOUNT_NOT_FOUND.getErrorCode(),"The account with uuid:[" +
                    eventDTO.getPayload().getCompany().getUuid() + "] does not exists in the system.");
        }

        Status status = statusDao.findByName("CANCELLED");
        uuidCheck.setStatusId(status.getId());
        accountDao.save(uuidCheck);

        return accountMapper.bindDTO(uuidCheck);
    }


    public AccountDTO changeAccount(EventDTO eventDTO) throws EventException {
        return null;
    }


    /**
     * Helper to create and save a new account to the database
     *
     * @param statusId the account status id
     * @return {@link Account} new account
     */
    private Account createNewAccount(Integer statusId) {
        Account account = new Account();
        account.setUuid(UUID.randomUUID().toString());
        account.setNumberOfUsers(-1);
        account.setStatusId(statusId);
        account.setModified(new Date());
        account.setCreated(new Date());

        return account;
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

