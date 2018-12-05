/**
 * Created by GESOFT on 2018/3/9.
 */
var g_rsa_pubkey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCe/IKom0+YYH/8gW4cDlkwsusFZWF8Z4HR2fG6t+YGyMh3YjKAS/nrCPU3YXG1kGl8uxnA7mEopSf9YiWZNOXmk0VtFx0eO3i6N/hzAMpEmjXdZYd1evEglbAvRH0nTVPhndSYEzECaq1O0HLodHadCkv1bODv4KLVxgOhXV8tEwIDAQAB";

// loadMenu();
jQuery(document).ready(function(){
   loadMenu();

    // 初始化消息队列
    // initMsg();
});

function loadMenu() {

    var ctxPath = $(".logo").attr('href');
    var obj = $.ajax({
        type:'post',
        url: ctxPath + "getmenus.do",
        async:false,
        dataType:"html",
        beforeSend: beforeSend,
        cache: false,
        success: function (data) {
            // $("#loading").remove();
            $("#menus").empty();
            $("#menus").html(data);

            $('#menus').tree({
                animationSpeed: 500,
                accordion     : true,
                followLink    : false,
                trigger       : '.treeview a'
            });
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

/**
 * 初始化消息
 */
function initMsg() {
    var ctxPath = $(".logo").attr('href');
    var obj = $.ajax({
        type:'post',
        url: ctxPath + "getmsginfo.do",
        async:true,
        dataType:"json",
        beforeSend: beforeSend,
        success: function (data) {
            msgInit('sysmsg', data.clientid, data.topic, msgHandler.rcvMessage);
        },
        complete: complete,
        error: error,
    });
}

var msgHandler ={
    rcvMessage: function(message){
        //接收到消息后，自己的业务处理逻辑
        alert(message);
    }
};