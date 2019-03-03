/**
 * Created by GESOFT on 2018/3/21.
 */
var AuthPost = {
    'url': 'auth/sysauthpost/',
    'datas' : {},
};
AuthPost.ctxPath = $(".logo").attr('href');
AuthPost.actionTreeUrl = "auth/sysauthactiontree/";

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
AuthPost.getHtmlInfo = function(url, params){
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

AuthPost.getHtml = function(url, params){
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
AuthPost.loadData = function(obj, action, curpage) {
    var dialogInfo = AuthPost.getHtmlInfo(action, {"curpage" : curpage, orgId: obj});
    dialogInfo += "<script>AuthPost.checkboxInit();</script>";
    $("#item-list").html(dialogInfo);
    return true;
};


/**
 * 表头添加方法
 * */
AuthPost.add = function() {

    var orgId = $('#AuthOrganizationIdList').attr('value');

    AuthPost.addCommon(orgId);
}


/**
 * 表格头部添加通用方法
 */
AuthPost.addCommon = function(orgId) {

    var dialogInfo = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'add.html', {orgId: orgId});
    layer.open({
        type: 1,
        title: '添加岗位',
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
            AuthPost.save(AuthPost.ctxPath + AuthPost.url + 'save.do', index, orgId);
            return false;
        }
    });
};


AuthPost.addModel = function() {
    var dialogInfo = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'add.html', {});
    layer.open({
        type: 1,
        title: '添加部门',
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
            AuthPost.save(AuthPost.ctxPath + AuthPost.url + 'save.do', index);
            return false;
        }
    });
};



/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
AuthPost.save = function(url, layerIndex, orgId) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                layer.msg(data.message);
                // 刷新页面
                var con = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'list.html', {orgId: orgId});
                $("#item-list").empty().append(con);
                
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
AuthPost.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    var orgId = $('#AuthOrganizationIdList').attr('value');
    AuthPost.editCommon(uid, orgId);

};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
AuthPost.edit = function() {
    var arrays = [];
    $(".post-data-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg("请选择一条需要修改的数据。");
        return ;
    }
    var orgId = $('#AuthOrganizationIdList').attr('value');
    AuthPost.editCommon(arrays[0], orgId);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
AuthPost.editCommon = function(uid, orgId) {
    var dialogInfo = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'edit.html', {"id":uid, orgId: orgId});
    layer.open({
        type: 1,
        title: '修改部门',
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
            AuthPost.save(AuthPost.ctxPath + AuthPost.url + 'save.do', index, orgId);
            return false;
        }
    });
};

/**
 * 表格头部，统一删除按钮
 */
AuthPost.delete = function() {
    var arrays = [];
    $("#item-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        layer.msg("请选择至少一条需要删除的数据。");
        return ;
    }
    var ids = arrays.join(",");
    AuthPost.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
AuthPost.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    AuthPost.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
AuthPost.deleteCommon = function(ids) {
    var orgId = $('#AuthOrganizationIdList').attr('value');
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: AuthPost.ctxPath + AuthPost.url + "deletes.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");
                    // 刷新页面
                    var con = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'list.html', {orgId: orgId});
                    $("#item-list").empty().append(con);

                    layer.close(index);
                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};

/**
 * 设置权限项
 */
AuthPost.setDictItem = function() {
    var arrays = [];
    var treeUrl = '/a/static/sysAuthActionTree/AuthActionTree.js';
    var style = '.modal-body{background: #FFF!important;  }.nav-tabs-custom>.nav-tabs>li.active{border-top-color:#222d32;}';
    $("#data-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u' ));
    });


    if (arrays.length != 1) {
        layer.msg("请选择一条数据。");
        return ;
    }

    var dialogInfo = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'dictitem/list/'+ arrays[0] + '.html', {"curpage" : 1, "Id":1});
    layer.open({
        type: 1,
        title: '分配岗位',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: "<div id='item-list'>" + dialogInfo + "</div><script src='"+treeUrl+"' type='text/javascript'></script><script>AuthActionTree.checkboxStyle();</script><style>"+style+"</style>"
    });
};

AuthPost.checkboxInit = function() {
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

AuthPost.getActionSelectParent = function () {
    var id = [];
    $(".lg").find("ul").find("li").each(function(){
        var y = $(this).attr("style").indexOf("background-color:#3e3e3e;");
        if(y >= 0){
            id.push($(this).attr("id"));
        }
    });
    $.ajax({
        type: "POST",
        url: AuthPost.ctxPath + AuthPost.url + "list/"+id[0]+".do",
        dataType: "json",
        data:{},
        success: function (data) {
            $("#ActionSelectParent").attr("value", data.results.parentId);
        }
    });
}


/**
 * 表格头部权限配置，只能配置一条记录
 */
AuthPost.setPos = function() {
    AuthOrganization.status = 1;
    var arrays = [];
    $("#item-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg("请选择一条需要修改的数据。");
        return ;
    }

    AuthPost.setPermission(arrays[0]);

};

/**
 * 配置权限通用方法
 */
AuthPost.setPermission = function(uid){
    var dialogInfo = AuthPost.getHtmlInfo(AuthPost.ctxPath+AuthPost.url+"modelTree.html", {uid: uid});
    layer.open({
        type: 1,
        title: '配置权限',
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
            $(function(){
                AuthOrganization.status = 0;
                AuthPost.nodePost(uid);
                dialogItself.close();
            });

            return false;
        },
        btn1: function(index, layero) {
            AuthOrganization.status = 0;
        }
    });
};


/**
 * 获取actionTree
 */
AuthPost.getActionTree = function(){

};



/**
 *  配置权通用方法
 */

AuthPost.checkPermission = function (uid) {
    var dialogInfo = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'checkPermission/1.html', {"id":uid});

    layer.open({
        type: 1,
        title: '配置权限',
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
            AuthPost.save(AuthPost.ctxPath + AuthPost.url + 'save.do', index);

            return false;
        },
        btn1: function(index, layero) {
            AuthOrganization.status = 0;
        }
    });
};


AuthPost.getChildrenTree = function(node){
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
 * 获取选中节点通用方法
 */
AuthPost.findNode = function(){
    var Node = [];
    var node = [];

    var Nodes = $('#modelOrganizationTree').treeview('getChecked', [])

    $.each(Nodes, function(k,v){
        Node.push(v.actionId);
    });

    console.log(Node)
    /*alert(Node);*/
    for(var i=0;i<Node.length;i++){
        if(Node[i] != undefined){
            node.push(Node[i]);
        }
    }
    return node;
}


/**
 * 加工获取节点actionId
 */
AuthPost.nodePost = function(uid){
    var node = AuthPost.findNode();

    $.ajax({
        type: 'post',
        data: {
            actionId: node,
            postIds: uid
        },
        url: AuthPost.ctxPath + AuthPost.url + "setPostAction.do",
        dataType: "json",
        success: function(data){
            if(data.result == true){
                layer.msg("权限配置成功。");
            }
        }
    });
}



/**
 * 查看状态方法
 */
AuthPost.checkStatus = function(){
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });
    $("#uid").attr("value", arrays[0]);
}


AuthPost.checkCommon = function(){
    var uid = $("#uid").attr("value");
    var info = AuthPost.getHtmlInfo(AuthPost.ctxPath + AuthPost.url + 'edit.html', {"id":uid});
    $("#tab_3>.row").html(info);

}


/**
 * modelTree填充
 * @param e
 * @constructor
 */
AuthPost.TreeNode = function(e, postId){

    var node = $(e).bsTableBuild({
        uid: 'NONE',
        url: AuthPost.ctxPath+AuthPost.actionTreeUrl+"tree.do",
        showCheckbox: true,
        selectedBackColor: "none",
        selectedColor: '333',
        postId: postId
    });

    AuthPost.node = node;
}


