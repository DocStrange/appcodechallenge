package com.shaunmccready.mapper;

import com.google.common.collect.Lists;
import com.shaunmccready.dto.GenericDTO;
import com.shaunmccready.entity.GenericEntity;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;


public abstract class GenericMapper<T extends GenericDTO, E extends GenericEntity> {

    /**
     * Dozer Bean Mapper Singleton Object
     */
    @Autowired
    protected DozerBeanMapper beanMapper;


    /**
     * Convert an entity into a DTO
     *
     * @param entity an entity
     * @return a DTO
     */
    public abstract T bindDTO(E entity);

    /**
     * Convert an entity into a DTO
     *
     * @param entity   an entity
     * @param bindMode a string of comma separated binding option requested.
     * @return an entity
     */
    public abstract T bindDTO(E entity, String bindMode);

    /**
     * Convert a DTO Object into an Entity Object
     *
     * @param dto    a dto
     * @param entity an entity
     * @return an entity
     * //@throws PropertyNotFoundException
     */
    public abstract E updateEntity(T dto, E entity);

    /**
     * Convert multiple entity from a paginated object into a List of DTO
     *
     * @param entities Page<Entity>
     * @param bindMode a string of comma separated binding option requested.
     * @return List<DTO>
     */
    public List<T> bindDTOList(Page<? extends E> entities, String bindMode) {
        List<T> list = Lists.newArrayList();
        for (E entity : entities) {
            list.add(this.bindDTO(entity, bindMode));
        }
        return list;
    }

}

