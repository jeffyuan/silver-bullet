/**
 * Created by GESOFT on 2018/3/21.
 */
var AuthOrganization = {
    'url': 'auth/sysauthorganization/',
    'datas' : {},
};
AuthOrganization.ctxPath = $(".logo").attr('href');
AuthOrganization.status = 0;

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
AuthOrganization.getHtmlInfo = function(url, params){
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

AuthOrganization.getHtml = function(url, params){
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
AuthOrganization.loadData = function(obj, action, curpage) {
    var dialogInfo = AuthOrganization.getHtml(action, {"curpage" : curpage});
    dialogInfo += "<script>AuthOrganization.checkboxInit();</script>";
    $("#data-list").html(dialogInfo);
    return true;
};


/**
 * 加载页面通用方法
 * @param obj
 * @param action
 * @param value
 * @param dom
 * @returns {boolean}
 */
AuthOrganization.loadDataCommon = function(obj, action, value, dom){

    var val = {
        curpage: value,
        parentId: ""
    };
    typeof(value[1]) == "undefined" ? val.parentId = AuthOrganization.parentId : val.parentId="" ;

    var dialogInfo = AuthOrganization.getHtmlInfo(action, val);
    dialogInfo += "<script>AuthOrganization.checkboxInit();</script>";
    $(dom).html(dialogInfo);
    return true;
}

/**
 * 表头添加方法
 * */
AuthOrganization.add = function() {

    var clickNode = $('#authOrganizationTree').treeview('getSelected');

    if(clickNode.length === 0){
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一个组织',
            buttonLabel: "确定"
        });
    } else{
        AuthOrganization.addCommon(clickNode[0].id);
    }

}


/**
 * 表格头部添加通用方法
 */
AuthOrganization.addCommon = function(uid) {
    var parentId = AuthOrganization.getParentId();
    var dialogInfo = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + AuthOrganization.url + 'add.html', {parentId:  uid});

    BootstrapDialog.show({
        title: '添加部门',
        closable: true,
        type: BootstrapDialog.TYPE_DEFAULT,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo ,
        buttons: [{
            label: '确定',
            icon: 'fa fa-save',
            cssClass: 'btn-success',
            action: function (dialogItself) {
                // 清除提示语
                $("label[id^=msg-]").each(function(){
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                AuthOrganization.save(AuthOrganization.ctxPath + AuthOrganization.url + 'save.do', dialogItself, parentId);
            }
        }, {
            label: '关闭',
            icon:'fa fa-close',
            cssClass: 'btn-danger',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }
        ]
    });
};


AuthOrganization.addModel = function() {
    var dialogInfo = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + AuthOrganization.url + 'add.html', {});
    BootstrapDialog.show({
        title: '添加部门',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        type: BootstrapDialog.TYPE_DEFAULT,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo,
        buttons: [{
            label: '确定',
            action: function (dialogItself) {
                // 清除提示语
                $("label[id^=msg-]").each(function(){
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                AuthOrganization.save(AuthOrganization.ctxPath + AuthOrganization.url + 'save.do', dialogItself);
            }
        }, {
            label: '关闭',
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
AuthOrganization.save = function(url, dialogItself, parentId) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
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
                var con = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + AuthOrganization.url + 'list.html', {parentId: parentId})
                con += "<script>AuthOrganization.checkboxInit();</script>"
                $("#data-list-content-list").empty().append(con);
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
AuthOrganization.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    AuthOrganization.editCommon(uid);

};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
AuthOrganization.edit = function() {
    var arrays = [];
    $("#data-list div[class='icheckbox_flat-blue checked']").each(function(){
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

    AuthOrganization.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
AuthOrganization.editCommon = function(uid) {
    var parentId = AuthOrganization.getParentId();
    var dialogInfo = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + AuthOrganization.url + 'edit.html', {"id":uid});
    BootstrapDialog.show({
        title: '修改部门',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        type: BootstrapDialog.TYPE_DEFAULT,
        size: BootstrapDialog.SIZE_NORMAL,
        message: dialogInfo,
        buttons: [{
            label: '确定',
            icon: 'fa fa-save',
            cssClass: 'btn-success',
            action: function (dialogItself) {
                // 清除提示语
                $("label[id^=msg-]").each(function(){
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                AuthOrganization.save(AuthOrganization.ctxPath + AuthOrganization.url + 'save.do', dialogItself, parentId);
            }
        }, {
            label: '关闭',
            icon: 'fa fa',
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
AuthOrganization.delete = function() {
    var arrays = [];
    $("#data-list div[class='icheckbox_flat-blue checked']").each(function(){
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
    AuthOrganization.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
AuthOrganization.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    AuthOrganization.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
AuthOrganization.deleteCommon = function(ids) {
    var parentId = AuthOrganization.getParentId();

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
                    url: AuthOrganization.ctxPath + AuthOrganization.url + "delete.do",
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
                            var con = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + AuthOrganization.url + 'list.html', {parentId: parentId})
                            con += "<script>AuthOrganization.checkboxInit();</script>"
                            $("#data-list-content-list").empty().append(con);
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

/**
 * 设置权限项
 */
AuthOrganization.setPost = function() {
    var arrays = [];
    var treeUrl = '/a/static/sysAuthActionTree/AuthActionTree.js';
    var style = '.modal-body{background: #FFF!important;  }.nav-tabs-custom>.nav-tabs>li.active{border-top-color:#222d32;}';
    $("#data-list div[class='icheckbox_flat-blue checked']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });


    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条数据。',
            buttonLabel: "确定"
        });
        return ;
    }

    var dialogInfo = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + 'auth/sysauthpost/' + 'dictitem/list/'+ arrays[0] + '.html', {"curpage" : 1});
    dialogInfo = "<div id='item-list'>" + dialogInfo + "</div>"

    BootstrapDialog.show({
        type: BootstrapDialog.TYPE_DEFAULT,
        title: '设置岗位',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_WIDE,
        message: dialogInfo
    });
};

AuthOrganization.checkboxInit = function() {
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


/**
 * 表格头部权限配置，只能配置一条记录
 */
AuthOrganization.check = function() {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function(){
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
    AuthOrganization.checkPermission(arrays[0]);
};



AuthOrganization.getChildrenTree = function(node){
    var ts = [];
    if(node.nodes){
        for(x in node.nodes){
            ts.push(node.nodes[x].nodeId)
            if(node.nodes[x].nodes){
                var getNodeDieDai = getNodeIdArr(node.nodes[x]);
                for(j in getNodeDieDai){
                    ts.push(getNodeDieDai[j]);
                }
            }
        }
    }else{
        ts.push(node.nodeId);
    }
    return ts;
}



/**
 * 树状图通用初始化
 */

AuthOrganization.TreeNode = function (e) {

    var node = $(e).bsTableBuild({
        uid: 'NONE',
        url: AuthOrganization.ctxPath + AuthOrganization.url + "tree.do"
    });

    AuthOrganization.node = node;
}



/**
 * table定位到当前选择list部门下
 */
function treeClick(e){
    AuthOrganization.status == 0?AuthOrganization.local(e): e;
}

function treeEnter(e){

}

function treeLeave(e){

}


AuthOrganization.local = function(e){
    parentId = $(e).attr("id");
    AuthOrganization.parentId = parentId;

    $.ajax({
        type: 'get',
        url: AuthOrganization.ctxPath + AuthOrganization.url + "local/"+1+".html",
        data: {parentId: parentId},
        dataType: "HTML",
        success: function(data){
            data += "<script>AuthOrganization.checkboxInit();</script>";
            $(".box-footer").remove();
            $("#data-list-content-list").css("display","none").html(data).fadeIn(200);
        }
    });
}


/**
 * 查看状态方法
 */
AuthOrganization.checkStatus = function(){
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });
    $("#uid").attr("value", arrays[0]);
}


AuthOrganization.checkCommon = function(){
    var uid = $("#uid").attr("value");
    var info = AuthOrganization.getHtmlInfo(AuthOrganization.ctxPath + AuthOrganization.url + 'edit.html', {"id":uid});
    $("#tab_3>.row").html(info);

}


/**
 * 获取parentId
 */
AuthOrganization.getParentId = function(){
    var parentIds = $("#authOrganizationTree").treeview("getSelected");
    parentId = parentIds.length === 0 ? "" : parentIds[0].id
    return parentId;
}




$(function () {
    AuthOrganization.checkboxInit();
});
