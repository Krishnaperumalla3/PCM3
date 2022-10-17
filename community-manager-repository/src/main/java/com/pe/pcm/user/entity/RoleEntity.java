package com.pe.pcm.user.entity;

import javax.persistence.*;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_SO_USERS_ROLE")
public class RoleEntity {
    @Id
    private Long id;

    private String name;
    private String nameDes;

//    @ManyToMany(mappedBy = "roles")
//    private Collection<UserEntity> users;
//
//    @ManyToMany
//    @JoinTable(
//            name = "roles_privileges",
//            joinColumns = @JoinColumn(
//                    name = "role_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(
//                    name = "privilege_id", referencedColumnName = "id"))
//    private Collection<PrivilegeEntity> privileges;

    public Long getId() {
        return id;
    }

    public RoleEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getNameDes() {
        return nameDes;
    }

    public RoleEntity setNameDes(String nameDes) {
        this.nameDes = nameDes;
        return this;
    }
}
