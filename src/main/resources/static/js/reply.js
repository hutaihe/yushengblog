window.onload = function(){
    var id = GetQueryString("id");
    var articleid = GetQueryString("articleid");

    if(id != null && articleid != null){
        loadReply(id,articleid);
    }else{
        window.location.href="/manager/comment/toManagerComment";
    }

};

//加载评论
function loadReply(id,articleid){
    $.ajax({
        type : "post",
        data : {
            "id" : id,
            "articleid" : articleid
        },
        url : "/manager/comment/loadReply",
        success : function(result){
            var data = result.commentResult;
            content = "";
            content += "<tr>";
            content += "<td ><span class='span1'>"+data.username+"</span>："+data.comment+"</td>";
            content += "<td>";
            content += " <button class=\"btn1\" onclick='eachCommment("+data.id+")'>回复</button>";
            content += "<button class=\"btn3\" onclick='deletebtn("+data.id+")'>删除</button>";
            content += "</td>";
            content += "</tr>";
            if(data.commentResults.length != 0){
                $.each(data.commentResults,function(i,n){
                    content += "<tr>";
                    content += "<td style='padding-left: 15px'><span class='span1'>"+n.username+"</span><span class='span2'>&nbsp;回复&nbsp;</span><span class='span1'>"+data.username+"</span>："+n.comment+"</td>";
                    content += "<td>";
                    content += " <button class=\"btn1\" onclick='eachCommment("+n.id+")'>回复</button>";
                    content += "<button class=\"btn3\" onclick='deletebtn("+n.id+")'>删除</button>";
                    content += "</td>";
                    content += "</tr>";
                });
            }
            $("tbody").html(content);
        }
    });
}
//评论信息回显表单
function eachCommment(id){
    $.ajax({
        type : "post",
        data : {
            "id" : id
        },
        url : "/manager/comment/eachCommment",
        success : function(result){
            if(result.success){
                var data = result.commentResult;
                $("#fid").val(data.id);
                $("#farticleid").val(data.articleid);
                $("#fppid").val(data.ppid);
                $("#fusername").val(data.username);
                $("#ftime").val(data.createtime);
                $("#fcontent").val(data.comment);
                $("#replycontent").val("");
                $("#replybtn").removeClass("invalid");
                $("#noreplybtn").removeClass("invalid");
                $("#replybtn").attr("onclick","replybtn()");
                $("#noreplybtn").attr("onclick","noreplybtn()");
                $("#replybtn").attr("class","replybtn");
                $("#noreplybtn").attr("class","noreplybtn");
            }
        }
    });
}
//回复
function replybtn(){
    var id = $("#fid").val();
    var articleid = $("#farticleid").val();
    var ppid = $("#fppid").val();
    var comment = $("#replycontent").val();
    if(comment == ""){
        layer.msg("请输入评论！", {time: 1000, icon: 5,shit:6});
        return false;
    }
    $.ajax({
        type : "post",
        data : {
            "id" : id,
            "articleid" : articleid,
            "ppid" : ppid,
            "comment" : comment,
        },
        url : "/manager/comment/saveComment",
        success : function(result){
            if(result.success){
                layer.msg("回复成功", {time: 1000, icon: 6});
                var id = GetQueryString("id");
                var articleid = GetQueryString("articleid");
                loadReply(id,articleid);
                noreplybtn();

            }
        }
    });

}
//算了
function noreplybtn(){
    $("#fid").val("");
    $("#farticleid").val("");
    $("#fppid").val("");
    $("#fusername").val("");
    $("#ftime").val("");
    $("#fcontent").val("");
    $("#replycontent").val("");
    $("#replybtn").removeAttr("onclick");
    $("#noreplybtn").removeAttr("onclick");
    $("#replybtn").removeClass("replybtn");
    $("#noreplybtn").removeClass("noreplybtn");
    $("#replybtn").attr("class","invalid");
    $("#noreplybtn").attr("class","invalid");
}
//删除
function deletebtn(id){
    layer.confirm("你确定要删除这条评论吗？",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            data : {
                "id" : id
            },
            url : "/manager/comment/deleteComment",
            success : function(result){
                if(result.success){
                    layer.msg("删除评论成功！", {time: 1000, icon: 6});
                    var pid = GetQueryString("id");
                    var articleid = GetQueryString("articleid");
                    if(id == pid){
                        window.location.href="/manager/comment/toManagerComment"
                    }else{
                        loadReply(pid,articleid)
                    }

                }
            }
        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });
}
//获取请求地址的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}