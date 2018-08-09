/**
 * Created by jeffyuan on 2018/3/21.
 */
var CmsRepairService = {
    'url': 'cms/cmsrepairfaultservice/'
};
CmsRepairService.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
CmsRepairService.getHtmlInfo = function(url, params){
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

CmsRepairService.getSearchHtmlInfo = function(obj, url, params){
    var dialogInfo = '';
    $.ajax({
        type: "post",
        url: url,
        async: false,
        data: {'search': obj, 'curpage': params},
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
CmsRepairService.loadData = function(obj, action, curpage) {
    var dialogInfo = CmsRepairService.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairService.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};


CmsRepairService.loadSearchData = function(obj, action, curpage){
    var dialogInfo = CmsRepairService.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairService.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
}


/**
 * 表格头部添加方法
 */
CmsRepairService.add = function() {
    var dialogInfo = CmsRepairService.getHtmlInfo(CmsRepairService.ctxPath + CmsRepairService.url + 'add.html', '');
    BootstrapDialog.show({
        title: '添加维修任务信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_SMALL,
        message: dialogInfo,
        buttons: [{
            icon: 'fa fa-save',
            label: '添加',
            cssClass: 'btn-success',
            action: function (dialogItself) {
                // 清除提示语
                $("label[id^=msg-]").each(function(){
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                CmsRepairService.save(CmsRepairService.ctxPath + CmsRepairService.url + 'save.do', dialogItself);
            }
        }, {
            icon: 'fa fa-close',
            label: '取消',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }
        ]
    });

};

/**
 * 编辑和创建保存方法
 * @param url
 * @param dialogItself
 */
CmsRepairService.save = function(url, dialogItself) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formCmsRepairFaultService").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                BootstrapDialog.alert({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '提示',
                    message: data.message,
                    buttonLabel: "确定"
                });
                dialogItself.close();
                // 刷新页面

                CmsRepairService.loadData(null, CmsRepairService.ctxPath + CmsRepairService.url + 'list.html', 1);
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
CmsRepairService.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairService.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairService.edit = function() {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
        return ;
    }

    CmsRepairService.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
CmsRepairService.editCommon = function(uid) {
    var dialogInfo = CmsRepairService.getHtmlInfo(CmsRepairService.ctxPath + CmsRepairService.url + 'edit.html', {id: uid});
    BootstrapDialog.show({
        title: '编辑客户信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo,
        buttons: [{
            icon: 'fa fa-save',
            label: '保存',
            cssClass: 'btn-success',
            action: function (dialogItself) {
                // 清除提示语
                $("label[id^=msg-]").each(function(){
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                CmsRepairService.save(CmsRepairService.ctxPath + CmsRepairService.url + 'save.do', dialogItself);
            }
        }, {
            icon: 'fa fa-close',
            label: '关闭',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }
        ]
    });
};

/**
 * 表格头部，统一删除按钮
 */
CmsRepairService.delete = function() {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择至少一条需要删除的数据。',
            buttonLabel: "确定"
        });
        return ;
    }
    var ids = arrays.join(",");
    CmsRepairService.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
CmsRepairService.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairService.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
CmsRepairService.deleteCommon = function(ids) {
    BootstrapDialog.confirm({
        title: '删除提示',
        message: '是否确定删除?',
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true,
        btnCancelLabel: '取消',
        btnOKLabel: '删除',
        btnOKClass: 'btn-warning',
        callback: function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: CmsRepairService.ctxPath + CmsRepairService.url + "delete.do",
                    data: { ids: ids },
                    dataType: "json",
                    success: function (data) {
                        if (data.result == true) {
                            BootstrapDialog.alert({
                                type: BootstrapDialog.TYPE_WARNING,
                                title: '提示',
                                message: "删除成功！",
                                buttonLabel: "确定"
                            });
                            // 刷新页面
                            window.location.reload();
                        } else {
                            BootstrapDialog.alert({
                                type: BootstrapDialog.TYPE_WARNING,
                                title: '提示',
                                message: data.message,
                                buttonLabel: "确定"
                            });
                        }
                    }
                });
            }
        }
    });
};

CmsRepairService.checkboxInit = function() {
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
 * 查看通用方法
 * @param uid
 */
CmsRepairService.checkCommon = function(uid){
    var dialogInfo = CmsRepairService.getHtmlInfo(CmsRepairService.ctxPath + CmsRepairService.url + 'check.html', {id: uid});
    BootstrapDialog.show({
        title: '查看客户信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo,
        buttons: [{
            icon: 'fa fa-close',
            label: '关闭',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }]
    });
}


/**
 * 查看一条数据
 * @param obj
 */
CmsRepairService.checkOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairService.checkCommon(uid);
};



/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairService.check = function() {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
        return ;
    }

    CmsRepairService.checkCommon(arrays[0]);
};

/**
 * 获取选中的搜索key
 * @param e
 */
CmsRepairService.selectKey = function(e){
    var selectKey = $(e).attr("value");
}


/**
 * 根据条件查找信息
 */
CmsRepairService.search = function(){
    var searchKey = $("#selectKey").val();
    var searchValue = $("#selectValue").val();
    var search = searchKey+':'+searchValue;


    CmsRepairService.loadSearchData(search, CmsRepairService.ctxPath + CmsRepairService.url + 'search.do', 1);

}

/**
 * 刷新页面
 */
CmsRepairService.refresh = function(){
    CmsRepairService.loadData(null, CmsRepairService.ctxPath + CmsRepairService.url + 'list.html', 1);
}




/**
 * 获取故障名称字段详细信息
 * @param InfoId
 */
CmsRepairService.tableInfoFault = function(InfoId){
    var dialogInfo = CmsRepairService.getHtmlInfo(CmsRepairService.ctxPath + CmsRepairService.url + 'faultInfo.html', {faultId: InfoId});
    BootstrapDialog.show({
        title: '故障详细信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo,
        buttons: [{
            icon: 'fa fa-close',
            label: '关闭',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }]
    });
}



/**
 *获取客户姓名字段详细信息
 * @param InfoId
 */
CmsRepairService.tableInfoUser = function(InfoId){
    var dialogInfo = CmsRepairService.getHtmlInfo(CmsRepairService.ctxPath + CmsRepairService.url + 'userInfo.html', {InfoId: InfoId});
    BootstrapDialog.show({
        title: '客户详细信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo,
        buttons: [{
            icon: 'fa fa-close',
            label: '关闭',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }]
    });
}


/**
 * 查询现有客户
 */
CmsRepairService.selectCustom = function(){
    $.ajax({
        type: "post",
        url: CmsRepairService.ctxPath + CmsRepairService.url + 'userlist.html',
        async: false,
        data: {},
        dataType: 'html',
        success: function (data) {

            dialogInfo = data.replace(/\r|\n/g,"");

            BootstrapDialog.show({
                title: '客户列表',
                type: BootstrapDialog.TYPE_DEFAULT,
                closable: true,
                closeByBackdrop: false,
                closeByKeyboard: false,
                size: BootstrapDialog.SIZE_NORMAL,
                message: dialogInfo,
                buttons: [{
                    icon: 'fa fa-close',
                    label: '关闭',
                    cssClass: 'btn-danger',
                    action: function (dialogItself) {
                        dialogItself.close();
                    }
                }]
            });

        }
    });
}


/**
 * 设置客户名称
 */
CmsRepairService.setCustomName = function(){
    var id = [];
    var val = [];
    $("div[aria-checked='true']").each(function(){
        val.push($(this).parent().parent().find("td[data-name='name']").html());
        id.push($(this).parent().parent().attr("data-u"));
    });

    if (id.length != 1 && val.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
        return ;
    }

    CmsRepairService.setCustomNameCommon(id[0], val);
}


/**
 * 设置客户名称通用方法
 */
CmsRepairService.setCustomNameCommon = function(id, val){
    $("input[name='repairUserId']").attr("value", id);
    $(".customNameValue").html('<span style="color:#333;">'+val+'</span>');

    if($("input[name='repairUserId']").attr("value") != ''){
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '成功',
            message: '修改成功',
            buttonLabel: "确定"
        });
    }
}



/**
 * 查询现有故障
 */
CmsRepairService.selectFault = function(){
    $.ajax({
        type: "post",
        url: CmsRepairService.ctxPath + CmsRepairService.url + 'faultlist.html',
        async: false,
        data: {},
        dataType: 'html',
        success: function (data) {

            dialogInfo = data.replace(/\r|\n/g,"");

            BootstrapDialog.show({
                title: '故障列表',
                type: BootstrapDialog.TYPE_DEFAULT,
                closable: true,
                closeByBackdrop: false,
                closeByKeyboard: false,
                size: BootstrapDialog.SIZE_NORMAL,
                message: dialogInfo,
                buttons: [{
                    icon: 'fa fa-close',
                    label: '关闭',
                    cssClass: 'btn-danger',
                    action: function (dialogItself) {
                        dialogItself.close();
                    }
                }]
            });

        }
    });
}


/**
 * 设置故障名称
 */
CmsRepairService.setFaultName = function(){
    var id = [];
    var val = [];
    $("div[aria-checked='true']").each(function(){
        val.push($(this).parent().parent().find("td[data-name='name']").html());
        id.push($(this).parent().parent().attr("data-u"));
    });

    if (id.length != 1 && val.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
        return ;
    }

    CmsRepairService.setFaultNameCommon(id[0], val);
}


/**
 * 设置故障名称通用方法
 */
CmsRepairService.setFaultNameCommon = function(id,val){
    $("input[name='repairFaultId']").attr("value", id);
    $(".faultNameValue").html('<span style="color:#333;">'+val+'</span>');
    if($("input[name='repairFaultId']").attr("value") != ''){
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '添加成功',
            buttonLabel: "确定"
        });
    }
}


/**
 * 设置任务状态
 * @constructor
 */
CmsRepairService.status = function(){
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
        return ;
    }

    CmsRepairService.statusCommon(arrays[0]);
}


/**
 * 设置任务状态通用方法
 */
CmsRepairService.statusCommon = function(uid){
    var dialogInfo = CmsRepairService.getHtmlInfo(CmsRepairService.ctxPath + CmsRepairService.url + 'status.html', {id: uid});
    BootstrapDialog.show({
        title: '编辑客户信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_SMALL,
        message: dialogInfo,
        buttons: [{
            icon: 'fa fa-save',
            label: '保存',
            cssClass: 'btn-success',
            action: function (dialogItself) {
                // 清除提示语
                $("label[id^=msg-]").each(function(){
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                CmsRepairService.saveStatus(CmsRepairService.ctxPath + CmsRepairService.url + 'setStatus.do', dialogItself);
            }
        }, {
            icon: 'fa fa-close',
            label: '关闭',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }
        ]
    });
}



CmsRepairService.saveStatus = function(url, dialogItself){
    $.ajax({
        type: "post",
        url: url,
        data: $("#formCmsFSStatus").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                BootstrapDialog.alert({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '提示',
                    message: data.message,
                    buttonLabel: "确定"
                });
                dialogItself.close();
                // 刷新页面

                CmsRepairService.loadData(null, CmsRepairService.ctxPath + CmsRepairService.url + 'list.html', 1);
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
}




$(function () {
    CmsRepairService.checkboxInit();
});
