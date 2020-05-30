package com.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Author: W.B.An
 * @Date: 2020/5/16(星期六)
 * @Time: 17:19
 * 序号	站名	设备名称	设备类型	正线/侧线 天窗	检修条件	管辖单位
 */
public class ExcelDevice implements Serializable {
    @Excel(name = "序号", orderNum = "0")
    private Long id;
    @Excel(name = "站名", orderNum = "1")
    private String zName;
    @Excel(name = "设备名称", orderNum = "2")
    private String sbName;
    @Excel(name = "设备类型", orderNum = "3")
    private String sbType;
    @Excel(name = "正线/侧线", orderNum = "4")
    private String xb;
    @Excel(name = "天窗", orderNum = "5")
    private String tc;
    @Excel(name = "检修条件", orderNum = "6")
    private String jxCondition;
    @Excel(name = "管辖单位", orderNum = "7")
    private String gxdw;

    public ExcelDevice() {
    }

    public ExcelDevice(Long id, String zName, String sbName, String sbType, String xb, String tc, String jxCondition, String gxdw) {
        this.id = id;
        this.zName = zName;
        this.sbName = sbName;
        this.sbType = sbType;
        this.xb = xb;
        this.tc = tc;
        this.jxCondition = jxCondition;
        this.gxdw = gxdw;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getzName() {
        return zName;
    }

    public void setzName(String zName) {
        this.zName = zName;
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

    public String getJxCondition() {
        return jxCondition;
    }

    public void setJxCondition(String jxCondition) {
        this.jxCondition = jxCondition;
    }

    public String getGxdw() {
        return gxdw;
    }

    public void setGxdw(String gxdw) {
        this.gxdw = gxdw;
    }

    @Override
    public String toString() {
        return "ExcelDevice{" +
                "id=" + id +
                ", zName='" + zName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", sbType='" + sbType + '\'' +
                ", xb='" + xb + '\'' +
                ", tc='" + tc + '\'' +
                ", jxCondition='" + jxCondition + '\'' +
                ", gxdw='" + gxdw + '\'' +
                '}';
    }
}
