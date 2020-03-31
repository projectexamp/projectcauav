package com.vn.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "functions", schema = "project_test")
public class Functions {
    private int functionId;
    private Integer status;
    private Integer functionOrder;
    private String functionUrl;
    private String functionName;
    private String description;
    private String functionCode;

    public Functions() {
    }

    public Functions(Integer status, Integer functionOrder, String functionUrl, String functionName, String description, String functionCode) {
        this.status = status;
        this.functionOrder = functionOrder;
        this.functionUrl = functionUrl;
        this.functionName = functionName;
        this.description = description;
        this.functionCode = functionCode;
    }

    @Id
    @Column(name = "FUNCTION_ID")
    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
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
    @Column(name = "FUNCTION_ORDER")
    public Integer getFunctionOrder() {
        return functionOrder;
    }

    public void setFunctionOrder(Integer functionOrder) {
        this.functionOrder = functionOrder;
    }

    @Basic
    @Column(name = "FUNCTION_URL")
    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    @Basic
    @Column(name = "FUNCTION_NAME")
    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
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
    @Column(name = "FUNCTION_CODE")
    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Functions that = (Functions) o;
        return functionId == that.functionId &&
                Objects.equals(status, that.status) &&
                Objects.equals(functionOrder, that.functionOrder) &&
                Objects.equals(functionUrl, that.functionUrl) &&
                Objects.equals(functionName, that.functionName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(functionCode, that.functionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(functionId, status, functionOrder, functionUrl, functionName, description, functionCode);
    }
}
