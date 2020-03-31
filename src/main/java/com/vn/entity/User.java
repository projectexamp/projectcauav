package com.vn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "project_test")
public class User {
    private int userId;
    private String userName;
    private String fullName;
    private Integer gender;
    private String password;
    private Integer status;

    public User() {
    }

    public User(Integer status, String userName, String fullName, Integer gender, String password) {
        this.userName = userName;
        this.fullName = fullName;
        this.gender = gender;
        this.password = password;
        this.status = status;
    }
    @Id
    @Column(name = "USER_ID")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USER_NAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "FULL_NAME")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "GENDER")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return userId == that.userId &&
                Objects.equals(userName, that.userName) &&
                Objects.equals(fullName, that.fullName) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(password, that.password) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, fullName, gender, password, status);
    }
}
