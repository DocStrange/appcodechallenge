package com.shaunmccready.dto;

import java.util.Date;


public class StatusDTO extends GenericDTO{

    public static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Date created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
