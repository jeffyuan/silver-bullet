/**
 * Created by jeffyuan on 2018/3/21.
 */
var CmsRepairUser = {
    'url': 'cms/cmsrepairuser/'
};
CmsRepairUser.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
CmsRepairUser.getHtmlInfo = function(url, params){
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

CmsRepairUser.getSearchHtmlInfo = function(obj, url, params){
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
CmsRepairUser.loadData = function(obj, action, curpage) {
    var dialogInfo = CmsRepairUser.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairUser.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};


CmsRepairUser.loadSearchData = function(obj, action, curpage){
    var dialogInfo = CmsRepairUser.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairUser.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
}

CmsRepairUser.loadSearchDataModel = function(obj, action, curpage){
    var dialogInfo = CmsRepairUser.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairUser.checkboxInit();</script>";
    $("#userModelList").html(dialogInfo);
    return true;
}


/**
 * 流程管理model加载页面
 * @param obj
 * @param action url地址
 * @param curpage 当前页
 * @returns {boolean}
 */
CmsRepairUser.loadDataModel = function(obj, action, curpage) {
    var dialogInfo = CmsRepairUser.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairUser.checkboxInit();</script>";
    $("#userModelList").html(dialogInfo);
    return true;
};



/**
 * 表格头部添加方法
 */
CmsRepairUser.add = function() {
    var dialogInfo = CmsRepairUser.getHtmlInfo(CmsRepairUser.ctxPath + CmsRepairUser.url + 'add.html', '');
    BootstrapDialog.show({
        title: '添加客户信息',
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
                CmsRepairUser.save(CmsRepairUser.ctxPath + CmsRepairUser.url + 'save.do', dialogItself);
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
CmsRepairUser.save = function(url, dialogItself) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formCmsRepairUser").serialize(),
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

                CmsRepairUser.loadData(null, CmsRepairUser.ctxPath + CmsRepairUser.url + 'list.html', 1);
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
CmsRepairUser.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairUser.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairUser.edit = function() {
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

    CmsRepairUser.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
CmsRepairUser.editCommon = function(uid) {
    var dialogInfo = CmsRepairUser.getHtmlInfo(CmsRepairUser.ctxPath + CmsRepairUser.url + 'edit.html', {id: uid});
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
                CmsRepairUser.save(CmsRepairUser.ctxPath + CmsRepairUser.url + 'save.do', dialogItself);
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
CmsRepairUser.delete = function() {
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
    CmsRepairUser.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
CmsRepairUser.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairUser.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
CmsRepairUser.deleteCommon = function(ids) {
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
                    url: CmsRepairUser.ctxPath + CmsRepairUser.url + "delete.do",
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

CmsRepairUser.checkboxInit = function() {
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
CmsRepairUser.checkCommon = function(uid){
    var dialogInfo = CmsRepairUser.getHtmlInfo(CmsRepairUser.ctxPath + CmsRepairUser.url + 'check.html', {id: uid});
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
CmsRepairUser.checkOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairUser.checkCommon(uid);
};



/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairUser.check = function() {
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

    CmsRepairUser.checkCommon(arrays[0]);
};

/**
 * 获取选中的搜索key
 * @param e
 */
CmsRepairUser.selectKey = function(e){
    var selectKey = $(e).attr("value");
}


/**
 * 根据条件查找信息
 */
CmsRepairUser.search = function(dom){
    var searchKey = $(dom).parent().parent().parent().parent().parent().find("select").val();
    var searchValue = $(dom).parent().parent().find("input").val();
    console.log(searchValue);
    if(typeof(searchValue) == "undefined" ){
        searchValue = $(dom).parent().parent().find("select").val();
        console.log(searchValue);
    }
    var search = searchKey+':'+searchValue;

    CmsRepairUser.loadSearchData(search, CmsRepairUser.ctxPath + CmsRepairUser.url + 'search.do', 1);

}



/**
 * 刷新页面
 */
CmsRepairUser.refresh = function(){
    CmsRepairUser.loadData(null, CmsRepairUser.ctxPath + CmsRepairUser.url + 'list.html', 1);
}


/**
 * 流程管理model内容刷新
 */
CmsRepairUser.modelRefresh = function(){
    CmsRepairUser.loadDataModel(null, CmsRepairUser.ctxPath + CmsRepairUser.url + 'listModel.html', 1);
}





/**
 * model内根据条件查找信息
 */
CmsRepairUser.modelSearch = function(dom){
    var searchKey = $(dom).parent().parent().parent().parent().parent().find("select").val();
    var searchValue = $(dom).parent().parent().find("input").val();
    console.log(searchValue);
    if(typeof(searchValue) == "undefined" ){
        searchValue = $(dom).parent().parent().find("select").val();
    }
    var search = searchKey+':'+searchValue;


    CmsRepairUser.loadSearchDataModel(search, CmsRepairUser.ctxPath + CmsRepairUser.url + 'searchModel.do', 1);

}




CmsRepairUser.loadSearchData
$(function () {
    CmsRepairUser.checkboxInit();
});
