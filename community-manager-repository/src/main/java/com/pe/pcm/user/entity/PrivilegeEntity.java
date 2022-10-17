//package com.pe.pcm.user.entity;
//
//import javax.persistence.*;
//import java.util.Collection;
//
///**
// * @author Kiran Reddy.
// */
//@Entity
//@Table(name = "PETPE_SO_USER_PRIVILEGE")
//public class PrivilegeEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//
//    @ManyToMany(mappedBy = "privileges")
//    private Collection<RoleEntity> roles;
//
//    public Long getId() {
//        return id;
//    }
//
//    public PrivilegeEntity setId(Long id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public PrivilegeEntity setName(String name) {
//        this.name = name;
//        return this;
//    }
//
//    public Collection<RoleEntity> getRoles() {
//        return roles;
//    }
//
//    public PrivilegeEntity setRoles(Collection<RoleEntity> roles) {
//        this.roles = roles;
//        return this;
//    }
//}
