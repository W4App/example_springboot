package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author: W.B.An
 * @Date: 2020/5/25(星期一)
 * @Time: 0:16
 */
@Controller
public class ErrorController {
    @RequestMapping(value = "/tips", method = RequestMethod.GET)
    public String err() {
        return "tips";
    }
}
