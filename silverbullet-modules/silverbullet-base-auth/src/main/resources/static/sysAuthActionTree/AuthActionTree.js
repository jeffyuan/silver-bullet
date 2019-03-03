/**
 * Created by GESOFT on 2018/3/21.
 */
var AuthActionTree = {
    'url': 'auth/sysauthactiontree/',
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
    // 获取选中的tree节点ID
    var selectNodeId = null;
    var selectNode = null;
    if (obj == null) {
        selectNode = $(".node-selected");
    } else {
        selectNode = $(obj);
    }

    if (selectNode != null) {
        selectNodeId = selectNode.attr("id");
    }

    var dialogInfo = AuthActionTree.getHtmlInfo(action, {"curpage" : curpage, parentId: selectNodeId});
    dialogInfo += "<script>AuthActionTree.checkboxInit();</script>";
    $("#data-list-content-list").html(dialogInfo);
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
AuthActionTree.loadDataCommon = function(obj, action, value, dom){

    var val = {
        curpage: value,
        parentId: ""
    };

    var dialogInfo = AuthActionTree.getHtmlInfo(action, val);
    dialogInfo += "<script>AuthActionTree.checkboxInit();</script>";
    $(dom).html(dialogInfo);
    return true;
}


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
};

/**
 * 表格头部添加方法
 */
AuthActionTree.add = function() {
    var dialogInfo = AuthActionTree.getHtmlInfo(AuthActionTree.ctxPath + AuthActionTree.url + 'add.html', {});

    layer.open({
        type: 1,
        title: '添加权限',
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
            AuthActionTree.save(AuthActionTree.ctxPath + AuthActionTree.url + 'save.do', index);
        }
    });
};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
AuthActionTree.save = function(url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                // 刷新页面
                AuthActionTree.loadDataCommon(null, AuthActionTree.ctxPath + AuthActionTree.url + "list.html" ,1, "#data-list-content-list");
                layer.msg(data.message);
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
        layer.msg('请选择一条需要修改的数据。');
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
    layer.open({
        type: 1,
        title: '修改菜单',
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
            AuthActionTree.save(AuthActionTree.ctxPath + AuthActionTree.url + 'save.do', index);

            return false;
        }
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
        layer.msg('请选择至少一条需要删除的数据。');
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

    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: AuthActionTree.ctxPath + AuthActionTree.url + "delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");
                    // 刷新页面
                    AuthActionTree.loadDataCommon(null, AuthActionTree.ctxPath + AuthActionTree.url + "list.html" ,1, "#data-list-content-list");
                    layer.close(index);
                } else {
                    layer.msg(data.message);
                }
            }
        });
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
    layer.open({
        type: 1,
        title: '权限分配',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: "<div id='item-list'>" + dialogInfo + "</div><script>AuthActionTree.ItemCheckboxInit()</script>",
        btn: ['确定', '取消']
        ,yes: function(index, layero){
            // 清除提示语
            $("label[id^=msg-]").each(function(){
                $(this).text("");
            });
            $("#msg").text("");

            // 保存
            AuthActionTree.save(AuthActionTree.ctxPath + AuthActionTree.url + 'save.do', index);

            return false;
        }
    });
};

AuthActionTree.checkboxInit = function() {
    $('#data-list input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
    });

    //Enable check and uncheck all functionality
    $("#data-list .checkbox-toggle").click(function () {
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



AuthActionTree.checkbox = function() {
    $('#data-list input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
    });

    //Enable check and uncheck all functionality
    $("#data-list .checkbox-toggle").click(function () {
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
AuthActionTree.TreeNode = function (e) {
    var node = $(e).bsTableBuild({
        uid: 'NONE',
        url: AuthActionTree.ctxPath + AuthActionTree.url + "tree.do",
        selectedBackColor: "#ddd",
        selectedColor: '#333'
    });

    AuthActionTree.node = node;
};


/**
 * 节点鼠标经过事件
 * @type {string}
 */
treeEnter = function(e){
    AuthActionTree.treeEditButton(e);
};

treeClick = function(e){
    AuthActionTree.loadData(e, AuthActionTree.ctxPath + AuthActionTree.url + "list.html" ,1);
};


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
    // 当前的item
    var treeCurentItem = $(e).parent().parent();

    // 获取
    if((status == 1 && treeCurentItem.attr("position") == 1) ||
        (status == 0 && treeCurentItem.attr("position") == 0) ){
        return ;
    }

    treeCurentItem.css({
        'background-color': '#f2f2f2',
        'color': '#333'
    });

    var brotherTreeItem = null;
    if (status == 1) { // 获取上一个兄弟节点
        brotherTreeItem = treeCurentItem.prev();
    } else if (status == 0) {  //获取下一个兄弟节点
        brotherTreeItem = treeCurentItem.next();
    }

    if (brotherTreeItem == null){
        return ;
    }

    var data = {
        currentId: treeCurentItem.attr("id"),
        brotherId: brotherTreeItem.attr("id")
    };

    $.ajax({
        type: "post",
        url:  AuthActionTree.ctxPath + AuthActionTree.url + "treeNodeMove.do",
        async: false,
        data: data,
        dataType: 'json',
        success: function (data) {
            // 成功后，将两个节点进行换
            if(data.status == true) {
                if (status == 1) {
                    brotherTreeItem.remove();
                    treeCurentItem.after(brotherTreeItem);
                } else if(status == 0) {
                    brotherTreeItem.remove();
                    treeCurentItem.before(brotherTreeItem);
                }
            }
            // 调换position
            var currentItemPosition = treeCurentItem.attr('position');
            var brotherItemPosition = brotherTreeItem.attr('position');
            treeCurentItem.attr('position', brotherItemPosition);
            brotherTreeItem.attr('position', currentItemPosition);
        }
    });
};

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


var treeLeave = function(e){

}


$(function(){
    AuthActionTree.checkboxInit();
})
