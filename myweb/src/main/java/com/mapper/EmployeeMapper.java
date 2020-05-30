package com.mapper;

import com.model.EmployeeModel;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 17:17
 * private Long id;
 * private String emName;
 * private String zw;
 * private String dw;
 */
@Mapper
@Component
public interface EmployeeMapper {
    @CacheEvict(value = "employee_all", allEntries = true)
    @Insert("INSERT INTO employee (emname,zw,dw) VALUES  (#{emName}, #{zw},#{dw})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(EmployeeModel model);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Cacheable(value = "other", key = "'employee'+#id")
    @Select("SELECT * FROM employee WHERE id=#{id}")
    EmployeeModel select(int id);

    /**
     * 查询全部
     *
     * @return
     */
    //  @Cacheable(value = "employee_all") 因为和分页冲突. springboot 也不推荐把缓存管理加在mapper上
    @Select("SELECT * FROM employee")
    List<EmployeeModel> selectAll();

    /**
     * 更新 value
     *
     * @param model
     * @return
     */
    @CacheEvict(value = "employee_all", allEntries = true)
    @Update("UPDATE employee SET emname=#{emName},zw=#{zw},dw=#{dw} WHERE id=#{id}")
    int updateValue(EmployeeModel model);

    /**
     * 根据 ID 删除
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "employee_all", allEntries = true)
    @Delete("DELETE FROM employee WHERE id=#{id}")
    int delete(Integer id);

    /**
     * 返回雇员列表
     *
     * @return
     */
    @Select("SELECT emname FROM employee")
    String[] getEmployeeList();

    /**
     * 根据所属单位名, 找出一群职工...
     */
    @Select("SELECT emname FROM employee WHERE dw=#{dw}")
    String[] findEmployeeByGxdw(@Param("dw") String gxdw);
}
