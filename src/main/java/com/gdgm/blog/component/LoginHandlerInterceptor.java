package com.gdgm.blog.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        Object member = httpServletRequest.getSession().getAttribute("member");
        // 如果获取的request的session中memberr参数为空（未登录），就返回登录页，否则放行访问
        if (member == null) {
            httpServletRequest.getRequestDispatcher("/toLogin").forward(httpServletRequest, httpServletResponse);
            return false;
        } else {
            //获取所有的路径
            Set<String> set = (Set<String>)httpServletRequest.getSession().getServletContext().getAttribute("AllPermission");
           //获取请求路径,不在范围内就放行
            String servletPath = httpServletRequest.getServletPath();
            if(set.contains(servletPath)){
                HttpSession session = httpServletRequest.getSession();
                Set<String> url= (Set<String>)session.getAttribute("url");
                if(url.contains(servletPath)){
                    return true;
                }else{
                    httpServletRequest.getRequestDispatcher("/toLogin").forward(httpServletRequest, httpServletResponse);
                    return false;
                }
            }else{
                //不在范围内放行
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
