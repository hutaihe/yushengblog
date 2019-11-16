$("#obtain").click(function(){
    var floginacct = $("#floginacct");
    var femail = $("#femail");
    var myreg = /^([\.a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
    if(floginacct.val() == ""){
        layer.msg("请输入登录账号！", {time:1000, icon:5, shift:6});
        floginacct.focus();
        return false;
    }
    if(femail.val() == ""){
        layer.msg("请输入邮箱！", {time:1000, icon:5, shift:6});
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
    $.ajax({
        type: "post",
        data : {
            "loginacct" : floginacct.val(),
            "email" : femail.val(),
        },
        url :"/doForget",
        beforeSend: function() {
            loadingIndex = layer.msg('正在发送邮件！', {icon: 16});
        },
        success : function(result){
            layer.close(loadingIndex);
            if(result.success){
                layer.msg("邮件发送成功，请注意邮箱的动态！", {time:2000, icon:6});

            }else{
                layer.msg(result.message, {time:1500, icon:5, shift:6});
            }
        }

    });
});