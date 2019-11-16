window.onload = function(){
    var jsonObj = {};
    jsonObj.pageno = 1;
    jsonObj.pagesize = 10;
    jsonObj.keycode = null;
    loadMessage(jsonObj)
}
function loadMessage(jsonObj){
    $.ajax({
        type : "post",
        url : "/manager/message/loadMessage",
        data : jsonObj,
        success : function(result){
            if(result.success){
                var data = result.page.messageList;
                var content = "";
                var paging = "";
                $.each(data,function(i,n){
                    content += "<tr>";
                    content += "<td>"+(i+1)+"</td>";
                    content += "<td>"+n.username+"</td>";
                    content += "<td>"+n.content+"</td>";
                    content += "<td>"+n.createtime+"</td>";
                    if(n.status == "未回复"){
                        content += "<td style='color: red'>"+n.status+"</td>";
                    }else{
                        content += "<td style='color: #3c8dbc'>"+n.status+"</td>";
                    }
                    content += "<td>";
                    if(n.status == "未回复"){
                        content += " <button class='btn btn btn-success' onclick='replybtn("+n.id+")' >回复</button>";
                    }else{
                        content += " <button class='btn btn-primary' onclick='replybtn("+n.id+")' >修改</button>";
                    }
                    content += '<button class="btn btn-danger" onclick="deleteMessage('+n.id+',\''+n.username+'\')">删除</button>';
                    content += "</td>";
                    content += "</tr>";
                });
                //拼接分页
                paging += "<tr>";
                paging += " <td colspan='6' align='center'>";
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
    loadMessage(jsonObj);
}
function search(){
    var keycode = $("#keycode").val();
    var jsonObj = {};
    jsonObj.pageno = 1;
    jsonObj.pagesize = 10;
    jsonObj.keycode = keycode;
    loadMessage(jsonObj);
}
function replybtn(id){
    $.ajax({
        type : "post",
        url : "/manager/message/eachMessage",
        data : {
            "id" : id
        },
        success : function(result) {
            if (result.success) {
                var data = result.mesa;
                $("#username").text(data.username);
                $("#createtime").text(data.createtime);
                $("#messagecontent").text(data.content);
                if(data.reply == null){
                    $("#replycontent").val("");
                }else{
                    $("#replycontent").text(data.reply);
                }

                $("#messageid").val(data.id);
                if(data.status == "未回复"){
                    $("#replybtn").show();
                    $("#updatebtn").hide();
                }else{
                    $("#updatebtn").show();
                    $("#replybtn").hide();
                }

                $("#panel1").hide();
                $("#panel2").show();
            }else{
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        }
    });
}
function reply(){
    if($("#replycontent").val() == ""){
        layer.msg("回复内容不能为空！", {time:1000, icon:5, shift:6});
    }else{
        $.ajax({
            type : "post",
            url : "/manager/message/reply",
            data : {
                "id" :  $("#messageid").val(),
                "reply" : $("#replycontent").val(),
            },
            success : function(result) {
                if (result.success) {
                    $("#updatebtn").show();
                    $("#replybtn").hide();
                    layer.msg("回复成功", {time:1000, icon:6, shift:6});

                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            }
        });
    }
}
function goback(){
    $("#panel2").hide();
    $("#panel1").show();
    var jsonObj = {};
    jsonObj.pageno = 1;
    jsonObj.pagesize = 10;
    jsonObj.keycode = null;
    loadMessage(jsonObj);
}
function deleteMessage(id,name){
    layer.confirm("确定要删除"+name+"的留言吗？",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/message/deleteMessage",
            data : {
                "id":id,
            },
            success : function(result){
                if(result.success){
                    var jsonObj = {};
                    jsonObj.pageno = 1;
                    jsonObj.pagesize = 10;
                    jsonObj.keycode = null;
                    loadMessage(jsonObj)
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