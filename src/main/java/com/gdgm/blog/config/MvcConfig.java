package com.gdgm.blog.config;

import com.gdgm.blog.component.LoginHandlerInterceptor;
import com.gdgm.blog.component.VisitHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    //将拦截器组件注册在容器
    @Bean
    public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
            //注册拦截器
            public void addInterceptors(InterceptorRegistry registry) {
                //super.addInterceptors(registry);
                //登录拦截器
                registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**").
                        excludePathPatterns("/","/index","/toLogin","/doLogin","/toRegister",
                                "/doRegister","/toForget","/doForget","/doChangePasword",
                                "/test","/loadNav","/loadArticle","/category","/loadArticleByTypeid",
                                "/loadPopular","/loadLabel","/tag","/loadArticleByLabelId","/loadLike",
                        "/article","/articleContent","/author","/loadArticleByMemberid","/loadComment",
                        "/likeArticle","/fabulousArticle","/search","/doSearch","/message","/loadMessage",
                         "/submitMessage","/loadBrief");
                //访问拦截器
                registry.addInterceptor(new VisitHandlerInterceptor()).addPathPatterns("/article");
            }
        };
        return adapter;
    }
    //设置默认的View跳转页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
       registry.addViewController("/").setViewName("index");
        super.addViewControllers(registry);
    }
}
