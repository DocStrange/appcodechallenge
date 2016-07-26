package com.shaunmccready.mapper;

import com.shaunmccready.dto.StatusDTO;
import com.shaunmccready.entity.Status;
import org.springframework.stereotype.Component;

@Component
public class StatusMapper extends GenericMapper<StatusDTO,Status>{

    @Override
    public StatusDTO bindDTO(Status entity) {
        return beanMapper.map(entity, StatusDTO.class);
    }

    @Override
    public StatusDTO bindDTO(Status entity, String bindMode) {
        StatusDTO statusDTO = bindDTO(entity);
        return statusDTO;
    }

    @Override
    public Status updateEntity(StatusDTO dto, Status entity) {
        beanMapper.map(dto,entity);
        return entity;
    }
}
