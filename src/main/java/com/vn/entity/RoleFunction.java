package com.vn.entity;

import javax.persistence.Id;

import javax.persistence.*;

@Entity
@Table(name = "role_function", //
        uniqueConstraints = { //
                @UniqueConstraint(name = "ROLE_FUNCTION_ID", columnNames = "ROLE_FUNCTION_ID") })
public class RoleFunction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_FUNCTION_ID")
    private int roleFunctionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FUNCTION_ID", nullable = false)
    private Functions function;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Role role;

    @Column(name = "IS_ACTIVE")
    private int isActive;

    public RoleFunction() {
    }

    public RoleFunction(Functions functions, Role role, int isActive) {
        this.function = functions;
        this.role = role;
        this.isActive = isActive;
    }

    public int getRoleFunctionId() {
        return roleFunctionId;
    }

    public Functions getFunction() {
        return function;
    }

    public void setFunction(Functions function) {
        this.function = function;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public void setRoleFunctionId(int roleFunctionId) {
        this.roleFunctionId = roleFunctionId;
    }
}
