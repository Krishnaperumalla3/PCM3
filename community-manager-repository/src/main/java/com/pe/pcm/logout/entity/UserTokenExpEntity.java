package com.pe.pcm.logout.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_USER_TOKEN_EXP")
public class UserTokenExpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOKEN_EXP_GENERATOR")
    @SequenceGenerator(name = "TOKEN_EXP_GENERATOR", allocationSize = 1, sequenceName = "SEQ_USER_TOKEN_EXP")
    private Long id;
    private String userid;
    private String token;
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private Timestamp createdDate;

    public Long getId() {
        return id;
    }

    public UserTokenExpEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUserid() {
        return userid;
    }

    public UserTokenExpEntity setUserid(String userid) {
        this.userid = userid;
        return this;
    }

    public String getToken() {
        return token;
    }

    public UserTokenExpEntity setToken(String token) {
        this.token = token;
        return this;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public UserTokenExpEntity setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserTokenExpEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userid='" + userid + "'")
                .add("token='" + token + "'")
                .add("createdDate=" + createdDate)
                .toString();
    }
}
