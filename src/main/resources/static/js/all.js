window.onload = function() {
    var status = GetQueryString("status");
    if(status == null){
        showtab1();
    }else{
        loadData(status,5,1); //什么转台的文章，一页五条数据
        if(status == 0){
            showtab3();
        }else if(status == 1){
            showtab2();
        }else if(status == 2){
            showtab1();
        }
        else if(status == 3){
            showtab4();
        }
    }
}

/*切换面板*/
function showtab1(){
    $("#top ul li:eq(0)").css("color","black");
    $("#top ul li:eq(1)").css("color","#3498db");
    $("#top ul li:eq(2)").css("color","#3498db");
    $("#top ul li:eq(3)").css("color","#3498db");
    $("#tab1").show();
    $("#tab2").hide();
    $("#tab3").hide();
    $("#tab4").hide();
    loadData(2,5,1);
}
function showtab2(){
    $("#top ul li:eq(0)").css("color","#3498db");
    $("#top ul li:eq(1)").css("color","black");
    $("#top ul li:eq(2)").css("color","#3498db");
    $("#top ul li:eq(3)").css("color","#3498db");
    $("#tab2").show();
    $("#tab1").hide();
    $("#tab3").hide();
    $("#tab4").hide();
    loadData(1,5,1);
}
function showtab3(){
    $("#top ul li:eq(1)").css("color","#3498db");
    $("#top ul li:eq(2)").css("color","black");
    $("#top ul li:eq(0)").css("color","#3498db");
    $("#top ul li:eq(3)").css("color","#3498db");
    $("#tab3").show();
    $("#tab1").hide();
    $("#tab2").hide();
    $("#tab4").hide();
    loadData(0,5,1);
}
function showtab4(){
    $("#top ul li:eq(3)").css("color","black");
    $("#top ul li:eq(0)").css("color","#3498db");
    $("#top ul li:eq(1)").css("color","#3498db");
    $("#top ul li:eq(2)").css("color","#3498db");
    $("#tab4").show();
    $("#tab1").hide();
    $("#tab2").hide();
    $("#tab3").hide();
    loadData(3,5,1);
}
/*切换面板*/
/*加载数据*/
function loadData(data,pagesize,pageno){
    $.ajax({
        type : "post",
        url : "/manager/article/loadData",
        data : {
            "data" : data,
            "pagesize":pagesize,
            "pageno":pageno
        },
        success : function(result){
            if(result.success){
                var page = result.page;
                var datas = page.articleResults;
                if(datas != null){
                    var content = "";
                    $.each(datas,function(i,n){
                        content += "<tr>";
                        content += "<td>"+n.title+"</td>";
                        content += "<td>"+n.username+"</td>";
                        content += "<td>"+n.typenames+"</td>";
                        content += "<td>"+n.labelnames+"</td>";
                        content += "<td>"+n.comnum+"</td>";
                        content += "<td>"+n.access+"</td>";
                        content += "<td>"+n.creationtime+"</td>";
                        if(data == 0){
                            content += "<td id='tranbtn'><button class='a1' id='editbtn' onclick='window.location.href=\"/manager/article/edit?id="+n.id+"\"'>编辑</button><button class='a2' onclick='release("+n.id+")'>发布</button><button class='a3' onclick='discard("+n.id+")'>丢弃</button></td>";
                        }
                        if(data == 1){
                            content += "<td id='tranbtn'><button class='a4' onclick='upperShelf("+n.id+")'>上架</button><button class='a1' id='editbtn' onclick='window.location.href=\"/manager/article/edit?id="+n.id+"\"'>编辑</button><button class='a3' onclick='discard("+n.id+")'>丢弃</button></td>";
                        }
                        if(data == 2){
                            content += "<td id='tranbtn'><button class='a1' onclick='lowerShelf("+n.id+")'>下架</button><button class='a3' onclick='discard("+n.id+")'>丢弃</button></td>";
                        }
                        if(data == 3){
                            content += "<td id='tranbtn'><button class='a1' onclick='reduction("+n.id+")'>还原</button><button class='a3' onclick='destruction("+n.id+")'>永久销毁</button></td>";
                        }

                        content += "</tr>";
                    });
                    var a = "#data"+data; //拼接一个id
                    var b = ".data"+data;//拼接一个class
                    $(".table "+a).html(content);
                    var fcontent = "";
                    fcontent += "<li onclick='loadData("+data+","+5+","+1+")'>首页</li>";
                    if(page.pageno == 1){
                        fcontent += "<li onclick='loadData("+data+","+5+","+1+")'>上一页</li>";
                    }else{
                        fcontent += "<li onclick='loadData("+data+","+5+","+(page.pageno-1)+")'>上一页</li>";
                    }
                    if(page.pageno == page.totalno){
                        fcontent += "<li onclick='loadData("+data+","+5+","+page.totalno+")'>下一页</li>";
                    }else{
                        fcontent += "<li onclick='loadData("+data+","+5+","+(page.pageno+1)+")'>下一页</li>";
                    }
                    var tcontent = "第"+page.pageno+" /"+page.totalno+"页";
                    $(b+" .tleft").html(tcontent);
                    /*分页栏*/
                    fcontent += "<li onclick='loadData("+data+","+5+","+page.totalno+")'>尾页</li>";

                    $(b+" .hfoot").html(fcontent);
                }
                else{
                    var a = "#data"+data; //拼接一个id
                    var b = ".data"+data;//拼接一个class
                    var content ="<tr>";
                    content += "<td colspan='8' style='text-align: center'>暂无数据</td>";
                    content += "</tr>";
                    $(".table "+a).html(content);
                }

            }
            else{
                layer.msg(result.message, {time:1000, icon:5, shift:6});
            }
        }
    })
}
//编辑
$("#editbtn").click(function(){
    window.location.href="/manager/article/edit/";
});
//发布
function release(id){
    $.ajax({
        type : "post",
        url : "/manager/article/release",
        data : {
            "id" : id,
            "status" : "1"
        },
        success : function(result){
            if(result.success){
                layer.msg("发布文章成功！", {time:2000, icon:6});
                showtab2();
            }else{
                layer.msg("发布文章失败！", {time:2000, icon:5,shift:6});
            }
        }
    });
}
//丢弃
function discard(id){
    layer.confirm("确定要丢弃吗？",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/article/discard",
            data : {
                "id" : id,
                "status" : "3"
            },
            success : function(result){
                if(result.success){
                    layer.msg("丢弃文章成功！", {time:2000, icon:6});
                    showtab4();
                }else{
                    layer.msg("丢弃文章失败！", {time:2000, icon:5,shift:6});
                }
            }
        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });


}
//还原
function reduction(id){
    $.ajax({
        type : "post",
        url : "/manager/article/reduction",
        data : {
            "id" : id,
            "status" : "0"
        },
        success : function(result){
            if(result.success){
                layer.msg("还原文章成功！", {time:2000, icon:6});
                showtab3();
            }else{
                layer.msg("还原文章失败！", {time:2000, icon:5,shift:6});
            }
        }
    });
}
//销毁
function destruction(id){
    layer.confirm("确定要销毁吗?",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/article/destruction",
            data : {
                "id" : id,
            },
            success : function(result){
                if(result.success){
                    layer.msg("销毁文章成功！", {time:2000, icon:6});
                    showtab4();
                }else{
                    layer.msg("销毁文章失败！", {time:2000, icon:5,shift:6});
                }
            }
        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });


}
//下架
function lowerShelf(id){
    layer.confirm("确定要下架吗?",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/article/lowerShelf",
            data : {
                "id" : id,
                "status" : "1",
            },
            success : function(result){
                if(result.success){
                    layer.msg("下架文章成功！", {time:2000, icon:6});
                    showtab2();
                }else{
                    layer.msg("下架文章失败！", {time:2000, icon:5,shift:6});
                }
            }
        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });
}
//上架
function  upperShelf(id){
    layer.confirm("确定要上架吗?",  {icon: 3, title:'提示'}, function(cindex){
        $.ajax({
            type : "post",
            url : "/manager/article/upperShelf",
            data : {
                "id" : id,
                "status" : "2",
            },
            success : function(result){
                if(result.success){
                    layer.msg("上架文章成功！", {time:2000, icon:6});
                    showtab1();
                }else{
                    layer.msg(result.message, {time:2000, icon:5,shift:6});
                }
            }
        });
        layer.close(cindex);
    }, function(cindex){
        layer.close(cindex);
    });
}
//获取请求地址的参数
function GetQueryString(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
