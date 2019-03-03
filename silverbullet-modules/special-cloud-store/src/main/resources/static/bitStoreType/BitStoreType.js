/**
 * Created by jeffyuan on 2018/3/21.
 */
var Specialcloudstore = {
    'url': 'specialcloudstore/bitstoretype/'
};
Specialcloudstore.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
Specialcloudstore.getHtmlInfo = function(url, params){
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
Specialcloudstore.loadData = function(obj, action, curpage) {
    var dialogInfo = Specialcloudstore.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>Specialcloudstore.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};

/**
 * 表格头部添加方法
 */
Specialcloudstore.add = function() {
    var dialogInfo = Specialcloudstore.getHtmlInfo(Specialcloudstore.ctxPath + Specialcloudstore.url + 'add.html', '');
    layer.open({
        type: 1,
        title: '添加',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: dialogInfo,
        btn: ['确定', '取消']
        ,yes: function(index, layero){
            // 清除提示语
            $("label[id^=msg-]").each(function(){
                $(this).text("");
            });
            $("#msg").text("");

            // 保存
            Specialcloudstore.save(Specialcloudstore.ctxPath + Specialcloudstore.url + 'save.do', index);

            return false;
        }
    });
};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
Specialcloudstore.save = function(url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formSpecialcloudstore").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                layer.msg(data.message);
                // 刷新页面
                Specialcloudstore.loadData(null, Specialcloudstore.ctxPath + Specialcloudstore.url + 'list.html', 1);
                layer.close(layerIndex);
            } else {
                if (data.errors != null) {
                    // 错误信息反馈到页面上
                    for (var i = 0 ; i < data.errors.length; i++) {
                        $("#msg-" + data.errors[i].field).text('  (' + data.errors[i].defaultMessage + ')');
                    }
                } else {
                    $("#msg").text(data.message);
                }
            }
        }
    })
};

/**
 * 表格中编辑按钮
 */
Specialcloudstore.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    Specialcloudstore.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
Specialcloudstore.edit = function() {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条需要修改的数据。');
        return ;
    }

    Specialcloudstore.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
Specialcloudstore.editCommon = function(uid) {
    var dialogInfo = Specialcloudstore.getHtmlInfo(Specialcloudstore.ctxPath + Specialcloudstore.url + 'edit.html',  {id: uid});
    layer.open({
        type: 1,
        title: '编辑字典',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: dialogInfo,
        btn: ['确定', '取消']
        ,yes: function(index, layero){
            // 清除提示语
            $("label[id^=msg-]").each(function(){
                $(this).text("");
            });
            $("#msg").text("");

            // 保存
            Specialcloudstore.save(Specialcloudstore.ctxPath + Specialcloudstore.url + 'save.do', index);

            return false;
        }
    });
};

/**
 * 表格头部，统一删除按钮
 */
Specialcloudstore.delete = function() {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        layer.msg('请选择至少一条需要删除的数据。');
        return ;
    }
    var ids = arrays.join(",");
    Specialcloudstore.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
Specialcloudstore.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    Specialcloudstore.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
Specialcloudstore.deleteCommon = function(ids) {
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: Specialcloudstore.ctxPath + Specialcloudstore.url + "delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");

                    layer.close(index);

                    // 刷新页面
                    window.location.reload();
                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};

Specialcloudstore.checkboxInit = function() {
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

$(function () {
    Specialcloudstore.checkboxInit();
});
