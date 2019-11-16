//获取参数中的id
var id ;
// 记录父id
var parentid ;
window.onload=function(){
    id=  GetQueryString("id");
    loadNav();
    loadArticle(1,10,id);
    loadPopular();
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
            backred(parentid);
        }

    })

}
//加载文章
function loadArticle(pageno,pagesize,id){
    $.ajax({
        type:"post",
        data : {
            "pagesize" : pagesize,
            "pageno" : pageno,
            "id" : id
        },
        url : "/loadArticleByLabelId",
        success : function(result){
            if(result.success){
                var page = result.page;
                var data = page.articleResults;
                var content = "";
                $.each(data,function(i,n){
                    content +='<div class="excerpt">';
                    content +='            <header>';
                    content +='                <a class="label">'+n.classification.name+'</a>';
                    content +='                <i class="glyphicon glyphicon-triangle-right"></i>';
                    content +='                <a href="/article?id='+n.id+'" class="arcitle-title" title='+n.title+'>';
                    content +='                    '+n.title+'';
                    content +='                </a>';
                    content +='            </header>';
                    content +='            <div class="focus">';
                    content +='                <a href="">';
                    content +='                    <img src="/image/thumbnail/'+n.picture.iconpath+'">';
                    content +='                </a>';
                    content +='            </div>';
                    content +='            <span class="note">'+n.content+'';
                    content +='                </span>';
                    content +='            <p>';
                    content +='                <span class="glyphicon glyphicon-user" style="color: #0099CC"><a style="color: #0099CC" href="/author?id='+n.memberid+'">'+n.username+' </a></span>';
                    content +='                <span class="glyphicon glyphicon-eye-open" style="color: #95a5a6"><a>'+n.access+'浏览 </a></span>';
                    content +='                <span class="glyphicon glyphicon-comment" style="color: #95a5a6"><a>'+n.comnum+'评论 </a></span>';
                    content +='                <span class="glyphicon glyphicon-heart" style="color: #FF6666"><a >'+n.likenum+'喜欢 </a></span>';
                    content +='            </p>';
                    content +='</div>';
                });
                //记录当前页
                $("#pageno").val(page.pageno);
                $(".col-lg-9").append(content);
            }
        }
    })
}
//滚动条
$(window).scroll(function() {
    if ($(document).scrollTop() >= $(document).height() - $(window).height()) {
        var pageno = $("#pageno").val();
        pageno =  parseInt(pageno)+1;
        loadArticle(pageno,10,id);
        $("#pageno").val(pageno+1);
    }
});
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