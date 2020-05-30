package com.service;

import com.bestvike.linq.Linq;
import com.mapper.WtMapper;
import com.model.WtModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 15:37
 */
@Service
public class WtService {
    @Autowired
    private final WtMapper dao;

    public WtService(WtMapper dao) {
        this.dao = dao;
    }

    public boolean insert(WtModel model) {
        return dao.insert(model) > 0;
    }

    public WtModel select(Long id) {
        return dao.select(id);
    }

    public List<WtModel> selectAll() {
        return dao.selectAll();
    }

    public boolean updateValue(WtModel model) {
        return dao.updateValue(model) > 0;
    }

    public boolean delete(Long id) {
        return dao.delete(id) > 0;
    }

    public List<WtModel> findAllbySbIdNoxj(Long sbId) {
        List<WtModel> wtModels = dao.selectAll();
        return Linq.of(wtModels)
                .where(x -> x.getSbId().equals(sbId) && !x.getXj())
                .toList();
    }

    public boolean updateXj(WtModel wtModel) {
        return dao.updateXj(wtModel) > 0;
    }

    public List<Long> findWtIdsBySbId(Long sbId) {
        List<WtModel> wtModels = dao.selectAll();
        return Linq.of(wtModels)
                .where(x -> x.getSbId().equals(sbId))
                .select(y -> y.getId())
                .toList();
    }
}
