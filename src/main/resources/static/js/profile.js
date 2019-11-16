window.onload = function(){
    localInformation();
}
function tab1(){
    var body1 = $("#body1");
    var body2 = $("#body2");
    var foot1 = $("#foot1");
    var foot2 = $("#foot2");
    if(body1.is(":hidden")){
        body1.show();
        body2.hide();
        foot1.show();
        foot2.hide();
    }
}
function tab2(){
    var body1 = $("#body1");
    var body2 = $("#body2");
    var foot1 = $("#foot1");
    var foot2 = $("#foot2");
    if(body2.is(":hidden")){
        body2.show();
        body1.hide();
        foot2.show();
        foot1.hide();
    }
}
$("#selectedbtn").click(function(){
    $("#selectfile").click();
});
$('#selectfile').change(function(){
    $("#headimage").val($("#selectfile").val());
});
function localInformation(){
    $.ajax({
        type : "post",
        data : {},
        url : "/manager/user/localInformation",
        success : function(result){
            var data = result.memberResult;
            $("#memberid").val(data.id);
            $("#role").text(data.role);
            $("#loginacct").val(data.loginacct);
            $("#username").val(data.username);
            $("#email").val(data.email);
            // $("#headimage").val(data.imagePath);
            $("#personalnote").val(data.personalnote);
        }
    });
}

$("#savebtn").click(function(){
    var options = {
        url:"/manager/user/updateMemberInformation",
        beforeSubmit : function(){
            loadingIndex = layer.msg('数据正在保存中', {icon: 6});
            return true ;
        },
        success : function(result){
            layer.close(loadingIndex);
            if(result.success){
                layer.msg("数据保存成功,请重新登录！", {time:2000, icon:6});
                setTimeout(function(){
                    window.location.href="/doLogout";
                },2000);
            }else{
                layer.msg("数据保存失败", {time:1000, icon:5, shift:6});
            }
        }
    };
    //通过jquery的form表单异步提交表单
    $("#inforamationForm").ajaxSubmit(options);
    return ;

});
$("#updatebtn").click(function(){
    var originalpassword = $("#Originalpassword").val();
    var newpassword = $("#newpassword").val();
    var confirmpassword = $("#confirmpassword").val();
    var memberid = $("#memberid").val();
    if(Originalpassword == ""){
        layer.msg("请输入原密码！", {time:1000, icon:5, shift:6});
        return false;
    }
    if(newpassword == ""){
        layer.msg("请输入新密码！", {time:1000, icon:5, shift:6});
        return false;
    }
    if(confirmpassword == ""){
        layer.msg("请确认密码！", {time:1000, icon:5, shift:6});
        return false;
    }
    if(confirmpassword != newpassword){
        layer.msg("两次密码不正确！", {time:1000, icon:5, shift:6});
        return false;
    }else{
        $.ajax({
            type : "post",
            data :{
                "memberid" : memberid,
                "originalpassword" : originalpassword,
                "newpassword" : newpassword,
                "confirmpassword" : confirmpassword,
            },
            url : "/manager/user/updatePassword",
            success : function(result){
                if(result.success){
                    layer.msg("修改密码成功！", {time:1000, icon:6});
                }else{
                    layer.msg("原密码不正确！", {time:1000, icon:5, shift:6});
                }
            }
        });
    }
});