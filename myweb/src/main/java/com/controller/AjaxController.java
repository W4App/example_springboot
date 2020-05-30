package com.controller;

import com.model.DeviceModel;
import com.model.WtModel;
import com.service.DeviceService;
import com.service.WtService;
import com.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/4/25(星期六)
 * @Time: 9:54
 * ajax异步获取控制!
 */
@Controller
public class AjaxController {
    @Autowired
    DeviceService deviceService;
    @Autowired
    WtService wtService;
    @Autowired
    MyUtil myUtil;

    @RequestMapping(value = "/getSbName", method = RequestMethod.POST)
    @ResponseBody
    /**
     * 根据站名查找设备名, 作为XJ.html的设备输入框内容自动完成功能
     */
    public ArrayList<String> getSbName(@RequestParam(value = "znName") String znName) {
        ArrayList<String> resultArr = new ArrayList<>();
        if (znName.equals("请选择")) {
            resultArr.add(" 未获得设备列表");
        } else {
            String[] tmp = deviceService.findSbNameByZName(znName);
            if (tmp.length == 0) {
                resultArr.add(znName + "  站未获得设备列表");
            }
            for (String s : tmp) {
                resultArr.add(s);
            }
        }
        return resultArr;
    }

    /**
     * 根据站名查找出相应的工区, 找出职工信息
     *
     * @param zName
     * @return
     */
    @RequestMapping(value = "/getJxrs", method = RequestMethod.POST)
    @ResponseBody
    public String[] getJxrs(@RequestParam("zName") String zName) {
        return myUtil.getEmployeeList(zName);
    }

    /**
     * xj.html界面显示需要销记的问题记录.
     * 加载thymeleaf 的fragment
     *
     * @param name
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/wt", method = RequestMethod.POST)
    public String getWt(@RequestParam("name") String name, ModelMap modelMap) {
        //这里需要多项查询?性能咋办呢?
        //1.根据站名和设备名称查找设备ID,
        //2.根据设备ID找出一系列的wt项...
        String[] tmp = name.split("\\|");
        Long deviceId = deviceService.findIdByZnameSbname(tmp[1], tmp[0]);
        List<WtModel> wtModels = wtService.findAllbySbIdNoxj(deviceId);
        if (wtModels.isEmpty()) {
            WtModel wtModel = new WtModel();
            if (deviceId == null) {
                wtModel.setWtScript("该设备不存在");
            } else {
                wtModel.setWtScript("无数据");
            }
            wtModels.add(wtModel);
        }
        modelMap.addAttribute("wts", wtModels);
        return "wtFragment::wtsfragmet";
    }

    /**
     * xjList.html里面使用, 点击设备名传入设备ID. 动态加载设备问题
     *
     * @param sbId
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/wtQuery", method = RequestMethod.GET)
    public String getWtQuery(@RequestParam(value = "sbId") Long sbId, ModelMap modelMap) {
        DeviceModel deviceModel = deviceService.select(sbId);
        List<WtModel> wtModels = wtService.findAllbySbIdNoxj(sbId);
        if (wtModels.isEmpty()) {
            WtModel wtModel = new WtModel();
            wtModel.setWtScript("无数据");
            wtModels.add(wtModel);
        }
        modelMap.addAttribute("wts", wtModels);
        modelMap.addAttribute("zn", deviceModel.getZname());
        modelMap.addAttribute("sbname", deviceModel.getSbName());
        return "wtQueryFlagment::wtQuery";
    }

    /**
     * ajax 方式在录入界面中销记问题表 传入问题Id的数组,字符串处理=>xj.html
     * 这是异步写操作!
     *
     * @param wtIds :前端传来的特定字符串
     */
    @RequestMapping(value = "/wtxj", method = RequestMethod.POST)
    @ResponseBody
    public void wtUpdate(@RequestParam("wtIds") String wtIds) {
        //[48,49,50]
        String t = wtIds.substring(1, wtIds.length() - 1);
        if (t.isEmpty()) {
            return;
        }
        String[] ids = t.split("\\,");
        WtModel wtModel;
        for (String s : ids) {
            try {
                Long id = Long.parseLong(s);
                wtModel = wtService.select(id);
                wtModel.setXj(true);
                wtModel.setXjDate(LocalDate.now());
                wtService.updateValue(wtModel);
            } catch (Exception e) {
            }
        }
    }
}
