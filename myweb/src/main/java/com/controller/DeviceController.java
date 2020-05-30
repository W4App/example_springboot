package com.controller;

import com.config.AppConfig;
import com.github.pagehelper.PageInfo;
import com.model.DeviceModel;
import com.service.DeviceService;
import com.service.RepairService;
import com.service.WtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

/**
 * @Author: W.B.An
 * @Date: 2020/5/1(星期五)
 * @Time: 18:10
 */
@Controller
public class DeviceController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    RepairService repairService;
    @Autowired
    WtService wtService;
    @Autowired
    private DeviceService deviceService;

    @RequestMapping(value = "/devices", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        PageInfo<DeviceModel> deviceModelPageInfo = deviceService.getDevicePage(pageNum, pageSize);
        model.addAttribute("devices", deviceModelPageInfo);
        return "devices";
    }

    @RequestMapping(value = "/addDevice", method = RequestMethod.GET)
    public String createBookForm(ModelMap map) {
        map.addAttribute("gxdw", appConfig.getU_NAME());
        map.addAttribute("zhanName", appConfig.getZ_NAME());
        map.addAttribute("DeviceItem", new DeviceModel());
        map.addAttribute("action", "addDevice");
        return "deviceForm";
    }

    @RequestMapping(value = "/addDevice", method = RequestMethod.POST)
    public String postBook(@ModelAttribute DeviceModel deviceModel) {
        deviceService.insert(deviceModel);
        return "redirect:/devices";
    }

    @RequestMapping(value = "/deviceEdit/{id}", method = RequestMethod.GET)
    public String editDemo(@PathVariable Long id, ModelMap map) {
        DeviceModel deviceModel = deviceService.select(id);
        map.addAttribute("gxdw", appConfig.getU_NAME());
        map.addAttribute("zhanName", appConfig.getZ_NAME());
        map.addAttribute("DeviceItem", deviceModel);
        map.addAttribute("action", "deviceEdit");
        return "deviceForm";
    }

    @RequestMapping(value = "/deviceEdit", method = RequestMethod.POST)
    public String editUpdate(@ModelAttribute DeviceModel deviceModel) {
        System.out.println(deviceModel.toString());
        deviceService.updateValue(deviceModel);
        return "redirect:/devices";
    }

    @RolesAllowed(value = "ROLE_admin")
    @RequestMapping(value = "/deviceDelete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Long id) {
        deviceService.delete(id);
        //同步删除含有该设备的检修和问题记录
        return "redirect:/devices";
    }
}
