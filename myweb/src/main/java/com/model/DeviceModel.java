package com.model;

import java.io.Serializable;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 6:56
 */
public class DeviceModel implements Serializable {
    private Long id;
    private String zname;
    private String gxdw;
    private String sbName;
    private String sbType;
    private String xb;
    private String tc;
    private String jxCondiction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZname() {
        return zname;
    }

    public void setZname(String zname) {
        this.zname = zname;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getSbType() {
        return sbType;
    }

    public void setSbType(String sbType) {
        this.sbType = sbType;
    }

    public String getXb() {
        return xb;
    }

    public void setXb(String xb) {
        this.xb = xb;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getGxdw() {
        return gxdw;
    }

    public void setGxdw(String gxdw) {
        this.gxdw = gxdw;
    }

    public String getJxCondiction() {
        return jxCondiction;
    }

    public void setJxCondiction(String jxCondiction) {
        this.jxCondiction = jxCondiction;
    }

    @Override
    public String toString() {
        return "DeviceModel{" +
                "id=" + id +
                ", zname='" + zname + '\'' +
                ", gxdw='" + gxdw + '\'' +
                ", sbName='" + sbName + '\'' +
                ", sbType='" + sbType + '\'' +
                ", xb='" + xb + '\'' +
                ", tc='" + tc + '\'' +
                ", jxCondiction='" + jxCondiction + '\'' +
                '}';
    }
}
