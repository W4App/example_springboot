package com.service;

import com.bestvike.linq.Linq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.DeviceMapper;
import com.model.DeviceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 16:57
 */
@Service
public class DeviceService {
    @Autowired
    private final DeviceMapper dao;
    @Autowired
    RepairService repairService;
    @Autowired
    WtService wtService;

    public DeviceService(DeviceMapper dao) {
        this.dao = dao;
    }

    /**
     * 插入设备信息!
     * 设备表中同一个站名下,不能有重复的设备名称
     *
     * @param model
     * @return
     */
    public boolean insert(DeviceModel model) {
        List<DeviceModel> deviceModels =
                Linq.of(findDevicesByZname(model.getZname()))
                        .where(x -> x.getSbName().toUpperCase().equals(model.getSbName().toUpperCase()))
                        .toList();
        if (deviceModels.isEmpty()) {
            return dao.insert(model) > 0;
        } else {
            return false;
        }
    }

    public DeviceModel select(Long id) {
        return dao.select(id);
    }

    /**
     * 从接口层提到服务层,springboot 推荐方法. 避免aop出问题! pagehelper就是这样
     *
     * @return
     */
    @Cacheable(value = "device_all")
    public List<DeviceModel> selectAll() {
        return dao.selectAll();
    }

    public boolean updateValue(DeviceModel model) {
        return dao.updateValue(model) > 0;
    }

    public boolean delete(Long id) {
        //删除维修表中引用了设备id的项目
        List<Long> rpsIds = repairService.findRpsIDBySbId(id);
        for (Long rpsId : rpsIds) {
            repairService.delete(rpsId);
        }
        //同步删除问题记录中引用的设备
        List<Long> wtIds = wtService.findWtIdsBySbId(id);
        for (Long wtId : wtIds) {
            wtService.delete(wtId);
        }
        return dao.delete(id) > 0;
    }

    public String[] findSbNameByZName(String zname) {
        List<DeviceModel> deviceModelList = dao.selectAll();
        List<String> stringList = Linq.of(deviceModelList)
                .where(x -> x.getZname().equals(zname))
                .select(DeviceModel::getSbName)
                .toList();
        String[] tmp = new String[stringList.size()];
        stringList.toArray(tmp);
        return tmp;
    }

    public Long findIdByZnameSbname(String zname, String sbname) {
        List<DeviceModel> deviceModels = dao.selectAll();
        List<Long> list = Linq.of(deviceModels)
                .where(x -> x.getZname().equals(zname) && x.getSbName().equals(sbname))
                .select(DeviceModel::getId).toList();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public String findGxdwByZname(String zName) {
        List<DeviceModel> deviceModels = dao.selectAll();
        List<String> gxdw = Linq.of(deviceModels)
                .where(x -> x.getZname().equals(zName))
                .select(y -> y.getGxdw())
                .toList();
        if (gxdw.isEmpty()) {
            return null;
        } else {
            return gxdw.get(0);
        }
    }

    public String findSbNameById(Long id) {
        DeviceModel deviceModel = dao.select(id);
        return deviceModel.getSbName();
    }

    /**
     * 根据站名找到设备集合
     *
     * @param zName
     * @return
     */
    public List<DeviceModel> findDevicesByZname(String zName) {
        List<DeviceModel> dev = dao.selectAll();
        return Linq.of(dev)
                .where(x -> x.getZname().equals(zName)).toList();
    }

    /**
     * pagehelper插件. 和ehcache有冲突, 如果在mapper加上缓存, 只能得到第一页的数据,.startpage静态方法
     * 失效. 所以单独提供一个服务方法
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<DeviceModel> getDevicePage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DeviceModel> list = dao.selectAll();
        return new PageInfo<>(list);
    }
}

