package com.util;

import com.bestvike.linq.IEnumerable;
import com.bestvike.linq.Linq;
import com.model.DeviceModel;
import com.model.EmployeeModel;
import com.model.RepairModel;
import com.service.DeviceService;
import com.service.EmployeeService;
import com.service.RepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/4(星期一)
 * @Time: 18:09
 */
@Component
public class MyUtil {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    RepairService repairService;

    public String[] getEmployeeList(String zName) {
        //根据 站名 在设备列表中查找管辖单位 *这个单一数据, 用SQL
        //根据管辖单位, 查找单位职工 这个固定列表一次性读入吧.
        //形成优先列表...
        if (zName.equals("请选择")) {
            return employeeService.getEmployeeList();
        }
        String gxdw = deviceService.findGxdwByZname(zName);
        ArrayList<String> result = new ArrayList<>();
        List<EmployeeModel> employeeModels = employeeService.selectAll();
        List arr1 = Linq.of(employeeModels)
                .where(x -> x.getDw().equals(gxdw))
                .select(y -> y.getEmName())
                .toList();
        List arr2 = Linq.of(employeeModels)
                .where(x -> x.getDw().contains("检查整治"))
                .select(y -> y.getEmName())
                .toList();
        List arr3 = Linq.of(employeeModels)
                .where(x -> x.getDw().contains("车间"))
                .select(y -> y.getEmName())
                .toList();
        List arr4 = Linq.of(employeeModels)
                .where(x -> (!x.getDw().contains("车间") && !x.getDw().contains("检查整治") && !x.getDw().equals(gxdw)))
                .select(y -> y.getEmName())
                .toList();
        result.addAll(arr1);
        result.addAll(arr2);
        result.addAll(arr3);
        result.addAll(arr4);
        return result.toArray(new String[result.size()]);
    }

    //根据设备ID 得到站名和设备名称
    public String[] getZnameSbname(Long sbId) {
        String[] result = new String[2];
        if (sbId == null) {
            return result;
        }
        List<DeviceModel> deviceModels = deviceService.selectAll();
        List<String> zns = Linq.of(deviceModels)
                .where(x -> x.getId()
                        .equals(sbId))
                .select(y -> y.getZname())
                .toList();
        List<String> sbName = Linq.of(deviceModels)
                .where(x -> x.getId().equals(sbId))
                .select(y -> y.getSbName())
                .toList();
        if (zns.isEmpty() && sbName.isEmpty()) {
            System.out.println(sbId);
            return null;
        } else {
            result[0] = zns.get(0);
            result[1] = sbName.get(0);
            return result;
        }
    }

    //查询指定站名,指定周期情况下,返回未检修的设备....
    public List<DeviceModel> getNoRepairDevice(String zname, String zq, String jxType) {
        String[] sbNames = deviceService.findSbNameByZName(zname);
        List<RepairModel> rps = Linq.of(repairService.selectAllByYear())
                .where(x -> x.getZn().equals(zname))
                .toList();
        //按检修类型过滤
        rps = jxTypeFilter(rps, jxType);
        //按周期过滤
        rps = zqFilter(rps, zq);
        List<Long> res = Linq.of(rps)
                .select(x -> x.getSbId())
                .distinct()
                .toList();
        String[] resultSbName = new String[res.size()];
        for (int i = 0; i < res.size(); i++) {
            resultSbName[i] = deviceService.findSbNameById(res.get(i));
        }
        IEnumerable<String> strings = Linq.of(sbNames).except(Linq.of(resultSbName));
        List<DeviceModel> noRepairDevices = new ArrayList<>();
        for (String s : strings.toArray(String.class)) {
            Long id = deviceService.findIdByZnameSbname(zname, s);
            noRepairDevices.add(deviceService.select(id));
        }
        //排序
        return Linq.of(noRepairDevices)
                .orderBy(x -> x.getSbType()).thenBy(y -> y.getXb())
                .toList();
    }

    /**
     * 按检修类型来过滤检修集合
     *
     * @param rps    ->需要过滤的集合
     * @param jxType ->检修类型
     * @return 过滤后的集合
     */
    public List<RepairModel> jxTypeFilter(List<RepairModel> rps, String jxType) {
        switch (jxType) {
            case "年表任务":
                rps = Linq.of(rps)
                        .where(x -> x.getJxType().equals("年表任务"))
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                break;
            case "月表任务":
                rps = Linq.of(rps)
                        .where(x -> x.getJxType().equals("月表任务"))
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                break;
            case "周计划":
                rps = Linq.of(rps)
                        .where(x -> x.getJxType().equals("周计划"))
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                break;
            case "其他整治":
                rps = Linq.of(rps)
                        .where(x -> x.getJxType().equals("其他整治"))
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                break;
            default:
                break;
        }
        return rps;
    }

    /**
     * 检修周期过滤
     *
     * @param rps 检修集合
     * @param zq  周期
     * @return 过滤集合
     */
    public List<RepairModel> zqFilter(List<RepairModel> rps, String zq) {
        //周期过滤
        LocalDate n = LocalDate.now();
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
        int weekCount = n.get(weekFields.weekOfWeekBasedYear());
        switch (zq) {
            case "今日": //显示当日录入的的检修记录 按日期倒排
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .getDayOfYear() == n.getDayOfYear())
                        .toList();
                break;
            case "本周": //显示本周的录入检修记录  按日期倒排
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .get(weekFields.weekOfWeekBasedYear()) == weekCount)
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                break;
            case "本月":  //显示本月录入记录. 日期倒排
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .getMonthValue() == n.getMonthValue())
                        .toList();
                break;
            case "本年": //显示年度录入记录
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .getYear() == n.getYear())
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                break;
            default:
                break;
        }
        return rps;
    }
}
