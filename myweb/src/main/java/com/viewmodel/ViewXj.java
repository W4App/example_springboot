package com.viewmodel;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * @Author: W.B.An
 * @Date: 2020/4/24(星期五)
 * @Time: 15:41
 */
public class ViewXj {
    private Long xj_id;
    @NotBlank(message = "设备名不能为空!")
    private String sb_name;
    @NotEmpty(message = "检修人不能为空")
    private String jx_name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate jx_date;
    @NotEmpty(message = "检修内容不能为空!")
    private String jx_content;
    private String nojx_content;
    private String jx_type;
    private boolean xj;
    private String zn;
    //页面初始
    private String[] znNames;
    private String[] jxrs;
    private String action;
    private String znCookie;
    private String flag;
    private Long sbId;

    public String getZn() {
        return zn;
    }

    public void setZn(String zn) {
        this.zn = zn;
    }

    public Long getXj_id() {
        return xj_id;
    }

    public void setXj_id(Long xj_id) {
        this.xj_id = xj_id;
    }

    public String getSb_name() {
        return sb_name;
    }

    public void setSb_name(String sb_name) {
        this.sb_name = sb_name;
    }

    public String getJx_name() {
        return jx_name;
    }

    public void setJx_name(String jx_name) {
        this.jx_name = jx_name;
    }

    public LocalDate getJx_date() {
        return jx_date;
    }

    public void setJx_date(LocalDate jx_date) {
        this.jx_date = jx_date;
    }

    public String getJx_content() {
        return jx_content;
    }

    public void setJx_content(String jx_content) {
        this.jx_content = jx_content;
    }

    public String getNojx_content() {
        return nojx_content;
    }

    public void setNojx_content(String nojx_content) {
        this.nojx_content = nojx_content;
    }

    public String getJx_type() {
        return jx_type;
    }

    public void setJx_type(String jx_type) {
        this.jx_type = jx_type;
    }

    public boolean isXj() {
        return xj;
    }

    public void setXj(boolean xj) {
        this.xj = xj;
    }

    public String[] getZnNames() {
        return znNames;
    }

    public void setZnNames(String[] znNames) {
        this.znNames = znNames;
    }

    public String[] getJxrs() {
        return jxrs;
    }

    public void setJxrs(String[] jxrs) {
        this.jxrs = jxrs;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getZnCookie() {
        return znCookie;
    }

    public void setZnCookie(String znCookie) {
        this.znCookie = znCookie;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Long getSbId() {
        return sbId;
    }

    public void setSbId(Long sbId) {
        this.sbId = sbId;
    }

    @Override
    public String toString() {
        return "ViewXj{" +
                "xj_id=" + xj_id +
                ", sb_name='" + sb_name + '\'' +
                ", jx_name='" + jx_name + '\'' +
                ", jx_date=" + jx_date +
                ", jx_content='" + jx_content + '\'' +
                ", nojx_content='" + nojx_content + '\'' +
                ", jx_type='" + jx_type + '\'' +
                ", xj=" + xj +
                ", zn='" + zn + '\'' +
                ", znNames=" + Arrays.toString(znNames) +
                ", jxrs=" + Arrays.toString(jxrs) +
                ", action='" + action + '\'' +
                ", znCookie='" + znCookie + '\'' +
                ", flag='" + flag + '\'' +
                ", sbId=" + sbId +
                '}';
    }
}
