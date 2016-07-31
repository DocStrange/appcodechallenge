package com.shaunmccready.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;


@NoRepositoryBean
public interface GenericDao<T,ID extends Serializable> extends CrudRepository<T,ID> {

    Iterable<T> findAll();

    List<T> findAll(Sort sort);

    Page<T> findAll(Pageable pageable);

    List<T> findAll(Specification<?> specification);



}
