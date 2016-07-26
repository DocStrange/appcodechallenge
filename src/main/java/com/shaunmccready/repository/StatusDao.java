package com.shaunmccready.repository;

import com.shaunmccready.entity.Status;
import org.springframework.stereotype.Repository;


@Repository
public interface StatusDao extends GenericDao<Status, Integer>{

    Status findByName(String name);
}
