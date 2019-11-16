window.onload = function(){
    loadType();
};
//加载分类目录
function loadType(){
    $.ajax({
        type : "post",
        url : "/manager/article/load",
        data : {},
        success : function(result){
            if(result.success){
                var data = result.typeList;
                var content = "<option value=\"0\">无父节点</option>";
                var contrntable="";
                $.each(data,function(i,n){
                    content += "<option value='"+n.id+"'>"+n.name+"</option>";
                    contrntable+='<tr>';
                    contrntable+='<td>'+n.name+'</td>';
                    contrntable+='<td>'+n.count+'</td>';
                    contrntable+=' <td>';
                    contrntable+='     <button class="modifybtn" onclick="queryTypeById('+n.id+')">修改</button>';
                    contrntable+='     <button class="delbtn" onclick="deleteType('+n.id+',\''+n.name+'\')">永久删除</button>';
                    contrntable+='</td>';
                    contrntable+='</tr>';
                    if(n.typeList != null){
                        $.each(n.typeList,function(i,n){
                            content += "<option value='"+n.id+"'>————"+n.name+"</option>";
                            contrntable+='<tr>';
                            contrntable+='<td>————'+n.name+'</td>';
                            contrntable+='<td>'+n.count+'</td>';
                            contrntable+=' <td>';
                            contrntable+='     <button class="modifybtn" onclick="queryTypeById('+n.id+')">修改</button>';
                            contrntable+='     <button class="delbtn" onclick="deleteType('+n.id+',\''+n.name+'\')">永久删除</button>';
                            contrntable+='</td>';
                            contrntable+='</tr>';
                            if(n.typeList != null){
                                $.each(n.typeList,function(i,n){
                                    contrntable+='<tr>';
                                    contrntable+='<td>————————'+n.name+'</td>';
                                    contrntable+='<td>'+n.count+'</td>';
                                    contrntable+=' <td>';
                                    contrntable+='     <button class="modifybtn" onclick="queryTypeById('+n.id+')">修改</button>';
                                    contrntable+='     <button class="delbtn" onclick="deleteType('+n.id+',\''+n.name+'\')">永久删除</button>';
                                    contrntable+='</td>';
                                    contrntable+='</tr>';
                                })

                            }
                        })
                    }
                });
                $("#typesel").html(content);
                $("tbody").html(contrntable);
            }
        }
    });
}
//添加
function addType(){
    var value = $("#typesel option:selected").val();
    var typename = $("#typename").val();
    if(typename ==""){
        layer.msg("请输入分类名", {time:1000, icon:5});
        return false;
    }
    $.ajax({
        type : "post",
        url : "/manager/article/addType",
        data : {
            "pid" : value,
            "typename" : typename,
        },
        success : function(result){
            if(result.success){
                window.location.href="/manager/article/toCatalogue";
            }else{
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        }

    });
}

//修改数据回显
function queryTypeById(id){
    $.ajax({
        type : "post",
        url : "/manager/article/queryTypeById",
        data : {
            "id" : id
        },
        success : function(result){
            if(result.success){
                var data = result.type;
                $("#typename").val(data.name);
                $("#typeid").val(data.id);
                if(data.pid == null){
                    $("#typesel").find('option[value=\'0\']').prop("selected","true");
                }else{
                    $("#typesel").find('option[value='+data.pid+']').prop("selected","true");
                }
                $("#confirmbtn").removeAttr("onclick"); //删除点击事件
                $("#confirmbtn").addClass("invalid");  //添加class
                $("#confirmbtn").removeClass("confirmbtn"); //删除指定的class
                $("#updatebtn").addClass("updatebtn"); //添加class
                $("#updatebtn").attr("onclick","updateType()")
            }
        }
    });
}
//修改分类
function updateType(){
    var value = $("#typesel option:selected").val();
    var typename = $("#typename").val();
    var typeid =  $("#typeid").val();
    if(typename ==""){
        layer.msg("请输入分类名字", {time:1000, icon:5});
        return false;
    }
    $.ajax({
        type : "post",
        url : "/manager/article/updateType",
        data : {
            "pid" : value,
            "name" : typename,
            "id" : typeid
        },
        success : function(result){
            if(result.success){
                window.location.href="/manager/article/toCatalogue"
            }else{
                layer.msg(result.message, {time:1000, icon:5});
            }
        }

    });
}
//永久删除
function deleteType(id,name){
    layer.confirm("你确定要删除["+name+"]吗？",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/article/deleteType",
            data : {
                "id" : id
            },
            success : function(result){
                if(result.success){
                    window.location.href="/manager/article/toCatalogue"
                }else{
                    layer.msg(result.message, {time:1000, icon:5});
                }
            }

        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });

}