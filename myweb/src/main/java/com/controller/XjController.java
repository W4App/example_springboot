package com.controller;

import com.bestvike.linq.Linq;
import com.config.AppConfig;
import com.github.pagehelper.PageInfo;
import com.model.RepairModel;
import com.model.WtModel;
import com.service.DeviceService;
import com.service.EmployeeService;
import com.service.RepairService;
import com.service.WtService;
import com.util.MyPageInfo;
import com.util.MyUtil;
import com.viewmodel.ViewXj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: W.B.An
 * @Date: 2020/4/6(星期一)
 * @Time: 20:27
 */
@Controller
public class XjController {
    @Autowired
    RepairService repairService;
    @Autowired
    WtService wtService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    MyUtil myUtil;
    @Autowired
    AppConfig appConfig;
    String cookieName = "请选择";
    int currentPage = 0;
    int limit = 2;

    /**
     * 销记录入表单驱动部分(读出)
     *
     * @param viewXj
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/tt", method = RequestMethod.GET)
    public String myTest(@ModelAttribute("xjModel") ViewXj viewXj,
                         HttpServletResponse response,
                         HttpServletRequest request) {
        System.out.println("Cookie : " + request.getHeader("Cookie"));
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (!c.getName().equals("zn")) {
                    Cookie cookie = new Cookie("zn", cookieName);
                    cookie.setMaxAge(1000 * 60 * 60 * 24 * 2);
                    response.addCookie(cookie);
                }
            }
        }
        viewXj.setJx_date(LocalDate.now());
        viewXj.setAction("tt");
        viewXj.setZnNames(appConfig.getZ_NAME());
        String[] jxr = myUtil.getEmployeeList(cookieName);
        viewXj.setJxrs(jxr);
        viewXj.setZnCookie(cookieName);
        return "xj";
    }

    /**
     * 录入验证并写入
     *
     * @param viewXj
     * @param bindingResult
     * @param done
     * @param response
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/tt", method = RequestMethod.POST)
    public String PostXj(@ModelAttribute("xjModel") @Valid ViewXj viewXj,
                         BindingResult bindingResult,
                         @RequestParam(value = "done") String done,
                         HttpServletResponse response,
                         HttpServletRequest request) throws UnsupportedEncodingException {
        //验证检查
        if (bindingResult.hasFieldErrors()) {
            viewXj.setAction("tt");
            viewXj.setJx_date(LocalDate.now());
            viewXj.setZnNames(appConfig.getZ_NAME());
            String[] jxr = myUtil.getEmployeeList(cookieName);
            viewXj.setJxrs(jxr);
            viewXj.setZnCookie(cookieName);
            return "xj";
        } else {
            cookieName = viewXj.getZn();
            //记录维修记录
            RepairModel repairModel = new RepairModel();
            Long sbId = deviceService.findIdByZnameSbname(viewXj.getZn(), viewXj.getSb_name());
            repairModel.setSbId(sbId);
            repairModel.setJxContent(viewXj.getJx_content());
            repairModel.setJxDate(viewXj.getJx_date());
            repairModel.setJxRen(viewXj.getJx_name());
            repairModel.setJxType(viewXj.getJx_type());
            repairModel.setXj(viewXj.isXj());
            repairModel.setZn(viewXj.getZn());
            repairService.insert(repairModel);
            //记录cookie,下次重启填充站名
            Cookie cookie = new Cookie("zn", cookieName);
            cookie.setMaxAge(1000 * 60 * 60 * 24 * 2);
            response.addCookie(cookie);
            //记录问题记录
            String wtStr = viewXj.getNojx_content().trim();
            if (!wtStr.equals("")) {
                String[] wts = wtStr.split("@");
                for (String s : wts) {
                    WtModel wtModel = new WtModel();
                    wtModel.setSbId(deviceService.findIdByZnameSbname(viewXj.getZn(), viewXj.getSb_name()));
                    wtModel.setWtDate(viewXj.getJx_date());
                    wtModel.setWtScript(s);
                    wtModel.setXj(false);
                    wtService.insert(wtModel);
                }
            }
            if (done.equals("继续录入")) {
                return "redirect:/tt";
            } else {
                String z = URLEncoder.encode(viewXj.getZn(), "utf-8");
                String jr = URLEncoder.encode("今日", "utf-8");
                return "redirect:xjList" + "?" + "zn=" + z + "&query=" + jr;
            }
        }
    }

    /**
     * @param zn       站名
     * @param query    周期
     * @param task     任务类型
     * @param flag     销记标识
     * @param page     未实现
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/xjList", method = RequestMethod.GET)
    public String xjList(@RequestParam(value = "zn", defaultValue = "-:-") String zn,
                         @RequestParam(value = "query", defaultValue = "-:-") String query,
                         @RequestParam(value = "task", defaultValue = "-:-") String task,
                         @RequestParam(value = "flag", defaultValue = "-:-") String flag,
                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                         ModelMap modelMap) {
        List<RepairModel> rps;
        List<RepairModel> repairModels;
        if (zn.equals("-:-")) {
            rps = repairService.selectAllByYear();
            rps = Linq.of(rps).orderBy(x -> x.getZn()).toList();
            //task类型过滤
            rps = myUtil.jxTypeFilter(rps, task);
        } else {
            rps = repairService.selectByZname(zn);
            //task类型过滤
            rps = myUtil.jxTypeFilter(rps, task);
        }
        //周期过滤
        rps = myUtil.zqFilter(rps, query);
        //完成情况过滤
        switch (flag) {
            case "完成":
                rps = Linq.of(rps).where(x -> x.isXj()).toList();
                break;
            case "未完成":
                rps = Linq.of(rps).where(x -> !x.isXj()).toList();
                break;
            default:
                break;
        }
        //  这里进行数组分页--> 自己写逻辑实现!
        PageInfo<RepairModel> pageInfo = MyPageInfo.getMyPage(rps, pageNum, pageSize);
        repairModels = pageInfo.getList();
        List<ViewXj> viewXjs = new ArrayList<>();
        for (RepairModel r : repairModels) {
            Long id = r.getId();
            Long sbId = r.getSbId();
            String jxcontent = r.getJxContent();
            LocalDate jxDate = r.getJxDate();
            String jxRen = r.getJxRen();
            String jxType = r.getJxType();
            Boolean xj = r.isXj();
            String[] znSB = myUtil.getZnameSbname(sbId);
            ViewXj v = new ViewXj();
            v.setSb_name(znSB[1]); //设备名
            v.setZn(znSB[0]);      //站名
            v.setJx_name(jxRen);    //检修人
            v.setJx_date(jxDate);    //检修日期
            v.setJx_content(jxcontent); //检修内容
            v.setJx_type(jxType);  //检修类型
            v.setXj(xj);            //销记
            v.setXj_id(id);          //id
            v.setSbId(sbId);         //设备id
            viewXjs.add(v);           //插入集合
        }
        if (viewXjs.isEmpty()) {
            ViewXj v = new ViewXj();
            viewXjs.add(v);
            viewXjs.get(0).setJx_content("无数据");
        }
        //回显控制
        viewXjs.get(0).setZnNames(appConfig.getZ_NAME());
        modelMap.addAttribute("repairList", viewXjs);
        modelMap.addAttribute("zhanName", zn);
        modelMap.addAttribute("jxType", task);
        modelMap.addAttribute("zq", query);
        modelMap.addAttribute("flag", flag);
        modelMap.addAttribute("pageInfo", pageInfo);
        return "xjList";
    }
}
