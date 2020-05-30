package com.service;

import com.bestvike.linq.Linq;
import com.mapper.UserInfoMapper;
import com.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/24(星期日)
 * @Time: 18:30
 */
@Service
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 预留注册账户用. 去重逻辑
     *
     * @param userInfo
     * @return
     */
    public boolean insertUser(UserInfo userInfo) {
        List<UserInfo> userInfos = Linq.of(userInfoMapper.selectAll())
                .where(x -> x.getUserName().toUpperCase().equals(userInfo.getUserName().toUpperCase()))
                .toList();
        if (userInfos.isEmpty()) {
            return userInfoMapper.insert(userInfo) > 1;
        } else {
            return false;
        }

    }

    public List<UserInfo> selectAll() {
        return userInfoMapper.selectAll();
    }

    public UserInfo findByUserName(String userName) {
        return userInfoMapper.findByUserName(userName);
    }
}
