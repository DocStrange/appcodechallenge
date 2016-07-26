package com.shaunmccready.mapper;

import com.shaunmccready.dto.StockDTO;
import com.shaunmccready.entity.Stock;
import org.springframework.stereotype.Component;


@Component
public class StockMapper extends GenericMapper<StockDTO,Stock> {


    @Override
    public StockDTO bindDTO(Stock entity) {
        return beanMapper.map(entity, StockDTO.class);
    }

    @Override
    public StockDTO bindDTO(Stock entity, String bindMode) {
        StockDTO dto = bindDTO(entity);

        return dto;
    }

    @Override
    public Stock updateEntity(StockDTO dto, Stock entity) {
        beanMapper.map(dto,entity);
        return entity;
    }
}
