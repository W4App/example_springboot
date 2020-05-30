package com.controller;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.bestvike.linq.Linq;
import com.model.DeviceModel;
import com.model.ExcelDevice;
import com.service.DeviceService;
import com.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: W.B.An
 * @Date: 2020/5/16(星期六)
 * @Time: 17:03
 */
@Controller
public class UploadExcelController {
    @Autowired
    DeviceService deviceService;

    @RequestMapping(value = "/excelImportDevice", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, List<ExcelDevice>> uploadExcelDevice(@RequestParam("file") MultipartFile file) {
        ImportParams importParams = new ImportParams();
        importParams.setHeadRows(1);
        List<ExcelDevice> result =
                FileUtil.importExcel(file, 0, 1, ExcelDevice.class);
        Map<String, List<ExcelDevice>> msg = new HashMap<>();
        List<ExcelDevice> deviceList = Linq.of(result)
                .where(x -> x.getId() == null || x.getSbName() == null)
                .toList();
        if (!deviceList.isEmpty()) {
            msg.put("输入表格数据的序号或设备名称格式错误", deviceList);
            return msg;
        }
        List<ExcelDevice> wrongTipMsg = new ArrayList<>();
        List<ExcelDevice> rightTipMsg = new ArrayList<>();
        for (ExcelDevice e : result) {
            DeviceModel t = new DeviceModel();
            t.setZname(e.getzName());
            t.setSbName(e.getSbName());
            t.setSbType(e.getSbType());
            t.setXb(e.getXb());
            t.setTc(e.getTc());
            t.setJxCondiction(e.getJxCondition());
            t.setGxdw(e.getGxdw());
            if (!deviceService.insert(t)) {
                wrongTipMsg.add(e);
                msg.put("相同站名重复的设备名称", wrongTipMsg);
            } else {
                rightTipMsg.add(e);
                msg.put("成功插入的数据", rightTipMsg);
            }
        }
        return msg;
    }
}
