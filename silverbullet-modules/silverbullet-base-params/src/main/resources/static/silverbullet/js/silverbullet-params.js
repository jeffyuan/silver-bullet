/**
 * Created by GESOFT on 2018/3/21.
 */
var Params = {
    'url': 'params/sysparamsdictionary/',
    'datas': {},
};
Params.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
Params.getHtmlInfo = function (url, params) {
    var dialogInfo = '';
    $.ajax({
        type: "post",
        url: url,
        async: false,
        data: params,
        dataType: 'html',
        success: function (data) {
            dialogInfo = data.replace(/\r|\n/g, "");
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
Params.loadData = function (obj, action, curpage) {
    var dialogInfo = Params.getHtmlInfo(action, {"curpage": curpage});
    dialogInfo += "<script>Params.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};

/**
 * 查看字典
 */
Params.check = function () {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });
    if (arrays.length != 1) {
        layer.msg('请选择一条需要查看的数据。');
        return ;
    }
    Params.checkCommon(arrays[0]);
    console.log(arrays)
};

/**
 * 查看通用方法
 * @param uid
 */
Params.checkCommon = function(uid){
    var dialogInfo = Params.getHtmlInfo(Params.ctxPath + Params.url + 'check.html', {id: uid});
    layer.open({
        type: 1,
        title: '查看字典信息',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: dialogInfo
    });
};

/**
 * 表格头部添加方法
 */
Params.add = function () {
    var dialogInfo = Params.getHtmlInfo(Params.ctxPath + Params.url + 'add.html', {});
    layer.open({
        type: 1,
        title: '添加字典',
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
            Params.save(Params.ctxPath + Params.url + 'save.do', index);

            return false;
        }
    });
};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
Params.save = function (url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function (data) {
            if (data.result == true) {
                layer.msg( data.message);

                layer.close(layerIndex);
                // 刷新页面
                window.location.reload();
            } else {
                if (data.errors != null) {
                    // 错误信息反馈到页面上
                    for (var i = 0; i < data.errors.length; i++) {
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
Params.editOne = function (obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    Params.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
Params.edit = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg("请选择一条需要修改的数据。");
        return;
    }

    Params.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
Params.editCommon = function (uid) {
    var dialogInfo = Params.getHtmlInfo(Params.ctxPath + Params.url + 'edit.html', {"id": uid});
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
            Params.save(Params.ctxPath + Params.url + 'save.do', index);

            return false;
        }
    });
};

/**
 * 表格头部，统一删除按钮
 */
Params.delete = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        layer.msg('请选择至少一条需要删除的数据。');
        return;
    }
    var ids = arrays.join(",");
    Params.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
Params.deleteOne = function (obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    Params.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
Params.deleteCommon = function (ids) {
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: Params.ctxPath + Params.url + "delete.do",
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

/**
 * 设置字典项
 */
Params.setDictItem = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条数据。');
        return;
    }

    var dialogInfo = Params.getHtmlInfo(Params.ctxPath + Params.url + 'dictitem/list/' + arrays[0] + '.html', {"curpage": 1});
    layer.open({
        type: 1,
        title: '设置字典项',
        maxmin: false,
        area: '600px',
        shade: 0.3,
        shadeClose: false,
        content: "<div id='item-list'>" + dialogInfo + "</div><script>Params.ItemCheckboxInit();</script>"
    });
};

Params.checkboxInit = function () {
    $('#data-list-content input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
    });

    //Enable check and uncheck all functionality
    $("#data-list-content .checkbox-toggle").click(function () {
        var clicks = $(this).data('clicks');
        if (clicks) {
            //Uncheck all checkboxes
            $("#data-list-content input[type='checkbox']").iCheck("uncheck");
            $(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
        } else {
            //Check all checkboxes
            $("#data-list-content input[type='checkbox']").iCheck("check");
            $(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
        }
        $(this).data("clicks", !clicks);
    });
};

$(function () {
    Params.checkboxInit();
});

/////////////////////////paramsItem/////////////////////////////////////////////
/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
Params.editItem = function () {
    var arrays = [];
    $("#data-list-item div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条需要修改的数据。');
        return;
    }

    Params.editCommonItem(arrays[0]);
};

/**
 * 表格头部添加方法
 */
Params.addItem = function (dicKeyId) {
    var dialogInfo = Params.getHtmlInfo(Params.ctxPath + Params.url + '/dictitem/add.html', {"dicKeyId": dicKeyId});
    layer.open({
        type: 1,
        title: '添加字典项',
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
            Params.saveItem(Params.ctxPath + Params.url + '/dictitem/save.do', index);

            return false;
        }
    });
};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
Params.saveItem = function (url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParamsItem").serialize(),
        dataType: "json",
        success: function (data) {
            if (data.result == true) {
                layer.msg(data.message);
                // 刷新页面
                Params.loadItemData(null, Params.ctxPath + Params.url + 'dictitem/list/' + data.dicKeyId + '.html', 1);
                layer.close(layerIndex);
            } else {
                if (data.errors != null) {
                    // 错误信息反馈到页面上
                    for (var i = 0; i < data.errors.length; i++) {
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
Params.editOneItem = function (obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    Params.editCommonItem(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
Params.editItem = function () {
    var arrays = [];
    $("#data-list-item div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条需要修改的数据。');
        return;
    }

    Params.editCommonItem(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
Params.editCommonItem = function (uid) {
    var dialogInfo = Params.getHtmlInfo(Params.ctxPath + Params.url + '/dictitem/edit.html', {"id": uid});
    layer.open({
        type: 1,
        title: '编辑字典项',
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
            Params.saveItem(Params.ctxPath + Params.url + '/dictitem/save.do', index);

            return false;
        }
    });
};

/**
 * 表格头部，统一删除按钮
 */
Params.deleteItem = function () {
    var arrays = [];
    $("#data-list-item div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        layer.msg('请选择至少一条需要删除的数据。');
        return;
    }
    var ids = arrays.join(",");
    Params.deleteCommonItem(ids);
};

/**
 * 表格中删除一条数据
 */
Params.deleteOneItem = function (obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    Params.deleteCommonItem(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
Params.deleteCommonItem = function (ids) {
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: Params.ctxPath + Params.url + "/dictitem/delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");

                    // 刷新页面
                    Params.loadItemData(null, Params.ctxPath + Params.url + 'dictitem/list/' + data.dicKeyId + '.html', 1);

                    layer.close(index);
                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};

/**
 * 初始化chckbox，注意限定某个对象下的checkbox 例如 item-list
 * @constructor
 */
Params.ItemCheckboxInit = function () {
    $('#item-list input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
    });

    //Enable check and uncheck all functionality
    $("#item-list .checkbox-toggle").click(function () {
        var clicks = $(this).data('clicks');
        if (clicks) {
            //Uncheck all checkboxes
            $("#item-list input[type='checkbox']").iCheck("uncheck");
            $(".fa", this).removeClass("fa-check-square-o").addClass('fa-square-o');
        } else {
            //Check all checkboxes
            $("#item-list input[type='checkbox']").iCheck("check");
            $(".fa", this).removeClass("fa-square-o").addClass('fa-check-square-o');
        }
        $(this).data("clicks", !clicks);
    });
};

/**
 * 加载页面
 * @param obj
 * @param action url地址
 * @param curpage 当前页
 * @returns {boolean}
 */
Params.loadItemData = function (obj, action, curpage) {
    var dialogInfo = Params.getHtmlInfo(action, {"curpage": curpage});
    dialogInfo += "<script>Params.ItemCheckboxInit();</script>";
    $("#item-list").html(dialogInfo);
    return true;
}