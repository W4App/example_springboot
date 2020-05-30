package com.mapper;

import com.model.WtModel;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 15:27
 * <p>
 * private Long id;
 * private Long sbId;
 * private LocalDate wtDate;
 * private String wtScript;
 * private LocalDate xjDate;
 * private Boolean xj;
 */
@Mapper
@Component
public interface WtMapper {
    @CacheEvict(value = "wt_all", allEntries = true)
    @Insert("INSERT INTO wt (sbid,wtdate,wtscript,xjdate,xj) " +
            "VALUES (#{sbId}, #{wtDate},#{wtScript},#{xjDate},#{xj})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(WtModel model);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Cacheable(value = "other", key = "'wts'+#id")
    @Select("SELECT * FROM wt WHERE id=#{id}")
    WtModel select(Long id);

    /**
     * 查询全部
     *
     * @return
     */
    @Cacheable(value = "wt_all")
    @Select("SELECT * FROM wt")
    List<WtModel> selectAll();

    /**
     * 更新 value
     *
     * @param model
     * @return
     */
    @CacheEvict(value = "wt_all", allEntries = true)
    @Update("UPDATE wt SET sbid=#{sbId},wtdate=#{wtDate},wtscript=#{wtScript},xjdate=#{xjDate},xj=#{xj} WHERE id=#{id}")
    int updateValue(WtModel model);

    /**
     * 根据 ID 删除
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "wt_all", allEntries = true)
    @Delete("DELETE FROM wt WHERE id=#{id}")
    Long delete(Long id);

    /**
     * 根据
     *
     * @param sbId
     * @return
     */
    @Select("SELECT * FROM wt WHERE sbid=#{sbId} AND xj=false")
    List<WtModel> findAllbySbIdNoxj(@Param("sbId") Long sbId);

    @Update("UPDATE wt SET xjdate=#{xjDate},xj=#{xj}) WHERE id=#{id}")
    int updateXj(WtModel model);
}
