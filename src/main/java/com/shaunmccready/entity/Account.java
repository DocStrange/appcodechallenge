package com.shaunmccready.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account", schema = "account")
public class Account implements GenericEntity{

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * Positive number means limit on users. Negative means unlimited
     */
    private Integer numberOfUsers;

    private String accountIdentifier;

    private Integer statusId;

    private Status status;

    private Set<User> users = new HashSet<>();

    private Date modified;

    private Date created;

    @Id
    @Column(name = "account_id", unique = true, nullable = false)
    @SequenceGenerator(name = "account_account_id_seq", sequenceName = "account.account_account_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "account_account_id_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "number_users")
    public Integer getNumberOfUsers() {
        return numberOfUsers;
    }

    public void setNumberOfUsers(Integer numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    @Column(name = "uuid")
    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String uuid) {
        this.accountIdentifier = uuid;
    }

    @ManyToOne()
    @JoinColumn(name = "status_id", nullable = true, insertable = false, updatable = false)
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "status_id")
    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


}
