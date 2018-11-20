/**
 * Created by jeffyuan on 2018/3/21.
 */
var CmsRepairFault = {
    'url': 'cms/cmsrepairfault/'
};
CmsRepairFault.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
CmsRepairFault.getHtmlInfo = function(url, params){
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


CmsRepairFault.getSearchHtmlInfo = function(obj, url, params){
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
CmsRepairFault.loadData = function(obj, action, curpage) {
    var dialogInfo = CmsRepairFault.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairFault.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};


CmsRepairFault.loadSearchData = function(obj, action, curpage){
    var dialogInfo = CmsRepairFault.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairFault.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
}


/**
 * 流程管理model加载页面
 * @param obj
 * @param action url地址
 * @param curpage 当前页
 * @returns {boolean}
 */
CmsRepairFault.loadDataModel = function(obj, action, curpage) {
    var dialogInfo = CmsRepairFault.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairFault.checkboxInit();</script>";
    $("#faultModelList").html(dialogInfo);
    return true;
};



/**
 * 在model内加载条件搜索到的数据
 * @param obj
 * @param action
 * @param curpage
 * @returns {boolean}
 */
CmsRepairFault.loadSearchDataModel = function(obj, action, curpage){
    var dialogInfo = CmsRepairFault.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairFault.checkboxInit();</script>";
    $("#faultModelList").html(dialogInfo);
    return true;
}



/**
 * 表格头部添加方法
 */
CmsRepairFault.add = function() {
    var dialogInfo = CmsRepairFault.getHtmlInfo(CmsRepairFault.ctxPath + CmsRepairFault.url + 'add.html', '');
    dialogInfo += '<script>$(".select2").select2();</script>';
    BootstrapDialog.show({
        title: '添加故障信息',
        type: BootstrapDialog.TYPE_DEFAULT,
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_NORMAL,
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
                CmsRepairFault.save(CmsRepairFault.ctxPath + CmsRepairFault.url + 'save.do', dialogItself);
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
CmsRepairFault.save = function(url, dialogItself) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formCms").serialize(),
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

                CmsRepairFault.loadData(null, CmsRepairFault.ctxPath + CmsRepairFault.url + 'list.html', 1);
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
CmsRepairFault.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairFault.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairFault.edit = function() {
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

    CmsRepairFault.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
CmsRepairFault.editCommon = function(uid) {
    var dialogInfo = CmsRepairFault.getHtmlInfo(CmsRepairFault.ctxPath + CmsRepairFault.url + 'edit.html', {id: uid});
    BootstrapDialog.show({
        title: '编辑故障信息',
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
                CmsRepairFault.save(CmsRepairFault.ctxPath + CmsRepairFault.url + 'save.do', dialogItself);
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
CmsRepairFault.delete = function() {
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
    CmsRepairFault.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
CmsRepairFault.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairFault.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
CmsRepairFault.deleteCommon = function(ids) {
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
                    url: CmsRepairFault.ctxPath + CmsRepairFault.url + "delete.do",
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

CmsRepairFault.checkboxInit = function() {
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
CmsRepairFault.checkCommon = function(uid){
    var dialogInfo = CmsRepairFault.getHtmlInfo(CmsRepairFault.ctxPath + CmsRepairFault.url + 'check.html', {id: uid});
    dialogInfo += '<script>$("#formCmsRepairFault").children().attr("disabled", "disabled")</script>'

    BootstrapDialog.show({
        title: '查看故障信息',
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
CmsRepairFault.checkOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairFault.checkCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairFault.check = function() {
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

    CmsRepairFault.checkCommon(arrays[0]);
};




/**
 * 根据条件查找信息
 */
CmsRepairFault.search = function(dom){
    var searchKey = $(dom).parent().parent().parent().parent().parent().find("select").val();
    var searchValue = $(dom).parent().parent().find("input").val();
    if(typeof(searchValue) == "undefined" ){
        searchValue = $(dom).parent().parent().find("select").val();
    }
    var search = searchKey+':'+searchValue;

    CmsRepairFault.loadSearchData(search, CmsRepairFault.ctxPath + CmsRepairFault.url + 'search.do', 1);

}



/**
 * 流程管理model内容刷新
 */
CmsRepairFault.modelRefresh = function(){
    CmsRepairFault.loadDataModel(null, CmsRepairFault.ctxPath + CmsRepairFault.url + 'listModel.html', 1);
}

/**
 * model内搜索
 */
CmsRepairFault.modelSearch = function(dom){
    var searchKey = $(dom).parent().parent().parent().parent().parent().find("select").val();
    var searchValue = $(dom).parent().parent().find("input").val();
    if(typeof(searchValue) == "undefined" ){
        searchValue = $(dom).parent().parent().find("select").val();
    }
    var search = searchKey+':'+searchValue;


    CmsRepairFault.loadSearchDataModel(search, CmsRepairFault.ctxPath + CmsRepairFault.url + 'searchModel.do', 1);

}




/**
 * 刷新页面
 */
CmsRepairFault.refresh = function(){
    CmsRepairFault.loadData(null, CmsRepairFault.ctxPath + CmsRepairFault.url + 'list.html', 1);
}



$(function () {
    CmsRepairFault.checkboxInit();
});
