package com.controller;

import com.config.AppConfig;
import com.model.DeviceModel;
import com.service.DeviceService;
import com.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/5/13(星期三)
 * @Time: 21:38
 */
@Controller
public class MoreQueryController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    MyUtil myUtil;
    @Autowired
    AppConfig appConfig;

    @RequestMapping(value = "/advQuery", method = RequestMethod.GET)
    public String xjList(@RequestParam(value = "zn", defaultValue = "-:-") String zn,
                         @RequestParam(value = "query", defaultValue = "-:-") String query,
                         @RequestParam(value = "task", defaultValue = "-:-") String task,
                         ModelMap modelMap) {
        //zn  站名
        //query  周期
        // task  检修类型
        List<DeviceModel> deviceModelList = myUtil.getNoRepairDevice(zn, query, task);
        if (deviceModelList.isEmpty()) {
            DeviceModel deviceModel = new DeviceModel();
            deviceModel.setZname("无数据");
            deviceModelList.add(deviceModel);
        }
        //回显控制
        modelMap.addAttribute("zn", appConfig.getZ_NAME());
        modelMap.addAttribute("deviceList", deviceModelList);
        modelMap.addAttribute("zhanName", zn);
        modelMap.addAttribute("jxType", task);
        modelMap.addAttribute("zq", query);
        return "advQuery";
    }
}
