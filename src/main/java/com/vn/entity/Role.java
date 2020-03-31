package com.vn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "project_test")
public class Role {
    private int roleId;
    private Integer status;
    private String roleName;
    private String description;
    private String roleCode;
    private Integer roleOrder;

    public Role() {
    }

    public Role(int status,String roleName, String description, String roleCode, int roleOrder) {
          this.status = status;
          this.roleName = roleName;
          this.description = description;
          this.roleCode = roleCode;
          this.roleOrder = roleOrder;
    }
    @Id
    @Column(name = "ROLE_ID")
    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ROLE_NAME")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Basic
    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "ROLE_CODE")
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Basic
    @Column(name = "ROLE_ORDER")
    public Integer getRoleOrder() {
        return roleOrder;
    }

    public void setRoleOrder(Integer roleOrder) {
        this.roleOrder = roleOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role that = (Role) o;
        return roleId == that.roleId &&
                Objects.equals(status, that.status) &&
                Objects.equals(roleName, that.roleName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(roleCode, that.roleCode) &&
                Objects.equals(roleOrder, that.roleOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleId, status, roleName, description, roleCode, roleOrder);
    }
}
