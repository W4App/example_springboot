package com.controller;

import com.config.AppConfig;
import com.github.pagehelper.PageInfo;
import com.model.EmployeeModel;
import com.service.EmployeeService;
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
 * @Time: 18:09
 */
@Controller
public class EmployeeController {
    @Autowired
    AppConfig appConfig;
    @Autowired
    CacheManager cacheManager;
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize
    ) {
        PageInfo<EmployeeModel> employeeModelPageInfo = employeeService.getEmployeePage(pageNum, pageSize);
        model.addAttribute("employee", employeeModelPageInfo);
        return "employee";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.GET)
    public String createBookForm(ModelMap map) {
        map.addAttribute("employeeItem", new EmployeeModel());
        map.addAttribute("action", "addEmployee");
        map.addAttribute("gxdw", appConfig.getU_NAME());
        return "employeeForm";
    }

    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)
    public String postBook(@ModelAttribute EmployeeModel employeeModel) {
        employeeService.insert(employeeModel);
        cacheManager.getCache("other").clear();
        return "redirect:/employee";
    }

    @RequestMapping(value = "/employeeEdit/{id}", method = RequestMethod.GET)
    public String editDemo(@PathVariable Integer id, ModelMap map) {
        EmployeeModel employeeModel = employeeService.select(id);
        map.addAttribute("employeeItem", employeeModel);
        map.addAttribute("action", "employeeEdit");
        map.addAttribute("gxdw", appConfig.getU_NAME());
        return "employeeForm";
    }

    @RequestMapping(value = "/employeeEdit", method = RequestMethod.POST)
    public String editUpdate(@ModelAttribute EmployeeModel employeeModel) {
        employeeService.updateValue(employeeModel);
        cacheManager.getCache("employee_all").clear();
        cacheManager.getCache("other").putIfAbsent("'employee'+#employeeModel.id", employeeModel);
        return "redirect:/employee";
    }

    @RolesAllowed(value = "ROLE_admin")
    @RequestMapping(value = "/employeeDelete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        employeeService.delete(id);
        return "redirect:/employee";
    }
}
