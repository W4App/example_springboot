package com.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Author: W.B.An
 * @Date: 2020/5/4(星期一)
 * @Time: 19:09
 * yam 读取用户配置文件
 * prefix = "customer" 引入数据段
 * 数据项yam和java严格对应!(U_NAME和yml文件拼写一致!)
 */
@Component
@ConfigurationProperties(prefix = "customer")
public class AppConfig {
    private String[] U_NAME;
    private String[] Z_NAME;

    public String[] getU_NAME() {
        return U_NAME;
    }

    public void setU_NAME(String[] u_NAME) {
        U_NAME = u_NAME;
    }

    public String[] getZ_NAME() {
        return Z_NAME;
    }

    public void setZ_NAME(String[] z_NAME) {
        Z_NAME = z_NAME;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "U_NAME=" + Arrays.toString(U_NAME) +
                ", Z_NAME=" + Arrays.toString(Z_NAME) +
                '}';
    }
}
