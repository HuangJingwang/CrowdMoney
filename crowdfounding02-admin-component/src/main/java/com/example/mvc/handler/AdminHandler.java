package com.example.mvc.handler;


import com.example.Constant.CrowdConstant;
import com.example.entity.Admin;
import com.example.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/update.html")
    public  String updateAdmin(Admin admin,@RequestParam("pageNum") Integer pageNum,@RequestParam("keyword") String keyword)
    {
        adminService.updateAdmin(admin);
        return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }
    @RequestMapping("/admin/to/edit/page.html")
    public  String toEditPage(
            @RequestParam("adminId") Integer adminId,
            ModelMap modelMap
    )
    {
       Admin admin= adminService.getAdminById(adminId);
       modelMap.addAttribute(admin);
        return "admin_edit";
    }
    @RequestMapping("/admin/to/login/page.html")
    public String doLogin(
            @RequestParam("loginAcct") String logAcct,
            @RequestParam("userPswd") String userPswd,
            HttpSession session
    )
    {
        Admin admin=adminService.getAdminByLoginAcct(logAcct,userPswd);
        //为避免写错，此处“LoginAdmin”用常量代替
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String logout(HttpSession session)
    {
        session.invalidate();
            return "redirect:/admin/do/login/page.html";
    }
    @RequestMapping("/admin/page.html")
    public String getAdminPage(
// 注意：页面上有可能不提供关键词，要进行适配
// 在@RequestParam注解中设置defaultValue属性为空字符串表示浏览器不提供关键词时，keyword 变量赋值为空字符串
            @RequestParam(value="keyword", defaultValue="") String keyword, // 浏览器未提供 pageNum 时，默认前往第一页
                    @RequestParam(value="pageNum", defaultValue="1") Integer pageNum, // 浏览器未提供 pageSize 时，默认每页显示 5 条记录
            @RequestParam(value="pageSize", defaultValue="5") Integer pageSize, ModelMap modelMap
    ) {
// 查询得到分页数据
        PageInfo<Admin> pageInfo = adminService.getAdminPage(keyword, pageNum, pageSize);
// 将分页数据存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin_page";
    }
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
public String remove(@PathVariable("adminId") Integer adminId,
                     @PathVariable("pageNum") Integer pageNum,
                     @PathVariable("keyword") String keyword)
    {
        adminService.deleteAdminById(adminId);

        return "redirect:/admin/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("admin/save.html")
    public String save(Admin admin)
    {
        adminService.saveAdmin(admin);

        return "redirect:/admin/page.html?pageNum="+Integer.MAX_VALUE;
    }
}
