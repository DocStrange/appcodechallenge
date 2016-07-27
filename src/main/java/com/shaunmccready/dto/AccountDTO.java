package com.shaunmccready.dto;

import java.util.Date;

public class AccountDTO extends GenericDTO {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer numberOfUsers;

    private String accountIdentifier;

    private String status;

    private Integer statusId;

    private Date modified;

    private Date created;

    @Override
    public void setDefaultFields(){
        modified = new Date();
        created = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String uuid) {
        this.accountIdentifier = uuid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}

