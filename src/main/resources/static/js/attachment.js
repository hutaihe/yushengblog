window.onload = function(){
    loadEnclosure();
}
var jsonboj={};
function loadEnclosure(jsonboj){
    $.ajax({
        type : "post",
        data :jsonboj,
        url : "/manager/enclosure/loadEnclosure",
        success : function(result){
            if(result.success){
                var data = result.pictureList;
                var content = "";
                $.each(data,function(i,n){
                    content += "<li>";
                    content += "<img src='/image/thumbnail/"+n.iconpath+"' onclick='picturedetails("+n.id+")'>";
                    content += "</li>";
                });
                $(".enclosure ul").html(content);
            }
        }
    });
}
$("#crenoticle").click(function(){
    if($("#uploadfile").is(":hidden")){
        $("#uploadfile").show();
    }else{
        $("#uploadfile").hide()
    }
});
$(".off").click(function(){
    $("#uploadfile").hide();
    $("#detailfile").hide();
});
$("#selectbtn").click(function(){
    $("#file0").click();
});
//搜索附件
$("#search").keyup(function(){
    if(event.keyCode ==13){
        var iconpath = $("#search").val();
        jsonboj.iconpath = iconpath;
        loadEnclosure(jsonboj);
    }
});
//点击图片获取详情
function picturedetails(id){
    $("#detailfile").show();
    $.ajax({
        type : "post",
        data :{
            "id" : id
        },
        url : "/manager/enclosure/pictureDetail",
        success : function(result){
            if(result.success){
                var data = result.picture;
                $("#leftdetail img").attr("src","/image/thumbnail/"+data.iconpath);
                $("#pictureid").val(data.id);
                $("#name").val(data.iconpath);
                $("#size").val(data.size);
                $("#filepath").val(data.filepath);
                $("#filetype").val(data.filetype);
                $("#createtime").val(data.createtime);
            }
        }
    });
}
//删除图片
$("#repicutue").click(function(){
    var id = $("#pictureid").val();
    $.ajax({
        type : "post",
        data :{
            "id" : id
        },
        url : "/manager/enclosure/deletePicture",
        success : function(result){
            if (result.success) {
                layer.msg("删除附件成功", {time: 2000, icon: 6});
                loadEnclosure();
                $("#detailfile").hide();
            } else {
                layer.msg("删除附件失败", {time: 2000, icon: 5, shift: 6});
            }
        }
    });
});
/*上传图片*/
//异步上传缩略图
$("#upload").click(function(){
    if($("#file0").val().length == 0){
        layer.msg("请先选择图片", {time: 2000, icon: 5, shift: 6});
        return false;
    }
    else{
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
                    loadEnclosure();
                } else {
                    layer.msg("保存缩略图失败", {time: 2000, icon: 5, shift: 6});
                }
            }
        };
        //通过jquery的form表单异步提交表单
        $("#fileform").ajaxSubmit(options);
    }
});
//图片预览
$("#file0").change(function () {
    var objUrl = getObjectURL(this.files[0]);
    if (objUrl) {
        $("#img0").attr("src", objUrl);
        $("#selecturl").val($("#file0").val());
    }
});
//移除图片
$("#remover").click(function(){
    if ($("#img0").attr("src").length == 0) {
        layer.msg("没有图片！", {time: 1000, icon: 5, shift: 6});
    } else {
        $("#img0").attr("src", "");
        $("#selecturl").val("");
    }
})
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