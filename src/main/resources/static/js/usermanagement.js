window.onload = function(){
    var jsonObj = {};
    jsonObj.pageno = 1;
    jsonObj.pagesize = 10;
    jsonObj.keycode = null;
    loadUser(jsonObj)
}
function loadUser(jsonObj){
    $.ajax({
        type : "post",
        url : "/manager/user/loadUser",
        data : jsonObj,
        success : function(result){
            if(result.success){
                var data = result.page.memberList;
                var content = "";
                var paging = "";
                $.each(data,function(i,n){
                    content += "<tr>";
                    content += "<td>"+(i+1)+"</td>";
                    content += "<td>"+n.loginacct+"</td>";
                    content += "<td>"+n.username+"</td>";
                    content += "<td>"+n.email+"</td>";
                    content += "<td>"+n.createtime+"</td>";
                    content += "<td>";
                    content += " <button class='btn btn btn-success' onclick='updateBtn("+n.id+")'>修改</button>";
                    /* content += "<button class='btn btn-primary'>修改</button>";*/
                    /*                 content += "<button class='btn btn-danger' onclick='deleteUser("+n.id+",\'"+n.username+"\')> 删除</button>";*/
                    content += '<button class="btn btn-danger" onclick="deleteUser(' + n.id + ',\'' + n.username + '\')">删除</button>';
                    content += "</td>";
                    content += "</tr>";

                });
                //拼接分页
                paging += "<tr>";
                paging += " <td colspan='5' align='center'>";
                paging += " <ul class='pagination'>";
                if(result.page.pageno == 1){
                    paging += " <li class='disabled'><a >上一页</a></li>";
                }else{
                    paging += " <li><a onclick='paging("+(result.page.pageno-1)+")' '>上一页</a></li>";
                }
                for( i = 1 ; i <= result.page.totalno ; i++){
                    paging += "<li";
                    if(i == result.page.pageno){
                        paging += " class='active'";
                    }
                    paging += ">";
                    paging += " <a href='#' onclick='paging("+i+")'>"+i+" <span class='sr-only'>(current)</span></a></li>";
                }
                if(result.page.pageno == result.page.totalno){
                    paging += " <li class='disabled'><a href='#'>下一页</a></li>";
                }else{
                    paging += " <li><a onclick='paging("+(result.page.pageno+1)+")'>下一页</a></li>";
                }
                paging += "</ul>";
                paging += "</td>";
                paging += "</tr>";

                $("tbody").html(content);
                $("tfoot").html(paging);
            }else{
                var content = "<tr>";
                content += "<td colspan='6' align='center'>";
                content += "暂无数据";
                content += "</td>";
                content += "</tr>";
                $("tbody").html(content);
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        }
    })
}
function paging(pageno){
    var jsonObj = {};
    jsonObj.pageno = pageno;
    jsonObj.pagesize = 10;
    loadUser(jsonObj);
}
//查询
function search(){
    var keycode = $("#keycode").val();
    var jsonObj = {};
    jsonObj.pageno = 1;
    jsonObj.pagesize = 10;
    jsonObj.keycode = keycode;
    loadUser(jsonObj);
}
//点击添加按钮
function addBtn(){
    $("#panel1").hide();
    $("#panel2").show();
    $("#floginacct").focus();
}
//添加用户
function add(){
    var floginacct = $("#floginacct");
    var fusername = $("#fusername");
    var femail = $("#femail");
    var fuserpswd1 = $("#fuserpswd1");
    var fuserpswd2 = $("#fuserpswd2");
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
    if(fuserpswd1.val() == ""){
        layer.msg("请输入登录密码！", {time:1500, icon:5, shift:6});
        fuserpswd1.focus();
        return false;
    }
    if(fuserpswd2.val() == ""){
        layer.msg("请确认密码！", {time:1500, icon:5, shift:6});
        fuserpswd2.focus();
        return false;
    }else{
        if(fuserpswd1.val() != fuserpswd2.val()){
            layer.msg("两次密码不一致，请重新确认密码！", {time:1500, icon:5, shift:6});
            fuserpswd2.val("");
            fuserpswd2.focus();
            return false;
        }
    }
    var loadingIndex = -1;
    var floginacct = $("#floginacct");
    var fusername = $("#fusername");
    var femail = $("#femail");
    var fuserpswd2 = $("#fuserpswd2");
    $.ajax({
        type: "post",
        data : {
            "loginacct" : floginacct.val(),
            "username" : fusername.val(),
            "email" : femail.val(),
            "userpswd" : fuserpswd2.val()
        },
        url :"/doRegister",
        beforeSend: function() {
            loadingIndex = layer.msg('正在添加', {icon: 16});
        },
        success : function(result){
            layer.close(loadingIndex);
            if(result.success){
                layer.msg("添加用户成功！", {time:1500, icon:6});
                $("#floginacct").val("");
                $("#fusername").val("");
                $("#femail").val("");
                $("#fuserpswd1").val("");
                $("#fuserpswd2").val("");
                $("#floginacct").focus();
            }else{
                layer.msg(result.message, {time:1500, icon:5, shift:6});
            }
        }

    });
}
//清空文本框
function clearText(){
    $("#floginacct").val("");
    $("#fusername").val("");
    $("#femail").val("");
    $("#fuserpswd1").val("");
    $("#fuserpswd2").val("");
    $("#floginacct").focus();
}
//取消添加
function unadd(){
    $("#panel1").show();
    $("#panel2").hide();
    $("#floginacct").val("");
    $("#fusername").val("");
    $("#femail").val("");
    $("#fuserpswd1").val("");
    $("#fuserpswd2").val("");
    $("#floginacct").focus();
    var jsonObj = {};
    jsonObj.pageno = 1;
    jsonObj.pagesize = 10;
    jsonObj.keycode = null;
    loadUser(jsonObj)
}

//点击修改的按钮
function updateBtn(id){

    $.ajax({
        type : "post",
        url : "/manager/user/echoeUser",
        data : {
            "id":id
        },
        success : function(result){
            if(result.success){
                var data = result.memberResult;
                if(data.role == "管理员"){
                    $("#administrators").attr("checked",true);
                    $("#administrators").attr("disabled",true);
                }else if (data.role == "投稿者") {
                    $("#contributor").attr("checked",true);
                    $("#contributor").attr("disabled",true);
                }
                $("#uloginacct").val(data.loginacct);
                $("#uusername").val(data.username);
                $("#uemail").val(data.email);
                $("#memberid").val(data.id);

                $("#panel1").hide();
                $("#panel3").show();
            }else{
                layer.msg("修改操作失败！", {time:1000, icon:5, shift:6});
            }
        }
    });

}
//修改用户
function update(){
    var username = $("#uusername").val();
    var email = $("#uemail").val();
    $.ajax({
        type : "post",
        url : "/manager/user/updateUser",
        data : {
            "id":$("#memberid").val(),
            "username" : username,
            "email" : email
        },
        success : function(result){
            if(result.success){
                layer.msg(result.message, {time:3000, icon:6, shift:6});
            }else{
                layer.msg(result.message, {time:3000, icon:5, shift:6});
            }
        }
    });
}
//还原
function reductionBtn(){
    updateBtn($("#memberid").val())
}
function goback(){
    $("#panel3").hide();
    $("#panel1").show();
}
function deleteUser(id,name){
    layer.confirm("确定要"+name+"吗？",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/user/deleteUser",
            data : {
                "id":id
            },
            success : function(result){
                if(result.success){
                    var jsonObj = {};
                    jsonObj.pageno = 1;
                    jsonObj.pagesize = 10;
                    jsonObj.keycode = null;
                    loadUser(jsonObj)
                    layer.msg(result.message, {time:3000, icon:6, shift:6});
                }else{
                    layer.msg(result.message, {time:3000, icon:5, shift:6});
                }
            }
        });
        layer.close(cindex);
    }, function(cindex){

        layer.close(cindex);
    });

}