package com.mapper;

import com.model.DeviceModel;
import org.apache.ibatis.annotations.*;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 16:49
 * private  Long id;
 * private String zname;
 * private  String sbName;
 * private  String sbType;
 * private String xb;
 * private String tc;
 */
@Mapper
@Component
public interface DeviceMapper {
    @CacheEvict(value = "device_all", allEntries = true)
    @Insert("INSERT INTO device (zname,gxdw,sbname,sbtype,xb,tc,jxCondiction)" +
            "VALUES (#{zname},#{gxdw}, #{sbName},#{sbType},#{xb},#{tc},#{jxCondiction})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(DeviceModel model);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Cacheable(value = "other", key = "'device'+#id")
    @Select("SELECT * FROM device WHERE id=#{id}")
    DeviceModel select(Long id);

    /**
     * 查询全部
     *
     * @return
     */
    //  @Cacheable(value = "device_all")
    @Select("SELECT * FROM device")
    List<DeviceModel> selectAll();

    /**
     * 更新 value
     *
     * @param model
     * @return
     */
    @CacheEvict(value = "device_all", allEntries = true)
    @Update("UPDATE device SET zname=#{zname},gxdw=#{gxdw}, sbname=#{sbName},sbtype=#{sbType},xb=#{xb},tc=#{tc},jxCondiction=#{jxCondiction} WHERE id=#{id}")
    int updateValue(DeviceModel model);

    /**
     * 根据 ID 删除
     *
     * @param id
     * @return
     */
    @CacheEvict(value = "device_all", allEntries = true)
    @Delete("DELETE FROM device WHERE id=#{id}")
    int delete(Long id);

    /**
     * 根据站名查找设备列表
     */
    @Select("SELECT sbname FROM device WHERE zname=#{zname}")
    String[] findSbByZName(@Param("zname") String zname);

    /**
     * 根据站名和设备名查找 设备id 站名和设备名联合唯一标识某个设备....
     */
    @Select("SELECT id FROM device WHERE zname=#{zname} AND sbname=#{sbName}")
    Long findIdByZnameSbname(@Param("zname") String zname, @Param("sbName") String sbName);

    /**
     * 根据站名返回管辖单位
     *
     * @return
     */
    @Select("SELECT gxdw FROM device where zname=#{zname} limit 0,1")
    String findGxdwByZname(@Param("zname") String zName);

    @Select("SELECT sbname FROM device WHERE id=#{id}")
    String findSbNameById(@Param("id") Long id);
}
