package com.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 7:10
 */
public class RepairModel implements Serializable {
    private Long id;
    private Long sbId;
    private String jxContent;
    private LocalDate jxDate;
    private String jxRen;
    private String jxType;
    private boolean xj;
    private String zn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSbId() {
        return sbId;
    }

    public void setSbId(Long sbId) {
        this.sbId = sbId;
    }

    public String getJxContent() {
        return jxContent;
    }

    public void setJxContent(String jxContent) {
        this.jxContent = jxContent;
    }

    public LocalDate getJxDate() {
        return jxDate;
    }

    public void setJxDate(LocalDate jxDate) {
        this.jxDate = jxDate;
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

    public boolean isXj() {
        return xj;
    }

    public void setXj(boolean xj) {
        this.xj = xj;
    }

    public String getZn() {
        return zn;
    }

    public void setZn(String zn) {
        this.zn = zn;
    }

    @Override
    public String toString() {
        return "RepairModel{" +
                "id=" + id +
                ", sbId=" + sbId +
                ", jxContent='" + jxContent + '\'' +
                ", jxDate=" + jxDate +
                ", jxRen='" + jxRen + '\'' +
                ", jxType='" + jxType + '\'' +
                ", xj=" + xj +
                ", zn='" + zn + '\'' +
                '}';
    }
}
