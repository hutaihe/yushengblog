window.onload = function() {
    var id= GetQueryString("id");
    if(id == null){
        showtab1();
    }
}
function showtab1(){
    $("#top ul li:eq(0)").css("color","black");
    $("#top ul li:eq(1)").css("color","#3498db");
    $("#top ul li:eq(2)").css("color","#3498db");
    locadComment(1,5,1);
}
function showtab2(){
    $("#top ul li:eq(0)").css("color","#3498db");
    $("#top ul li:eq(1)").css("color","black");
    $("#top ul li:eq(2)").css("color","#3498db");
    locadComment(1,5,0);

}
function showtab3(){
    $("#top ul li:eq(1)").css("color","#3498db");
    $("#top ul li:eq(2)").css("color","black");
    $("#top ul li:eq(0)").css("color","#3498db");
    locadComment(1,5,2);
}
//加载评论
//获取请求地址的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
//加载评论
function locadComment(pageno,pagesize,status){

    $.ajax({
        type : "post",
        data : {
            "pageno" : pageno,
            "pagesize" :pagesize,
            "status" : status
        },
        url : "/manager/comment/locadComment",
        success : function(result){
            if(result.success){
                var page = result.page;
                var data = page.commentResultList;
                var  content = "";
                var fleftcontent = "";
                var frigthcontent = "";
                if(page.totalsize != 0){
                    $.each(data,function(i,n){
                        content +="<tr>";
                        content +="<td >"+n.username+"</td>";
                        content +="<td>"+n.comment+"</td>";
                        content +="<td>"+n.title+"</td>";
                        content +="<td>"+n.createtime+"</td>";
                        content += "<td>";
                        if(status == "0"){
                            content += " <button id = 'btn1' onclick='updateStatus(1,"+n.id+")'>发布</button>";
                            content += " <button id = 'btn3'onclick='updateStatus(2,"+n.id+")'>丢弃</button>";
                        }
                        if(status == "1"){
                            content += " <button id='btn2' onclick='window.location.href=\"/manager/comment/toReply?id="+n.id+"&articleid="+n.articleid+"\"'>回复</button>";
                            content += " <button id = 'btn3'onclick='updateStatus(2,"+n.id+")'>丢弃</button>";
                        }
                        if(status == "2"){
                            content += " <button id = 'btn1' onclick='updateStatus(0,"+n.id+")'>还原</button>";
                            content += " <button id = 'btn3'onclick='deleteComment("+n.id+")'>永久丢弃</button>";
                        }
                        content += "</td>";
                    });
                    $("#data1").html(content);
                    fleftcontent += "第"+page.pageno+"/"+page.totalno+"页";
                    $("#fldata1").html(fleftcontent);
                    frigthcontent += "<ul>";
                    frigthcontent += "<li onclick='locadComment(1,5,"+status+")'>首页</li>";
                    if(page.pageno == 1){
                        frigthcontent += "<li>上一页</li>";
                    }else{
                        frigthcontent += "<li onclick='locadComment("+(page.pageno-1)+",5,"+status+")'>上一页</li>";
                    }
                    if(page.pageno == page.totalno){
                        frigthcontent += "<li>下一页</li>";
                    }else{
                        frigthcontent += "<li onclick='locadComment("+(page.pageno+1)+",5,"+status+")'>下一页</li>";
                    }
                    frigthcontent += "<li onclick='locadComment("+page.totalno+",5,"+status+")'>尾页</li>";
                    frigthcontent += "</ul>";
                    $("#frdata1 ul").html(frigthcontent);
                }else{
                    var content = "<tr>";
                    content += "<td colspan=\"8\" style=\"text-align: center\">暂无数据</td>";
                    content += " </tr>";
                    $("#data1").html(content);

                    var fleftcontent = "第1/0页";
                    $("#fldata1").html(fleftcontent);
                    var frigthcontent = "<ul>";
                    frigthcontent += "<li >首页</li>";
                    frigthcontent += "<li >上一页</li>";
                    frigthcontent += "<li >下一页</li>";
                    frigthcontent += "<li >尾页</li>";
                    frigthcontent += "</ul>";
                    $("#frdata1 ul").html(frigthcontent);
                }
            }
        }
    });
}
//更新状态
function updateStatus(status,id){
    $.ajax({
        type : "post",
        data : {
            "id" : id,
            "status" : status
        },
        url : "/manager/comment/updateStatus",
        success : function(result){
            if(result.success){
                if(status == 0){
                    showtab2();
                }else if(status == 1){
                    showtab1();
                }else if(status == 2){
                    showtab3();
                }
            }
        }
    });
}
//永久删除
function deleteComment(id){
    $.ajax({
        type : "post",
        data : {
            "id" : id,
        },
        url : "/manager/comment/permanentDelete",
        success : function(result){
            layer.msg("永久删除成功！", {time: 1500, icon: 6});
            showtab3();
        }
    });
}