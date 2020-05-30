package com.model;

import java.io.Serializable;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 7:23
 */
public class EmployeeModel implements Serializable {
    private Long id;
    private String emName;
    private String zw;
    private String dw;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmName() {
        return emName;
    }

    public void setEmName(String emName) {
        this.emName = emName;
    }

    public String getZw() {
        return zw;
    }

    public void setZw(String zw) {
        this.zw = zw;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    @Override
    public String toString() {
        return "EmployeeModel{" +
                "id=" + id +
                ", emName='" + emName + '\'' +
                ", zw='" + zw + '\'' +
                ", dw='" + dw + '\'' +
                '}';
    }
}
