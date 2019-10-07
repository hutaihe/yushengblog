package com.gdgm.blog.component;

import com.gdgm.blog.common.util.ApplicationContextUtils;
import com.gdgm.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
/**
 * 访问拦截器
 */
public class VisitHandlerInterceptor implements HandlerInterceptor {
    private static Map<String, HttpSession>  sessionMap = new HashMap<String, HttpSession>();

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String servletPath = httpServletRequest.getServletPath();
        String id = httpServletRequest.getParameter("id");
        String remortIP = getRemortIP(httpServletRequest);
        String visitArticle = remortIP+id.toString();
        //如果为空说明该ip第一次访问该文章
        if(sessionMap.get(visitArticle) == null) {
            ArticleService articleService = ApplicationContextUtils.getBean(ArticleService.class);
            Integer count = articleService.updateAccessById(Integer.valueOf(id));
            //将session存入sessionmap
            sessionMap.put(visitArticle, httpServletRequest.getSession());
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
