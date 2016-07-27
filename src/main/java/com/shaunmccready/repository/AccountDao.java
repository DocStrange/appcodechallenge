package com.shaunmccready.repository;

import com.shaunmccready.entity.Account;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountDao extends GenericDao<Account, Integer> {

    Account findByAccountIdentifierIgnoreCase(String uuid);

}
