/**
 * Created by OFG on 2018/7/31.
 */


var data = {
    screanWidth: 767
}

var className = {
    'BTNICON': '.ofg-btn-icon'
}

var color = {
    'NONE': 'none',
    'GRAY': '#e8e8e8',
    'GRAYH': '#c1bfbf'
}

var con = {
    'refresh': '<span class="iconTag">刷新</span>',
    'root': '<span class="iconTag">根目录</span>',
    'openAll': '<span class="iconTag">展开全部</span>',
    'closeAll': '<span class="iconTag">编辑树</span>'
}
var conIcon = {
    'refresh': '<i class="fa fa-refresh"></i>',
    'root': '<i class="fa fa-home"></i>',
    'openAll': '<i class="fa fa-folder-open"></i>',
    'closeAll': '<i class="fa fa-edit"></i>'
}

/**
 * 浏览设备判断
 * @returns {boolean}
 */
data.device = function(){
    var userAgentInfo = navigator.userAgent;

    var mobileAgents = [ "Android", "iPhone", "SymbianOS", "Windows Phone", "iPad","iPod"];

    var mobile_flag = false;

    for (var v = 0; v < mobileAgents.length; v++) {
        if (userAgentInfo.indexOf(mobileAgents[v]) > 0) {
            mobile_flag = true;
            break;
        }
    }
    return mobile_flag;
}



/**
 *
 */
data.btnIcon = function(){
    if(data.device() == false){
        $(className.BTNICON).hover(
            function(){
                $(this).html(con[$(this).attr("data-con")]);
            },
            function(){
                $(this).html(conIcon[$(this).attr("data-con")]);
            }
        );
    }

}



data.icon = function(){
    var widthModule = $(".iconModule")[0].offsetWidth;
    var widthBorder = $(".iconBorder")[0].offsetWidth*0.5;

    $(".iconModule").css({'height': widthModule});
    $(".iconBorder").css({'height': widthBorder});

}

data.iconChange = function (e) {
    var icon = $(e).find("i").attr("class");
    var icon_input = $(e).parent().parent().find("div[class='form-group']").find("div").find("div").find("input");
    var icon_i = $(e).parent().parent().find("div[class='form-group']").find("div").find("div[class='icon-i']").find("i");

    icon_input.val(icon);
    icon_i.attr('class', '').addClass(icon);

}

/**
 * 浏览设备屏幕尺寸判断   `
 * @param width
 * @constructor
 */
data.ActionBar = function(width){
    // var nowWidth = $(window).width();
    // if(nowWidth > width){
    //     $(".logo").remove();
    // }
    // console.log(nowWidth+"233");
}


/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
getHtmlInfo = function(url, params){
    var dialogInfo = '';
    $.ajax({
        type: "get",
        url: url,
        async: false,
        data: params,
        dataType: 'html',
        success: function (data) {
            dialogInfo = data.replace(/\r|\n/g,"");
        }
    });

    return dialogInfo;
};


/**
 * 加载页面
 * @param obj
 * @param action url地址
 * @param curpage 当前页
 * @returns {boolean}
 */
loadData = function(obj, action, curpage) {
    var dialogInfo = getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>checkboxInit();</script>";
    $("#content_async").html(dialogInfo);
    return true;
};


/**
 * 初始化checkBox
 */
checkboxInit = function() {
    $('#data-list input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
    });

    //Enable check and uncheck all functionality
    $(".checkbox-toggle").click(function () {
        var clicks = $(this).data('clicks');
        if (clicks) {
            //Uncheck all checkboxes
            $("#data-list input[type='checkbox']").iCheck("uncheck");
            $(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
        } else {
            //Check all checkboxes
            $("#data-list input[type='checkbox']").iCheck("check");
            $(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
        }
        $(this).data("clicks", !clicks);
    });
};



/**
 * 获取当前屏幕尺寸
 */
data.getWindowSize = function() {
    var map = new Map();

    map.set("screenHeight", $(window).height());
    map.set("screenWidth", $(window).width());

    return map;

}




/**
 * 宽屏添加功能按钮文字描述
 * @param screenSize
 */
data.buttonTips = function(screenSize) {
    if(767 <= screenSize.get("screenWidth")){
        $(".btn-group button").each(function(k,v) {
            $(this).append($(this).attr("name"));
        });
    }
}

/**
 * 控制台移动端toolBar样式
 */
data.phoneToolBar = function (screen) {
    if(767 >= screen.get("screenWidth")){
        $(".content-wrapper").append($("#toolBar").prop("outerHTML"));
        $("#toolBar").remove();
    }
}


/**
 * content模糊
 * @param dom
 */
data.blur = function(dom, status){
    status == 1?
        $(dom).css({
            "filter": "blur(5px)"
        }):
        $(dom).css({
            "filter": "none"
        })

}




$(function(){
    data.btnIcon();
    data.ActionBar(data.screanWidth);
    // data.buttonTips(data.getWindowSize());
    data.phoneToolBar(data.getWindowSize());
})