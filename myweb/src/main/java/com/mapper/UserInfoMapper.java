package com.mapper;

import com.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/24(星期日)
 * @Time: 18:17
 * user_info 库名
 * private Long id;
 * private String userName;
 * private String password;
 * private String Role;
 */
@Mapper
@Component
public interface UserInfoMapper {
    @Insert("INSERT INTO user_info (username,password,role)" +
            "VALUES (#{userName},#{password}, #{role})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(UserInfo model);

    @Select("SELECT * FROM user_info WHERE username = #{username}")
    UserInfo findByUserName(@Param("username") String userName);

    @Select("SELECT * FROM user_info")
    List<UserInfo> selectAll();
}
