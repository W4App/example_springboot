package com.service;

import com.bestvike.linq.Linq;
import com.mapper.RepairMapper;
import com.model.RepairModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 14:58
 * 缓存之重中之重. 对象内部调用是不会触发缓存的...  所虽然不推荐, 但是也要移动到接口中了.... 不然还得包一层!!
 */
@Service
public class RepairService {
    private final RepairMapper dao;

    @Autowired
    public RepairService(RepairMapper dao) {
        this.dao = dao;
    }

    public boolean insert(RepairModel model) {
        return dao.insert(model) > 0;
    }

    public RepairModel select(int id) {
        return dao.select(id);
    }

    public List<RepairModel> selectAll() {
        return dao.selectAll();
    }

    public boolean updateValue(RepairModel model) {
        return dao.updateValue(model) > 0;
    }

    public boolean delete(Long id) {
        return dao.delete(id) > 0;
    }

    public List<RepairModel> selectByZname(String zName) {
        List<RepairModel> repairModels = dao.selectAll();
        return Linq.of(repairModels)
                .where(x -> x.getZn().equals(zName))
                .toList();
    }

    public int getRepairCount() {
        return dao.getRepairCount();
    }

    public List<RepairModel> getRepairPage(int current, int page) {
        return dao.getRepairPage(current, page);
    }

    public List<RepairModel> selectAllByYear() {
        //重写
        List<RepairModel> rps = dao.selectAll();
        List<RepairModel> res = Linq.of(rps)
                .where(x -> x.getJxDate().getYear() == (LocalDate.now()).getYear())
                .toList();
        return res;
    }

    /**
     * 根据设备ID查找维修条目ID的集合.
     *
     * @return
     */
    public List<Long> findRpsIDBySbId(Long sbId) {
        List<RepairModel> rps = dao.selectAll();
        return Linq.of(rps)
                .where(x -> x.getSbId().equals(sbId))
                .select(y -> y.getId())
                .toList();
    }
}
