package com.shaunmccready.repository;

import com.shaunmccready.entity.Stock;
import org.springframework.stereotype.Repository;


@Repository
public interface StockDao extends GenericDao<Stock, Integer> {
}
