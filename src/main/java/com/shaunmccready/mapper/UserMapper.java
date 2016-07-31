package com.shaunmccready.mapper;

import com.shaunmccready.dto.UserDTO;
import com.shaunmccready.entity.User;
import org.springframework.stereotype.Component;

/**
 * A class used to Map a User Entity to the respective DTO and vice versa
 */
@Component
public class UserMapper extends GenericMapper<UserDTO, User> {


    @Override
    public UserDTO bindDTO(User entity) {
        return beanMapper.map(entity, UserDTO.class);
    }

    @Override
    public UserDTO bindDTO(User entity, String bindMode) {
        UserDTO userDTO = bindDTO(entity);
        return userDTO;
    }

    @Override
    public User updateEntity(UserDTO dto, User entity) {
        beanMapper.map(dto,entity);
        return entity;
    }
}
