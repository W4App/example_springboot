package com.controller;

import com.bestvike.linq.Linq;
import com.model.RepairExcel;
import com.model.RepairModel;
import com.model.WtModel;
import com.service.DeviceService;
import com.service.RepairService;
import com.service.WtService;
import com.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/11(星期一)
 * @Time: 13:42
 */
@Controller
public class ExcelExportController {
    @Autowired
    RepairService repairService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    WtService wtService;

    @RequestMapping(value = "/outExcel/{zq}", method = RequestMethod.GET)
    @ResponseBody
    public void export(HttpServletResponse response, @PathVariable String zq) {
        LocalDate n = LocalDate.now();
        List<RepairModel> rps = repairService.selectAllByYear();
        List<RepairExcel> rpxls = new ArrayList<>();
        switch (zq) {
            case "1": //年度
                rpxls = setExcelFmt(rps);
                break;
            case "2": //今日
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .getDayOfYear() == n.getDayOfYear())
                        .toList();
                rpxls = setExcelFmt(rps);
                break;
            case "3": //本周
                WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 4);
                int weekCount = n.get(weekFields.weekOfWeekBasedYear());
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .get(weekFields.weekOfWeekBasedYear()) == weekCount)
                        .orderByDescending(RepairModel::getJxDate)
                        .toList();
                rpxls = setExcelFmt(rps);
                break;
            case "4": //本月
                rps = Linq.of(rps)
                        .where(x -> x.getJxDate()
                                .getMonthValue() == n.getMonthValue())
                        .toList();
                rpxls = setExcelFmt(rps);
                break;
            case "5": //年度
                break;
        }
        FileUtil.exportExcel(rpxls, RepairExcel.class, "检修.xls", response);
    }

    private List<RepairExcel> setExcelFmt(List<RepairModel> rps) {
        List<RepairExcel> eXls = new ArrayList<RepairExcel>();
        for (RepairModel rp : rps) {
            Long id = rp.getId();
            String zName = rp.getZn(); //站名
            String sbName = deviceService.findSbNameById(rp.getSbId());//设备名
            LocalDate xjDate = rp.getJxDate(); //检修日期
            String jxRen = rp.getJxRen(); //检修人
            String jxContent = rp.getJxContent();
            String jxType = rp.getJxType(); //检修类型
            String xj = rp.isXj() ? "已销记" : "未销记"; //是否销记
            // --- 根据sbid 找到wt集合
            String wtcontent = "";
            List<WtModel> wtModels = wtService.findAllbySbIdNoxj(rp.getSbId());
            for (WtModel w : wtModels) {
                wtcontent = wtcontent + w.getWtScript() + ",";
            }
            //写入
            RepairExcel x = new RepairExcel();
            x.setId(id);
            x.setzName(zName);
            x.setSbName(sbName);
            x.setXjDate(xjDate);
            x.setJxRen(jxRen);
            x.setJxContent(jxContent);
            x.setJxType(jxType);
            x.setXj(xj);
            x.setWts(wtcontent);
            eXls.add(x);
        }
        return eXls;
    }
}
