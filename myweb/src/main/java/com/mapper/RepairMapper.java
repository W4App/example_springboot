package com.mapper;

import com.model.RepairModel;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 8:01
 */
@Mapper
@Component
public interface RepairMapper {
    @CacheEvict(value = "rps_all", allEntries = true)
    @Insert("INSERT INTO repair (sbid,jxcontent,jxdate,jxren,jxtype,xj,zn) " +
            "VALUES (#{sbId}, #{jxContent},#{jxDate},#{jxRen},#{jxType},#{xj},#{zn})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(RepairModel model);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Cacheable(value = "other", key = "'repair'+#id")
    @Select("SELECT * FROM repair WHERE id=#{id}")
    RepairModel select(int id);

    /**
     * 查询全部
     *
     * @return
     */
    @Cacheable(value = "rps_all")
    @Select("SELECT * FROM repair")
    List<RepairModel> selectAll();

    /**
     * 更新 value
     *
     * @param model
     * @return
     */
    @CacheEvict(value = "rps_all", allEntries = true)
    @Update("UPDATE repair " +
            "SET sbid=#{sbId}, jxcontent=#{jxContent},jxdate=#{jxDate},jxren=#{jxRen},jxtype=#{jxType},xj=#{xj},zn=#{zn}" +
            "WHERE id=#{id}")
    int updateValue(RepairModel model);

    /**
     * 根据 ID 删除
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "rps_all", allEntries = true)
    @Delete("DELETE FROM repair WHERE id=#{id}")
    int delete(Long id);

    /**
     * 根据站名查找维修条目
     *
     * @return
     */
    @Select("SELECT * FROM repair WHERE zn=#{zn}")
    List<RepairModel> selectByZname(@Param("zn") String zn);

    @Select("SELECT COUNT(*) FROM repair")
    int getRepairCount();

    @Select("SELECT * FROM repair order by 'PRIMARY' LIMIT #{current},#{page}")
    List<RepairModel> getRepairPage(@Param("current") int current, @Param("page") int page);

    @Select("SELECT * FROM repair where year(jxdate)=year(now())")
    List<RepairModel> selectAllByYear();
}

