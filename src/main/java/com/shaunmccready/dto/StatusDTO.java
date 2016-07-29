package com.shaunmccready.dto;

import com.shaunmccready.entity.Account;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class StatusDTO extends GenericDTO{

    public static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Set<Account> accounts = new HashSet<>();

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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
