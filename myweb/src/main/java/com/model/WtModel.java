package com.model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @Author: W.B.An
 * @Date: 2020/4/25(星期六)
 * @Time: 9:59
 */
public class WtModel implements Serializable {
    private Long id;
    private Long sbId;
    private LocalDate wtDate;
    private String wtScript;
    private LocalDate xjDate;
    private Boolean xj;

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

    public LocalDate getWtDate() {
        return wtDate;
    }

    public void setWtDate(LocalDate wtDate) {
        this.wtDate = wtDate;
    }

    public String getWtScript() {
        return wtScript;
    }

    public void setWtScript(String wtScript) {
        this.wtScript = wtScript;
    }

    public LocalDate getXjDate() {
        return xjDate;
    }

    public void setXjDate(LocalDate xjDate) {
        this.xjDate = xjDate;
    }

    public Boolean getXj() {
        return xj;
    }

    public void setXj(Boolean xj) {
        this.xj = xj;
    }

    @Override
    public String toString() {
        return "WtModel{" +
                "id=" + id +
                ", sbId=" + sbId +
                ", wtDate=" + wtDate +
                ", wtScript='" + wtScript + '\'' +
                ", xjDate=" + xjDate +
                ", xj=" + xj +
                '}';
    }
}
