package com.shaunmccready.mapper;

import com.shaunmccready.dto.AccountDTO;
import com.shaunmccready.entity.Account;
import com.shaunmccready.repository.StatusDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper extends GenericMapper<AccountDTO,Account>{


    @Override
    public AccountDTO bindDTO(Account entity) {
        return beanMapper.map(entity, AccountDTO.class);
    }

    @Override
    public AccountDTO bindDTO(Account entity, String bindMode) {
        AccountDTO accountDTO = bindDTO(entity);
        return accountDTO;
    }

    @Override
    public Account updateEntity(AccountDTO dto, Account entity) {
        beanMapper.map(dto,entity);
        return entity;
    }


}