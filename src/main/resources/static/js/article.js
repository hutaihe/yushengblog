//获取参数中的id
var id ;
// 记录父id
var parentid ;
window.onload=function(){
    id=  GetQueryString("id");
    loadNav();
    loadPopular();
    loadLabel();
    loadLike();
    loadArticle(id);
    // picturecontrol();
    loadComment(id);

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
                        if(id == n.id){
                            parentid =  n.id;
                        }
                        content += " <li id='"+n.id+"' class='dropdown'>";
                        content += "<a href='"+n.url+"?id="+n.id+"' class='dropdown-toggle' data-toggle='dropdown'>";
                        content +=n.name+ "<b class='caret'></b>";
                        content += " </a>";
                        content += "<ul  class='dropdown-menu'>";
                        $.each(n.typeList,function(i,b){
                            if(id == b.id){
                                parentid =  n.id;

                            }
                            content += "<li><a href='"+b.url+"?id="+b.id+"'>"+b.name+"</a></li>";
                        });
                        content += "</ul>";
                        content += "</li>";
                    }else{
                        if(id == n.id){
                            parentid= n.id;
                        }
                        content += "<li id='"+n.id+"'><a href='"+n.url+"?id="+n.id+"'>"+n.name+"</a></li>";
                    }
                });
                content += "<li><a href='/message'>留言</a></li>";
                $("#nav").append(content);
            }else{
                alert("系统异常！");
            }
        }

    })
}
//获取请求地址的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
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
//加载文章内容
function loadArticle(id){
    myajax  = $.ajax({
        type : "post",
        data : {
            "id" : id
        },
        url : "/articleContent",
        success :function(result){
            if(result.success){
                var data = result.articleResult;
                var last = result.last;
                var next = result.next;
                var articlelist = result.articleList;
                var top = "";
                var title = "";
                var label = ""; //标签
                var upper = ""; //上一篇
                var lower = ""; //下一篇
                var recommend = "" //推荐
                if(data.types.length>=2){
                    if(data.types[0].pid == null){
                        top += "<a href='/category?id="+data.types[0].id+"' title='"+data.types[0].name+"'>";
                        top += data.types[0].name;
                        top += "</a>";
                        top += " <small>&nbsp;>&nbsp;</small>";
                        top += "<a href='/category?id="+data.types[1].id+"' title='"+data.types[1].name+"'>";
                        top += data.types[1].name;
                        top += "</a>";
                        top += " <small>&nbsp;>&nbsp;</small>";
                        top += " <span style='color: #777'>";
                        top += data.title;
                        top += "</span>";
                    }else{
                        top += "<a href='/category?id="+data.types[1].id+"' title='"+data.types[1].name+"'>";
                        top += data.types[1].name;
                        top += "</a>";
                        top += " <small>&nbsp;>&nbsp;</small>";
                        top += "<a href='/category?id="+data.types[0].id+"' title='"+data.types[0].name+"'>";
                        top += data.types[0].name;
                        top += "</a>";
                        top += " <small>&nbsp;>&nbsp;</small>";
                        top += " <a title='"+data.title+"' style='color: #777'>";
                        top += data.title;
                        top += "</a>";
                    }
                }else{
                    top += "<a title='"+data.types[0].name+"'>";
                    top += data.types[0].name;
                    top += "</a>";
                    top += " <small>&nbsp;>&nbsp;</small>";
                    top += " <a title='"+data.title+"' style='color: #777'>";
                    top += data.title;
                    top += "</a>";
                }
                title += "<h1>"+data.title+"</h1>";
                title += " <div id='meta'>";
                if(+data.types.length>=2){
                    title += "<span class='glyphicon glyphicon-th-large'> <a href='/category?id="+data.types[1].id+"'>"+data.types[1].name+"</a></span>";
                }else{
                    title += "<span class='glyphicon glyphicon-th-large'> <a href='/category?id="+data.types[0].id+"'>"+data.types[0].name+"</a></span>";
                }
                title += " <span class='glyphicon glyphicon-user'> <a href='/author?id="+data.memberid+"'>"+data.username+"</a></span>";
                title += "<span class='glyphicon glyphicon-time' style='color: #95a5a6'> "+data.creationtime+"</span>";
                title += "<span class='glyphicon glyphicon-eye-open' style='color: #95a5a6'> "+data.access+"</span>";
                title += "<span class='glyphicon glyphicon-list-alt' style='color: #95a5a6'> "+data.comnum+"</span>";
                title += " </div>";
                $.each(data.labels,function(i,n){
                    if(i<6){
                        label +=" <span class='span"+(i+1)+"'>"+n.name+"</span>"
                    }
                })
                $("#content-top").append(top);
                $("#content-title").html(title);
                $("#content-content").html(data.content)
                //文章标签
                $("#content-footer-label").append(label)
                //上下篇
                if(last != null){
                    upper +="<a href='/article?id="+last.id+"'>"
                    upper +=" <p>上一篇</p>"
                    upper +=" <p>"+last.title+"</p>"
                    upper +="</a>"
                }else{
                    upper +="<a '>"
                    upper +=" <p>上一篇</p>"
                    upper +=" <p>没有上一篇了</p>"
                    upper +="</a>"
                }

                if(next != null){
                    lower +="<a href='/article?id="+next.id+"'>"
                    lower +=" <p>下一篇</p>"
                    lower +=" <p>"+next.title+"</p>"
                    lower +="</a>"
                }else{
                    lower +="<a '>"
                    lower +=" <p>下一篇</p>"
                    lower +=" <p>没有下一篇了</p>"
                    lower +="</a>"
                }
                $(".last").html(upper)
                $(".next").html(lower)
                //四条推荐文章
                $.each(articlelist,function(i,n){
                    recommend += " <li>";
                    recommend += " <img src='/image/thumbnail/"+n.picture.iconpath+"' height='auto' width='100%'>";
                    recommend += " <br>";
                    recommend += "<span><a href='article?id="+n.id+"'>"+n.title+"</a></span>";
                    recommend += "</li>";
                })
                $("#recommend ul").html(recommend);
                $(".bt2 small").text(data.likenum);
                $(".bt1 small").text(data.fabulous);


            }
        }
    })

}
//点赞
$(".bt1").click(function(){
    var id=  GetQueryString("id");
    $.ajax({
        type : "post",
        data : {
            "id" : id
        },
        url : "/fabulousArticle",
        success : function(result){
            if(result.success){
                var count = parseInt($(".bt1 small").text());
                $(".bt1 small").text(count+1)
            }
        }
    })
})
//喜欢
$(".bt2").click(function(){
    var id=  GetQueryString("id");
    $.ajax({
        type : "post",
        data : {
            "id" : id
        },
        url : "/likeArticle",
        success : function(result){
            if(result.success){
                var count = parseInt($(".bt2 small").text());
                $(".bt2 small").text(count+1)
            }
        }
    })
})
$("#comment-middle textarea").click(function(){
    $("#comment-middle small").hide()
})
$("#comment-middle textarea").blur(function(){
    if(this.value == ""){
        $("#comment-middle small").show()
    }

})
//加载评论
function loadComment(articleid){
    $.ajax({
        type : "post",
        data : {
            "articleid" : articleid
        },
        url : "/loadComment",
        success : function(result){
            var data = result.commentResultList;
            var content = "";
            $.each(data,function(i,n){
                content += " <li id='"+n.id+"'>";
                content += "<div class='comment-comment-image'>";
                content += "<img src='/image/pic/"+n.member.image+"'>";
                content += "</div>";
                content += "<div class='comment-comment-comtent'>";
                content += " <p>"+n.comment+"</p>";
                content += " <span>"+n.member.username+"</span>";
                content += "<span>"+n.createtime+"</span>";
                content += "<span><a onclick='reply("+n.id+",0,"+n.id+")'>回复</a></span>";
                content += "</div>";
                content += "</li>";
                if(n.commentResults.length>0){
                    $.each(n.commentResults,function(i,b){
                        content += " <li style='margin-left: 80px' id='"+b.id+"'>";
                        content += "<div class='comment-comment-image'>";
                        content += "<img src='/image/pic/"+b.member.image+"'>";
                        content += "</div>";
                        content += "<div class='comment-comment-comtent'>";
                        content += " <p>"+b.comment+"</p>";
                        content += " <span>"+b.member.username+"<small>回复</small>"+b.responder+"</span>";
                        content += "<span>"+b.createtime+"</span>";
                        content += "<span><a onclick='reply("+b.id+",1,"+n.id+")'>回复</a></span>";
                        content += "</div>";
                        content += "</li>";
                    })
                }
            })
            $("#comment-comment ul").html(content);
            $("#conmun span").html(result.connum);

        }
    })
}
//提交评论
function submitComment(){
    var memberid = $("#memberid").val();
    var commentContent = $("#commentContent").val();
    var articleid=  GetQueryString("id");
    var pid = $("#becomment").val();// 获取pid
    var json = {};
    //如果pid为空，则提交的评论是父评论，如果pid不为空，则为子评论
    if(pid != null){
        json.pid = pid
        json.ppid = $("#ppid").val(); //获取父id
    }else{
        json.pid= null
    }
    json.memberid = memberid;
    json.commentContent = commentContent;
    json.articleid = articleid;
    if(memberid == null){
        $("#commentContent").val("");
        $("#comment-middle small").show();
        layer.msg("请先登录再评论！", {time:1000, icon:5, shift:6});
    }else{
        $.ajax({
            type : "post",
            url : "/submitComment",
            data : json,
            success: function(result){
                if(result.success){
                    loadComment(articleid)
                    $("#commentContent").val("");
                    $("#comment-middle small").show()
                    //将评论区移到上面去
                    $("#append").css("margin-left","0")
                    $("#coordinate").after($("#append"))
                    $("#cancel").hide();
                    //评论加一
                    $(".glyphicon-list-alt").text(parseInt($(".glyphicon-list-alt").text())+1)
                }
            }
        })
    }
    $("#becomment").val("");
}
function reply(id,num,ppid){
    var memberid = $("#memberid").val();
    if(memberid == null){
        layer.msg("请先登录再评论！", {time:1000, icon:5, shift:6});
    }else{
        $("#cancel").show();
        $("#becomment").val(id);
        $("#ppid").val(ppid)
        if(num == "0"){
            $("#"+id).after($("#append"));
            $("#append").css("margin-left","0px")
        }else{
            $("#append").css("margin-left","80px");
            $("#"+id).after($("#append"))
        }
        $("#comment-middle textarea").focus();
        $("#comment-middle small").hide();
    }
}
$("#cancel").click(function(){
    $("#append").css("margin-left","0")
    $("#coordinate").after($("#append"))
    $("#cancel").hide();
})
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