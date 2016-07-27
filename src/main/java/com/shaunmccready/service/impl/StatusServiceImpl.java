package com.shaunmccready.service.impl;


import com.shaunmccready.dto.StatusDTO;
import com.shaunmccready.entity.ErrorCodes;
import com.shaunmccready.entity.Status;
import com.shaunmccready.exception.EventException;
import com.shaunmccready.mapper.StatusMapper;
import com.shaunmccready.repository.StatusDao;
import com.shaunmccready.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StatusServiceImpl implements StatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusServiceImpl.class);

    @Autowired
    private StatusDao statusDao;

    @Autowired
    private StatusMapper statusMapper;


    public StatusDTO getStatus(final Integer id) throws EventException {
        Status status = statusDao.findOne(id);

        if(null == status){
            throw new EventException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), "Status with id [" + id + "] not found");
        }

        return statusMapper.bindDTO(status);
    }



}

