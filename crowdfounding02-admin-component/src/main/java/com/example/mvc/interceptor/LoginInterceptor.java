package com.example.mvc.interceptor;

import com.example.Constant.CrowdConstant;
import com.example.Exception.AccessForbiddenException;
import com.example.entity.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       HttpSession session= request.getSession();

       Admin admin = (Admin)session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);

       if(admin==null)
       {
      throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
       }
       return true;
    }
}
