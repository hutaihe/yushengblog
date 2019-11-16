$("#checkResiter").click(function(){
    var floginacct = $("#floginacct");
    var fusername = $("#fusername");
    var femail = $("#femail");
    var fuserpswd = $("#fuserpswd");
    var fconfirmpassword = $("#fconfirmpassword");
    var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if(floginacct.val() == ""){
        layer.msg("请输入登录账号！", {time:1500, icon:5, shift:6});
        floginacct.focus();
        return false;
    }else{
        if(floginacct.val().length >18){
            layer.msg("登录账号过长，请重新输入！", {time:1500, icon:5, shift:6});
            floginacct.val("");
            floginacct.focus();
            return false;
        }
    }
    if(fusername.val() == ""){
        layer.msg("请输入昵称！", {time:1500, icon:5, shift:6});
        fusername.focus();
        return false;
    }
    if(femail.val() == ""){
        layer.msg("请输入邮箱！", {time:1500, icon:5, shift:6});
        femail.focus();
        return false;
    }else{
        if(!myreg.test(femail.val())){
            layer.msg("邮件格式不正确，请输入正确的邮箱！", {time:1500, icon:5, shift:6});
            femail.val("");
            femail.focus();
            return false;
        }
    }
    if(fuserpswd.val() == ""){
        layer.msg("请输入登录密码！", {time:1500, icon:5, shift:6});
        fuserpswd.focus();
        return false;
    }
    if(fconfirmpassword.val() == ""){
        layer.msg("请确认密码！", {time:1500, icon:5, shift:6});
        fconfirmpassword.focus();
        return false;
    }else{
        if(fuserpswd.val() != fconfirmpassword.val()){
            layer.msg("两次密码不一致，请重新确认密码！", {time:1500, icon:5, shift:6});
            fconfirmpassword.val("");
            fconfirmpassword.focus();
            return false;
        }
    }
    var loadingIndex = -1;
    var floginacct = $("#floginacct");
    var fusername = $("#fusername");
    var femail = $("#femail");
    var fuserpswd = $("#fuserpswd");

    $.ajax({
        type: "post",
        data : {
            "loginacct" : floginacct.val(),
            "username" : fusername.val(),
            "email" : femail.val(),
            "userpswd" : fuserpswd.val()
        },
        url :"/doRegister",
        beforeSend: function() {
            loadingIndex = layer.msg('正在注册', {icon: 16});
        },
        success : function(result){
            layer.close(loadingIndex);
            if(result.success){
                layer.msg("注册成功，3s后跳转到登录界面！", {time:1500, icon:6});
                setTimeout(function(){
                    window.location.href="/toLogin"
                },3000)
            }else{
                layer.msg(result.message, {time:1500, icon:5, shift:6});
            }
        }

    });
});