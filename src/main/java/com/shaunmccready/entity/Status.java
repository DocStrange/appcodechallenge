package com.shaunmccready.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "status", schema = "account")
public class Status implements GenericEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Set<Account> accounts = new HashSet<>();

    private Date created;

    @Id
    @Column(name = "status_id", unique = true, nullable = false)
    @SequenceGenerator(name = "status_status_id_seq", sequenceName = "account.status_status_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "status_status_id_seq")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "status")
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
