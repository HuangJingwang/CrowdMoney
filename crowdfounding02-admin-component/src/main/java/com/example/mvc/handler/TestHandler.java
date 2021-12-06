package com.example.mvc.handler;


import com.example.entity.Admin;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSsm(Model model)
    {
        List<Admin> a = adminService.getAll();
        model.addAttribute("a",a);
        return "target";
    }
    @RequestMapping("/test/error.html")
    public @ResponseBody String testerror()
    {
        String a=null;
        a.length();//测试空指针的xml异常
        return "made";
    }
}
