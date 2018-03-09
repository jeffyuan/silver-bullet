/**
 * Created by GESOFT on 2018/3/9.
 */

jQuery(document).ready(function(){
    loadMenu();
})

function loadMenu() {

    var ctxPath = $(".logo").attr('href');

    var obj = $.ajax({
        type:'post',
        url: ctxPath + "/getmenus",
        async:false,
        dataType:"html",
        beforeSend: beforeSend,
        success: function (data) {
            // $("#loading").remove();
            $("#menus").empty();
            $("#menus").html(data);
        },
        complete: complete,
        error: error,
    });
}

function error(XMLHttpRequest, textStatus, errorThrown){
    // 通常情况下textStatus和errorThown只有其中一个有值
    $("#loading").remove();
    $("#showResult").append("<div>请求出错啦！</div>");
}
function beforeSend(XMLHttpRequest){
    $("#loading").html('<div><img src="image/jbox-content-loading.gif"/></div>');
}
function complete(XMLHttpRequest, textStatus){

}