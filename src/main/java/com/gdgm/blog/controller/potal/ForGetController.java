package com.gdgm.blog.controller.potal;

import com.gdgm.blog.bean.Member;
import com.gdgm.blog.bean.Role;
import com.gdgm.blog.common.util.AjaxResult;
import com.gdgm.blog.common.util.MD5Util;
import com.gdgm.blog.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller
public class ForGetController {
  @Autowired
  private JavaMailSenderImpl javaMailSender;

  @Autowired
  private MemberService memberService;

  @RequestMapping("/toForget")
  public String toForget() {
    return "forget";
  }

  //发新密码
  @RequestMapping("/doForget")
  @ResponseBody
  public Object doForget(String loginacct, String email, HttpSession session) {
    AjaxResult result = new AjaxResult();
    try {
      Member member = memberService.queryMemberByLoginacct(loginacct);
      if (member == null) {
        result.setSuccess(false);
        result.setMessage("当前账户不存在，请输入正确的账号！");
      } else {
        if (!member.getEmail().equals(email)) {
          result.setSuccess(false);
          result.setMessage("邮箱与当前账号绑定的不一致！");
        } else {
          //随机生成验证码
          Integer c = new Random().nextInt(9999);
          String code = c.toString();
          MimeMessage mimeMessage = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
          helper.setTo(member.getEmail());
          helper.setSubject("找回密码");
          helper.setFrom("1343314863@qq.com");
          helper.setText("IT博客提示你正在进行找回密码操作，如果是本人操作，请点击<a href='http://120.55.165.50:8080/doChangePasword?loginacct=" + loginacct + "&code=" + code + "'>修改密码</a>链接进行修改,</br>点击修改密码后，新密码将会以邮件形式发给你,该链接30分钟有效！", true);
          javaMailSender.send(mimeMessage);
          result.setSuccess(true);
          session.setAttribute("code",code);
        }
      }
    } catch (Exception e) {
      result.setSuccess(false);
      result.setMessage("发送邮件失败！");
      e.printStackTrace();
    }
    return result;
  }

  //修改密码
  @RequestMapping("/doChangePasword")
  public String doChangePasword(String loginacct, String code,HttpSession session) {
    String email = null;
    String c = (String)session.getAttribute("code");
    try{
        if(c != null && code.equals(c)){
          Member member = memberService.queryMemberByLoginacct(loginacct);
          if (member != null) {
              email = member.getEmail();
            //随机生成密码
              Integer password = new Random().nextInt(99999999);
              String userpswd = password.toString();
              memberService.updatePasswordByloginacct(loginacct,MD5Util.digest(userpswd));
              MimeMessage mimeMessage = javaMailSender.createMimeMessage();
              MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
              helper.setTo(member.getEmail());
              helper.setSubject("获取新密码");
              helper.setFrom("1343314863@qq.com");
              helper.setText("IT博客已经帮你修改了密码，请使用新密码进行登录："+userpswd, true);
              javaMailSender.send(mimeMessage);
          }
        }
    }catch (Exception e){
      e.printStackTrace();
      try{
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(email);
        helper.setSubject("获取新密码");
        helper.setFrom("1343314863@qq.com");
        helper.setText("系统异常，修改密码失败！");
        javaMailSender.send(mimeMessage);
      }catch (Exception b){
        b.printStackTrace();
      }

    }

  return "redirect:/toLogin";
  }
}

