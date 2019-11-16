$("#checkLogin").click(function(){
    var floginacct = $("#floginacct");
    var fuserpswd = $("#fuserpswd");
    var beforeSend = -1;

    if(floginacct.val() == ""){
        layer.msg("请输入登录账号！", {time:1000, icon:5, shift:6});
        return false;
    }
    if(fuserpswd.val() == ""){
        layer.msg("请输入登录密码！", {time:1000, icon:5, shift:6});
        return false;
    }else{
        var loginacct =   floginacct.val();
        var userpswd = fuserpswd.val();
        var remember = $("#remember").is(":checked");
        $.ajax({
            type : "post",
            url : "/doLogin",
            data:{
                "loginacct" : loginacct,
                "userpswd" : userpswd,
                "remember" : remember == true ? 0 : 1
            },
            beforeSend: function() {
                loadingIndex = layer.msg('正在登录', {icon: 16});
            },
            success : function(result){
                layer.close(beforeSend);
                if(result.success){
                    window.location.href="/main"
                }
                else{
                    layer.msg("账号或密码错误！", {time:1000, icon:5, shift:6});
                }
            }
        });
    }

});