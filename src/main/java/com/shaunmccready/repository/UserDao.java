package com.shaunmccready.repository;

import com.shaunmccready.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends GenericDao<User, Integer> {

    User findByUuid(String uuid);

}
