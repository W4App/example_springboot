package com.model;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: W.B.An
 * @Date: 2020/5/11(星期一)
 * @Time: 13:03
 */
public class RepairExcel implements Serializable {
    private Long id;
    @Excel(name = "站名", width = 8)
    private String zName;
    @Excel(name = "设备名称", width = 10)
    private String sbName;
    @Excel(name = "检修日期", width = 20, exportFormat = "yyyy-MM-dd")
    private LocalDate xjDate;
    @Excel(name = "检修人", width = 20)
    private String jxRen;
    @Excel(name = "检修内容", width = 30)
    private String jxContent;
    @Excel(name = "检修类型", width = 10)
    private String jxType;
    @Excel(name = "是否销记", width = 10)
    private String xj;
    @Excel(name = "问题", width = 40)
    private String wts;

    public RepairExcel() {
    }

    public RepairExcel(Long id, String zName, String sbName, LocalDate xjDate, String jxRen, String jxContent, String jxType, String xj, String wts) {
        this.id = id;
        this.zName = zName;
        this.sbName = sbName;
        this.xjDate = xjDate;
        this.jxRen = jxRen;
        this.jxContent = jxContent;
        this.jxType = jxType;
        this.xj = xj;
        this.wts = wts;
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

    public LocalDate getXjDate() {
        return xjDate;
    }

    public void setXjDate(LocalDate xjDate) {
        this.xjDate = xjDate;
    }

    public String getJxRen() {
        return jxRen;
    }

    public void setJxRen(String jxRen) {
        this.jxRen = jxRen;
    }

    public String getJxType() {
        return jxType;
    }

    public void setJxType(String jxType) {
        this.jxType = jxType;
    }

    public String getXj() {
        return xj;
    }

    public void setXj(String xj) {
        this.xj = xj;
    }

    public String getWts() {
        return wts;
    }

    public void setWts(String wts) {
        this.wts = wts;
    }

    public String getJxContent() {
        return jxContent;
    }

    public void setJxContent(String jxContent) {
        this.jxContent = jxContent;
    }

    @Override
    public String toString() {
        return "RepairExcel{" +
                "id=" + id +
                ", zName='" + zName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", xjDate=" + xjDate +
                ", jxRen='" + jxRen + '\'' +
                ", jxContent='" + jxContent + '\'' +
                ", jxType='" + jxType + '\'' +
                ", xj='" + xj + '\'' +
                ", wts='" + wts + '\'' +
                '}';
    }
}
