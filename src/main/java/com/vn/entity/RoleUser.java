package com.vn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role_user", schema = "project_test")
public class RoleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_USER_ID", nullable = false)
    private Long roleUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(name = "IS_ACTIVE")
    private int isActive;

    public RoleUser() {
    }

    public RoleUser(Role role, User user, int isActive) {
        this.role = role;
        this.user = user;
        this.isActive = isActive;
    }

    public Long getRoleUserId() {
        return roleUserId;
    }

    public void setRoleUserId(Long roleUserId) {
        this.roleUserId = roleUserId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }
}
