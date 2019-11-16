window.onload = function(){
    loadData();
}
function loadData(){
    $.ajax({
        type : "post",
        url : "/LoadData",
        data:{},
        success : function(result){
            if(result.success){
                var data = result.dataResult;
                $("#articles").text(data.articles);
                $("#comments").text(data.comments);
                $("#enclosures").text(data.enclosures);
                $("#days").text(data.days);
                var createtime = data.createtime;
                $("#createtime").text(createtime.substr(0,createtime.lastIndexOf(" ")));
            }
        }
    })
}