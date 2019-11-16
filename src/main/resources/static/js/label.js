window.onload = function(){
    loadLabel();
};
//加载标签
function loadLabel(){
    $.ajax({
        type: "post",
        url:"/manager/article/queryAllLabel",
        data : {},
        success : function(result){
            if(result.success){
                var data = result.ldata;
                var content = "";
                $.each(data,function(i,n){
                    content += "<button id='"+n.id+"' onclick='showLabel("+n.id+")'>"+n.name+"("+n.count+")</button>";
                });
                $("#labelContent").html(content);
            }
        }
    });
}
//添加标签
function addLabel(){
    var labelname = $("#labelname").val();
    if(labelname ==""){
        layer.msg("请输入标签名", {time:1000, icon:5});
        return false;
    }
    $.ajax({
        type : "post",
        url : "/manager/article/addLabel",
        data : {
            "labelname" : labelname,
        },
        success : function(result){
            if(result.success){
                window.location.href="/manager/article/toLabel";
            }else{
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        }

    });
}
//回显label
function showLabel(id){
    $.ajax({
        type : "post",
        url : "/manager/article/showLabel",
        data : {
            "id" : id
        },
        success : function(result){
            if(result.success){
                var data = result.label;
                var labelname = $("#labelname");
                var labelid = $("#labelid");
                labelname.val(data.name);
                labelid.val(data.id);
                $("#confirmbtn").removeAttr("onclick"); //删除点击事件
                $("#confirmbtn").addClass("invalid");  //添加class
                $("#confirmbtn").removeClass("confirmbtn"); //删除指定的class
                $("#updatebtn").addClass("updatebtn"); //添加class
                $("#updatebtn").attr("onclick","updateLabel()")
                $("#deletebtn").addClass("deletebtn"); //添加class
                $("#deletebtn").attr("onclick","deleteLabel()")
            }
        }
    });
}
//修改
function updateLabel(){
    var labelid = $("#labelid").val();
    var labelname = $("#labelname").val();
    if(labelname ==""){
        layer.msg("请输入标签名", {time:1000, icon:5});
        return false;
    }
    $.ajax({
        type : "post",
        url : "/manager/article/updateLabel",
        data : {
            "name" : labelname,
            "id" : labelid,
        },
        success : function(result){
            if(result.success){
                window.location.href="/manager/article/toLabel";
            }else{
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        }

    });
}
//删除
function deleteLabel(){
    layer.confirm("确定要删除吗?",  {icon: 3, title:'提示'}, function(cindex){
        var labelid = $("#labelid").val();
        $.ajax({
            type : "post",
            url : "/manager/article/deleteLabel",
            data : {
                "id" : labelid,
            },
            success : function(result){
                if(result.success){
                    window.location.href="/manager/article/toLabel";
                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            }

        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });
}
