/*初始化富文本*/
var ue = UE.getEditor('myEditor');
window.onload = function() {
    //加载标签
    loadLabel();
    //加载分类
    loadType();
    //加载缩略图
    loadThumbnail();
    //如果参数是有id的则回显文章数据

}
//执行修改文章的方法
function updateArticle(jsonObj) {
    $.ajax({
        type: "post",
        data: jsonObj,
        dataType: "json",
        url: "/manager/article/updateArticle",
        success: function (result) {
            if (result.success) {
                layer.msg("修改文章成功！", {time: 2000, icon: 6});
                window.location.href="/manager/article/toAll?status="+jsonObj.status;
            } else {
                layer.msg("修改文章失败！", {time: 2000, icon: 5, shift: 6});
            }
        }
    });
}
//文章数据回显
function echoDisplay (id) {
    var articletitle = $("#titleofarticle");
    var ue = UE.getEditor('myEditor');
    $.ajax({
        type : "post",
        url : "/manager/article/articleDetail",
        data  :{
            "id" : id
        },
        success : function(result){

            if(result.success){
                var data = result.articleResult;
                var content = data.content;
                articletitle.val(data.title);//回显文章标题
                //回显文章内容
                /*  ue.addListener('ready',function () {
                      ue.execCommand('insertHtml',content);
                  });*/
                ue.ready(function() {
                    ue.setContent(content);
                });
                //回显文章类型
                $('#articletype').find('option[value='+data.type+']').attr('selected',true);
                //回显文章是否评论
                $('#articleevaluate').find('option[value='+data.evaluate+']').attr('selected',true);
                //回显标签
                $("#labelcontent").text("");
                $.each(data.labels,function(i,n){
                    var str = "<button class='alabel'><span class='sellabel' id='" + n.id + "'>" + n.name + "</span><span id='" + n.name + "' class='adel' onclick='adel(this)'>×</span></button>";
                    $("#labelcontent").append(str);
                    $('#labelsel').find('option[value='+n.id+']').remove();
                });
                //回显缩略图
                $("#thumbnail").html("");

                $("#thumbnail").append("<img src='/image/thumbnail/" + data.picture.iconpath+ "' id='" + data.picture.id + "'>");
                //回显分类
                $.each(data.types,function(i,n){
                    $("#typescrollbar").find('input[id='+n.id+']').prop("checked","true");
                });

            }
            else{
                window.location.href="/manager/article/toWriteArticle";
            }
        }

    });

}
//保存文章
function saveATE(status){
    var jsonObj = {};
    //文章标题
    var title = $("#titleofarticle").val();
    //文章内容
    var content = UE.getEditor('myEditor').getContent();
    //文章类型
    var type = $("#articletype").val();
    //是否评论
    var evaluate = $("#articleevaluate").val();
    //缩略图id
    var thumbnailid = $("#thumbnail img").attr("id");
    //标签
    var label = $(".alabel .sellabel");
    //接收多条数据
    $.each(label, function (i, n) {
        jsonObj["labelids[" + i + "]"] = n.id;
    })
    //分类
    var typeids = $("#typescrollbar input:checked");
    $.each(typeids, function (i, n) {
        jsonObj["typeids[" + i + "]"] = n.id;
    })
    if (title == "") {
        layer.msg("文章标题不能为空！", {time: 2000, icon: 5, shift: 6});
        $("#titleofarticle").focus();
        return false;
    }
    if (content == "") {
        layer.msg("文章内容不能为空！", {time: 2000, icon: 5, shift: 6});
        return false;
    }
    jsonObj.title = title;
    jsonObj.content = content;
    jsonObj.status = status;
    jsonObj.type = type;
    jsonObj.evaluate = evaluate;
    jsonObj.pid = thumbnailid;
    var id= GetQueryString("id");
    if(id == null){
        saveArticle(jsonObj);
    }else{
        jsonObj.id = id;
        updateArticle(jsonObj);
    }

}
//执行保存文章的方法
function saveArticle(jsonObj) {
    $.ajax({
        type: "post",
        data: jsonObj,
        dataType: "json",
        url: "/manager/article/saveArticle",
        success: function (result) {
            if (result.success) {
                layer.msg("保存文章成功！", {time: 2000, icon: 6});
                window.location.href="/manager/article/toAll?status="+jsonObj.status;
            } else {
                layer.msg("保存文章失败！", {time: 2000, icon: 5, shift: 6});
            }
        }
    });
}

//异步上传缩略图
function upload() {
    var file = $("#file0")[0].files[0];
    var size = (parseInt(file.size)/1024).toFixed(2);
    var filetype = file.type;
    var options = {
        url: "/manager/article/upfile",
        data : {
            "size" : size,
            "filetype" : filetype
        },
        beforeSubmit: function () {
            loadingIndex = layer.msg('数据正在保存中', {icon: 6});
            return true;
        },
        success: function (result) {
            layer.close(loadingIndex);
            if (result.success) {
                layer.msg("保存缩略图成功", {time: 2000, icon: 6});
            } else {
                layer.msg("保存缩略图失败", {time: 2000, icon: 5, shift: 6});
            }
        }
    };
    //通过jquery的form表单异步提交表单
    $("#uploadfile").ajaxSubmit(options);

}
/*初始化标签*/
function loadLabel() {
    $.ajax({
        type: "post",
        url: "/manager/article/loadLabel",
        data: {},
        success: function (result) {
            if (result.success) {
                var content = "<option>请选择</option>";
                var data = result.ldata;
                $.each(data, function (i, n) {
                    content += "<option value='" + n.id + "'>" + n.name + "</option>";
                });
                $("#labelsel").html(content);
            }
        }

    });
}
/*初始化分类*/
function loadType() {
    $.ajax({
        type: "post",
        url: "/manager/article/loadType",
        data: {},
        success: function (result) {
            if (result.success) {
                var data = result.typeList;
                var content = "<ul>";
                $.each(data, function (i, n) {
                    content += "<li>";
                    content += "<input type='checkbox' id=" + n.id + " name="+n.id+" onclick='sleecttype(this)'>";
                    content += "<label>" + n.name + "</label>";
                    if (n.typeList.length > 0) {
                        content += "<ul>";
                        $.each(n.typeList, function (i, b) {
                            content += "<li>";
                            content += "<input type='checkbox' id=" + b.id + " name="+n.id+" onclick='sleecttype(this)'>";
                            content += "<label>" + b.name + "</label>";
                            content += "</li>";
                        });
                        content += "</ul>";
                    }
                    content += "</li>";
                });
                content += "</ul>";
                $("#typescrollbar").html(content);
                $("#typescrollbar ul li ul").css("margin-left","15px");
                var id= GetQueryString("id");
                if(id != null){
                    echoDisplay(id);
                }
            } else {

            }
        }
    });
}
//加载缩略图
function loadThumbnail() {
    $.ajax({
        type: "post",
        url: "/manager/article/loadThumbnail",
        data: {},
        success: function (result) {
            if (result.success) {
                var data = result.pictureList;
                var content = "";
                $.each(data, function (i, n) {
                    content += "<li>";
                    // content += "<img id='" + n.id + "' src='/javaweb/image/pic/" + n.iconpath + "' width='170px' height='170px' onclick='selectthumbnail(this)'>";
                    content += "<img id='" + n.id + "' src='/image/thumbnail/" + n.iconpath + "' width='170px' height='170px' onclick='selectthumbnail(this)'>";

                    content += "</li>";
                });
                $("#tab1 ul").html(content);
            }
        }

    });
}
//选择标签
$("#labelsel").on("change", function () {
    var text = $("#labelsel option:selected").text();
    var id = $("#labelsel option:selected").val();
    if (text != "请选择") {
        var content = $("#labelcontent").text();
        var str = "<button class='alabel'><span class='sellabel' id='" + id + "'>" + text + "</span><span id='" + text + "' class='adel' onclick='adel(this)'>×</span></button>";
        if ($("#labelcontent").text() == "请选择标签") {
            $("#labelcontent").text("");
        }
        $("#labelcontent").append(str);
        $("#labelsel option:selected").remove();
    }
})
//移除标签
function adel(data) {
    var a = $(data).parents().children(":first").text();
    $(data).parents("button").remove();
    $("#labelsel").append("<option>" + a + "</option>");
}
/*缩略图*/
//显示与隐藏
function showandhidden() {
    if ($('#showthumbnail').is(':hidden')) {
        $('#showthumbnail').show();
    } else {
        $('#showthumbnail').hide();
    }
}
//显示选择的缩略图
function selectthumbnail(obj) {
    $("#showthumbnail").hide();
    $("#thumbnail").html("");
    var src = $(obj).attr("src");
    var id = $(obj).attr("id");
    $("#thumbnail").append("<img src='" + src + "' id='" + id + "'>");
}
//tab页切换
$(function () {
    $('#myTab a:last').tab('show');
})
$('#myTab a').click(function (e) {
    e.preventDefault();
    $(this).tab('show');
})
//缩略图面板的切换
function showtab1() {
    $("#tab1").show();
    $("#showtab1").addClass("showtabstyle");
    $("#showtab2").removeClass("showtabstyle");
    $("#tab2").hide();
}
//缩略图面板的切换
function showtab2() {
    $("#tab2").show();
    $("#showtab2").addClass("showtabstyle")
    $("#showtab1").removeClass("showtabstyle")
    $("#tab1").hide();

}
/*上传图片*/
//图片预览
$("#file0").change(function () {
    var objUrl = getObjectURL(this.files[0]);
    if (objUrl) {
        $("#img0").attr("src", objUrl);
    }
});
//获取图片的url
function getObjectURL(file) {
    var url = null;
    if (window.createObjectURL != undefined) { // basic
        url = window.createObjectURL(file);
    } else if (window.URL != undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file);
    } else if (window.webkitURL != undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file);
    }
    return url;
}

//移除图片
function removeimg() {
    if ($("#img0").attr("src").length == 0) {
        layer.msg("没有图片！", {time: 1000, icon: 5, shift: 6});
    } else {
        $("#img0").attr("src", "");
        $("#file0").val("");
    }
}

//获取请求地址的参数
function GetQueryString(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}
//选择分类
function sleecttype(data){
    if(data.id != data.name){
        if(data.checked==true){
            $("#typescrollbar input").prop("checked",false);
            data.checked=true
            $("#"+data.name).prop("checked",true)
        }else{
            data.checked=false
        }
    }else{
        if(data.checked==true){
            $("#typescrollbar input").prop("checked",false);
            data.checked=true
        }else{
            $("#typescrollbar input").prop("checked",false);
        }
    }
}