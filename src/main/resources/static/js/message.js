window.onload=function(){
    loadNav();
    loadMessage(0)
    loadPopular();  //热门排行
    loadLabel();
    loadLike()

}

//加载导航条
function loadNav(){
    $.ajax({
        type:"post",
        url: "/loadNav",
        success : function(result){
            if(result.success){
                var data = result.typeList;
                var content = "";
                $.each(data,function(i,n){
                    if(n.typeList.length != 0){

                        content += " <li id='"+n.id+"' class='dropdown'>";
                        content += "<a href='"+n.url+"?id="+n.id+"' class='dropdown-toggle' data-toggle='dropdown'>";
                        content +=n.name+ "<b class='caret'></b>";
                        content += " </a>";
                        content += "<ul  class='dropdown-menu'>";
                        $.each(n.typeList,function(i,b){
                            content += "<li><a href='"+b.url+"?id="+b.id+"'>"+b.name+"</a></li>";
                        });
                        content += "</ul>";
                        content += "</li>";
                    }else{

                        content += "<li id='"+n.id+"'><a href='"+n.url+"?id="+n.id+"'>"+n.name+"</a></li>";
                    }
                });
                content += "<li class='active'><a href='/message'>留言</a></li>";
                $("#nav").append(content);
            }else{
                alert("系统异常！");
            }
        }

    })

}
//热门排行
function loadPopular(){
    $.ajax({
        type : "post",
        url : "/loadPopular",
        success : function(result){
            if(result.success){
                var data = result.articleList;
                var content = "";
                $.each(data,function(i,n){
                    content += " <li>";
                    if(i == 0){
                        content += " <span class='label-1'>"+(i+1)+"</span>";
                    }else if(i == 1){
                        content += " <span class='label-2'>"+(i+1)+"</span>";
                    }else if(i == 2){
                        content += " <span class='label-3'>"+(i+1)+"</span>";
                    }else{
                        content += " <span class='label-4'>"+(i+1)+"</span>";
                    }
                    content += "  <a href='/article?id="+n.id+"'>"+n.title+"</a>";
                    content += " </li>";
                });
                $("#popular").html(content);
            }
        }
    })
}
//标签库
function loadLabel(){
    $.ajax({
        type: "post",
        url: "/loadLabel",
        success: function (result) {
            if (result.success) {
                var data = result.ldata;
                var content = "";
                $.each(data, function (i, n) {
                    content += " <button onclick='toTag("+n.id+")'>" + n.name + "</button>";
                });
                $("#label").html(content);
            }
        }
    })
}
function toTag(id){
    window.location.href="/tag?id="+id;
}
//猜你喜欢
function loadLike(){
    $.ajax({
        type : "post",
        url : "/loadLike",
        success :function(result){
            if(result.success){
                var data = result.articleResults;
                var content = "";
                $.each(data,function(i,n){
                    content += " <li class='like'>";
                    content += " <span class='like-1'>";
                    content += " <img src='/image/thumbnail/"+n.picture.iconpath+"'>";
                    content += " </span>";
                    content += " <span class='like-2'>";
                    content += " <a href='/article?id="+n.id+"'>"+n.title+"</a>";
                    content += " </span>";
                    content += " <span class='like-3'>"+n.creationtime+"</span>";
                    content += " <span class='like-4'>"+n.likenum+"喜欢</span>";
                    content += " </li>";
                });
                $("#like").html(content);
            }
        }
    })
}
//写点什么隐藏与显示
$("textarea").click(function(){
    $("small").hide()
})
$("textarea").blur(function(){
    if($("textarea").val() == ""){
        $("small").show()
    }
})
$(".reply").click(function(){
    $("#22").after($("#append"))
})
//加载留言
function loadMessage(pageno){
    $.ajax({
        type:"post",
        data : {
            "pagesize" : 10,
            "pageno" : pageno+1,
        },
        url : "/loadMessage",
        success : function(result){
            if(result.success){
                var page = result.page;
                var data = page.messageResultList;
                $("#messagenum").text("("+page.totalsize+")个小伙伴在吐槽");
                var content = "";
                $.each(data,function(i,n){
                    content += "<li>";
                    content += " <div class='userimage'>";
                    content += "<img src='/image/pic/"+n.image+"'>";
                    content += "</div>";
                    content += "<div class='commit'>";
                    content += "<p>"+n.content;
                    content += "</p>";
                    content += "<span>"+n.username+" </span>";
                    content += "<span>"+n.createtime+"</span>";
                    content += "</div>";
                    content += "</li>";
                    if(n.reply != null){
                        content += "<li style='margin-left: 80px'>";
                        content += " <div class='userimage'>";
                        content += "<img src='/image/3.png'>";
                        content += "</div>";
                        content += "<div class='commit'>";
                        content += "<p>"+n.reply;
                        content += "</p>";
                        content += "<span>管理员回复"+n.username+" </span>";
                        content += "</div>";
                        content += "</li>";
                    }
                });
                // 创建分页
                var num_entries = page.totalsize ;
                $("#Pagination").pagination(num_entries, {
                    num_edge_entries: 2, //边缘页数
                    num_display_entries: 4, //主体页数
                    callback: loadMessage,
                    items_per_page:page.pagesize, //每页显示1项
                    current_page:(page.pageno-1), //当前页,索引从0开始
                    prev_text:"上一页",
                    next_text:"下一页"
                });
                //记录当前页
                $("#pageno").val(page.pageno);
                $("#commit-content-content ul").html(content);
            }
        }
    })
}
//提交
function submitMessage(){
    var memberid = $("#memberid").val();
    if(memberid == null){
        layer.msg("请先登录再评论！", {time:3000, icon:5, shift:6});
    }else{
        alert($("#messagecontent").val());
        if($("#messagecontent").val() == ""){
            layer.msg("留言内容不能为空！", {time:3000, icon:5, shift:6});
        }else{
            $.ajax({
                type:"post",
                data : {
                    "memberid" : memberid,
                    "content" : $("#messagecontent").val()
                },
                url : "/submitMessage",
                success : function(result){
                    if(result.success){
                        $("#messagecontent").val("");
                        $("small").show()
                        loadMessage(0)

                    }else{
                        layer.msg("留言失败！", {time:3000, icon:5, shift:6});
                    }
                }
            })
        }

    }
}
function  search() {
    var display =$('#search').css('display');
    if(display == 'none'){
        $("#search").show()
    }else{
        $("#search").hide()
    }

}

$("#search").keyup(function(event){
    if(event.keyCode ==13){
        window.location.href="/search?keycode="+$("#search").val();
    }
});
$(document).ready(function(){
    $(document).off('click.bs.dropdown.data-api');
});