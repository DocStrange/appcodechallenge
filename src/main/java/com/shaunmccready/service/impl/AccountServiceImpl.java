package com.shaunmccready.service.impl;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.dto.EventDTO;
import com.shaunmccready.entity.Account;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.Status;
import com.shaunmccready.entity.User;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.AccountMapper;
import com.shaunmccready.repository.AccountDao;
import com.shaunmccready.repository.StatusDao;
import com.shaunmccready.repository.UserDao;
import com.shaunmccready.service.AccountService;
import com.shaunmccready.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private  AccountDao accountDao;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;


    @Transactional(rollbackFor = EventException.class)
    public AccountDTO createAccount(EventDTO eventDTO) throws EventException {
        if(null == eventDTO){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Missing details for Account creation");
        }

        userService.checkExistingUser(eventDTO);

        Status status = statusDao.findByName("ACTIVE");
        Account createdAccount = createNewAccount(status.getId(),eventDTO);
        accountDao.save(createdAccount);

        return accountMapper.bindDTO(createdAccount);
    }


    @Transactional(rollbackFor = EventException.class)
    public AccountDTO cancelAccount(EventDTO eventDTO) throws EventException {
        if(EventDTO.eventDTOContainsNulls(eventDTO.getPayload().getAccount(), eventDTO.getCreator())){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Missing details for Account cancellation");
        }

        Account uuidCheck = accountDao.findByAccountIdentifierIgnoreCase(eventDTO.getPayload().getAccount().getAccountIdentifier());
        if (null == uuidCheck){
            LOGGER.info("The account with uuid:[" + eventDTO.getPayload().getAccount().getAccountIdentifier() +
                    "] does not exists in the system.");
            throw new EventException(ErrorCodes.ACCOUNT_NOT_FOUND.getErrorCode(),"The account with uuid:[" +
                    eventDTO.getPayload().getAccount().getAccountIdentifier() + "] does not exists in the system.");
        }

        User owner = userDao.findByUuid(eventDTO.getCreator().getUuid());
        if(!owner.getAccountId().equals(uuidCheck.getId())){
            throw new EventException(ErrorCodes.UNAUTHORIZED.getErrorCode(), "Action not permitted. " +
                    "The user trying to cancel the subscription is not the owner of this account.");
        }

        Status status = statusDao.findByName("CANCELLED");
        uuidCheck.setStatusId(status.getId());
        accountDao.save(uuidCheck);

        return accountMapper.bindDTO(uuidCheck);
    }


    @Transactional(rollbackFor = EventException.class)
    public AccountDTO changeAccount(EventDTO eventDTO) throws EventException {
        if(null == eventDTO.getPayload() || null == eventDTO.getPayload().getAccount() || null == eventDTO.getPayload().getOrder()){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Missing details for Account change");
        }

        Account uuidCheck = accountDao.findByAccountIdentifierIgnoreCase(eventDTO.getPayload().getAccount().getAccountIdentifier());
        if (null == uuidCheck){
            LOGGER.info("The account with uuid:[" + eventDTO.getPayload().getAccount().getAccountIdentifier() +
                    "] does not exists in the system.");
            throw new EventException(ErrorCodes.ACCOUNT_NOT_FOUND.getErrorCode(),"The account with uuid:[" +
                    eventDTO.getPayload().getAccount().getAccountIdentifier() + "] does not exists in the system.");
        }

        uuidCheck.setEditionCode(eventDTO.getPayload().getOrder().getEditionCode());
        uuidCheck.setPricingDuration(eventDTO.getPayload().getOrder().getPricingDuration());

        return accountMapper.bindDTO(accountDao.save(uuidCheck));
    }


    /**
     * Helper to create and save a new account to the database
     *
     * @param statusId           the account status id
     * @param {@link EventDTO}   Event information
     * @return {@link Account}   new account
     */
    private Account createNewAccount(Integer statusId, EventDTO eventInformation) {
        Account account = new Account();
        account.setAccountIdentifier(UUID.randomUUID().toString());
        account.setNumberOfUsers(-1);
        account.setStatusId(statusId);

        if(null != eventInformation.getPayload() && null != eventInformation.getPayload().getOrder()) {
            account.setEditionCode(eventInformation.getPayload().getOrder().getEditionCode());
            account.setPricingDuration(eventInformation.getPayload().getOrder().getPricingDuration());
        }
        account.setModified(new Date());
        account.setCreated(new Date());

        return account;
    }



}

