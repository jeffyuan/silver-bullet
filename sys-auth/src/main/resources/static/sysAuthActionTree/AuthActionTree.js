/**
 * Created by GESOFT on 2018/3/21.
 */
var AuthActionTree = {
    'url': '/auth/sysauthactiontree/',
    'datas' : {},
};


AuthActionTree.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
AuthActionTree.getHtmlInfo = function(url, params){
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
AuthActionTree.loadData = function(obj, action, curpage) {
    var dialogInfo = AuthActionTree.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>AuthActionTree.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};


/**
 * 设置
 * @param params
 * @param url
 */
AuthActionTree.setNodePosition = function (params, url) {
    var treeNode = [];

    $.ajax({
        type: "post",
        url: url,
        async: false,
        data: params,
        dataType: 'json',
        success: function (data) {
            treeNode.push(data);
        }
    });

    return treeNode;
}



/**
 * 表格头部添加方法
 */
AuthActionTree.add = function() {
    var dialogInfo = AuthActionTree.getHtmlInfo(AuthActionTree.ctxPath + AuthActionTree.url + 'add.html', {});
    data.blur(".wrapper", 1);
    BootstrapDialog.show({
        title: '添加权限',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
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
                AuthActionTree.save(AuthActionTree.ctxPath + AuthActionTree.url + 'save.do', dialogItself);
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
AuthActionTree.save = function(url, dialogItself) {
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
                CmsRepairType.treeNode(TreeView.node);
                window.location.reload();
                /*AuthActionTree.loadData(null, AuthActionTree.ctxPath + AuthActionTree.url + "list.html" ,1);*/
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
AuthActionTree.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    AuthActionTree.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
AuthActionTree.edit = function() {
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

    AuthActionTree.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
AuthActionTree.editCommon = function(uid) {
    var dialogInfo = AuthActionTree.getHtmlInfo(AuthActionTree.ctxPath + AuthActionTree.url + 'edit.html', {"id":uid});
    BootstrapDialog.show({
        title: '修改用户',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
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
                AuthActionTree.save(AuthActionTree.ctxPath + AuthActionTree.url + 'save.do', dialogItself);
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
 * 表格头部，统一删除按钮
 */
AuthActionTree.delete = function() {
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
    AuthActionTree.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
AuthActionTree.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    AuthActionTree.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
AuthActionTree.deleteCommon = function(ids) {
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
                    url: AuthActionTree.ctxPath + AuthActionTree.url + "delete.do",
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

/**
 * 权限定义
 */
AuthActionTree.distribution = function() {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

   /* if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条数据。',
            buttonLabel: "确定"
        });
        return ;
    }*/

    var dialogInfo = AuthActionTree.getHtmlInfo(AuthActionTree.ctxPath + AuthActionTree.url + 'dictitem/list/'+ arrays[0] + '.html', {"curpage" : 1});
    BootstrapDialog.show({
        title: '权限分配',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_WIDE,
        message: "<div id='item-list'>" + dialogInfo + "</div><script>AuthActionTree.ItemCheckboxInit();</script>"
    });
};

AuthActionTree.checkboxInit = function() {
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



AuthActionTree.checkbox = function() {
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




AuthActionTree.getActionSelect = function () {
    $.ajax({
        type: "POST",
        url: AuthActionTree.ctxPath + "auth/sysauthaction/" + "list.do",
        dataType: "json",
        data:{},
        success: function (data) {
            var list = data.list.resultList;
            if (list.length >= 0) {
                var s = "<option value='NONE'>NONE</option>";
                for(var i = 0;i < list.length;i++){
                        s += '<option value='+list[i]['id']+">"+list[i]['name']+'</option>';
                }
                $("#ActionSelect").append(s);
            } else {
                $("#ActionSelect").append(s);
            }
        }
    });
}

/**
 * 下拉框内容插入
 */

AuthActionTree.getActionSelectParent = function () {
    $.ajax({
        type: "POST",
        url: AuthActionTree.ctxPath + AuthActionTree.url + "list.do",
        dataType: "json",
        data:{},
        success: function (data) {
            var list = data.list.resultList;
            if (list.length >= 0) {
                var s = "<option value='NONE'>NONE</option>";
                for(var i = 0;i < list.length;i++){
                    s += '<option value='+list[i]['id']+">"+list[i]['name']+'</option>';
                }
                $("#ActionSelectParent").append(s);
            } else {
                $("#ActionSelectParent").append(s);
            }
        }
    });
}


AuthActionTree.getChildrenTree = function(node){
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
 * 弹出框列表checkbox
 */
AuthActionTree.checkboxStyle = function() {
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
 * 树状图通用初始化
 */

AuthActionTree.TreeNode = function () {
    console.log("2")
    node = TreeView.tree("NONE", "#authActionTree", AuthActionTree.ctxPath+AuthActionTree.url+"tree.do");
    AuthActionTree.node = node;
}


/**
 * 节点鼠标经过事件
 * @type {string}
 */
treeEnter = function(e){
    AuthActionTree.treeEditButton(e);
}

treeClick = function(e){

}


/**
 * 节点编辑浮动菜单
 * @param e
 */
AuthActionTree.treeEditButton = function(e){
    var buttonGroup =
        '<div class=\'NodeEditList icon node-icon animated bounceIn\' data-editId=\'edit\'>' +
            '<span class=\'NodeEditIcon fa fa-arrow-up icon-left\' onclick=\'AuthActionTree.treeNodeMove(this,1)\' data-icon="up"></span>'+
            '<span class=\'NodeEditIcon fa fa-arrow-down icon-right\' onclick=\'AuthActionTree.treeNodeMove(this,0)\' data-icon="down"></span>'+
        '</div>'
    var siblings = $(e).siblings().find("div[data-editId='edit']")
    if(0 ===  siblings.length){
        if($(e).find("div[data-editId='edit']").length === 0){
            $(e).append(buttonGroup);
            AuthActionTree.unClick(e);
        }
    }else{
        siblings.remove();
        if($(e).find("div[data-editId='edit']").length === 0){
            $(e).append(buttonGroup);
            AuthActionTree.unClick(e);
        }
    }
}


/**
 *树节点移动
 * @param e
 */
AuthActionTree.treeNodeMove = function(e, status){

    var $dom = $(e).parent().parent()
    $dom.css({
        'background-color': '#f2f2f2',
        'color': '#333'
    });

    var data = {
        id: $dom.attr("id"),
        parentId: $dom.attr("parentuid"),
        sort: $dom.attr("sort"),
        statu: status
    }

    if(status == 1 && $dom.attr("position") == 1){
        return ;
    }else if(status == 0 && $dom.attr("position") == 0){
        return ;
    }
        else{
        var node = AuthActionTree.setNodePosition(data, AuthActionTree.ctxPath+AuthActionTree.url+"treeNodeMove.do");

        if(true === node[0].status){
            if("NONE" === data.parentId){
                AuthActionTree.TreeNode();
            }else{
                TreeView.treeNode(TreeView.node, "#authActionTree", AuthActionTree.ctxPath+AuthActionTree.url+"tree.do");
            }
        }else{

        }
    }
}

/**
 * 树移动禁止点击
 * @param e
 */
AuthActionTree.unClick = function(e){
    if($(e).attr("position") == 1){
        $(e).find("span[data-icon='up']").addClass("unClick");
    }else if($(e).attr("position") == 0){
        $(e).find("span[data-icon='down']").addClass("unClick");
    }
}



$(function () {
    AuthActionTree.checkboxInit();
    AuthActionTree.TreeNode();
});
