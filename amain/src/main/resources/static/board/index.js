/**
 * Created by OFG on 2018/8/10.
 */

var Board1 = {
    'url': 'amain/board/'
}

Board1.ctxPath = $(".logo").attr('href');


/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
Board1.getHtmlInfo = function(url, params){
    var dialogInfo = '';
    $.ajax({
        type: "post",
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
Board1.loadData = function(obj, action, curpage, dom) {
    var dialogInfo = Board1.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>Board1.checkboxInit();</script>";
    $(dom).html(dialogInfo);
    return true;
};

Board1.checkboxInit = function() {
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

$(function(){
    Board1.loadData(null, Board1.ctxPath + Board1.url + 'list0.html', 1, '#List0');
    Board1.loadData(null, Board1.ctxPath + Board1.url + 'list1.html', 1, '#List1');
    Board1.loadData(null, Board1.ctxPath + Board1.url + 'list2.html', 1, '#List2');
    // Board1.loadData(null, Board1.ctxPath + Board1.url + 'listChar.html', 1, '#ListChar');
});