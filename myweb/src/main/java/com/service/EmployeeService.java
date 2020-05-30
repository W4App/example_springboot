package com.service;

import com.bestvike.linq.Linq;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mapper.EmployeeMapper;
import com.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 17:23
 */
@Service
public class EmployeeService {
    @Autowired
    private final EmployeeMapper dao;

    public EmployeeService(EmployeeMapper dao) {
        this.dao = dao;
    }

    public boolean insert(EmployeeModel model) {
        return dao.insert(model) > 0;
    }

    public EmployeeModel select(int id) {
        return dao.select(id);
    }

    @Cacheable(value = "employee_all")
    public List<EmployeeModel> selectAll() {
        return dao.selectAll();
    }

    public boolean updateValue(EmployeeModel model) {
        return dao.updateValue(model) > 0;
    }

    public boolean delete(Integer id) {
        return dao.delete(id) > 0;
    }

    public String[] getEmployeeList() {
        List<EmployeeModel> models = dao.selectAll();
        List<String> stringList = Linq.of(models)
                .select(x -> x.getEmName()).toList();
        String[] res = new String[stringList.size()];
        stringList.toArray(res);
        return res;
    }

    public String[] findEmployeeByGxdw(String gxdw) {
        return dao.findEmployeeByGxdw(gxdw);
    }

    /**
     * 硬分页服务!
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<EmployeeModel> getEmployeePage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<EmployeeModel> list = dao.selectAll();
        return new PageInfo<>(list);
    }
}
