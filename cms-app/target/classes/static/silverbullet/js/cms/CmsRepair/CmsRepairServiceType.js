/**
 * Created by jeffyuan on 2018/3/21.
 */
var CmsRepairType = {
    'url': 'cms/cmsrepairservicetype/',
    'editStatus': 0
};
CmsRepairType.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
CmsRepairType.getHtmlInfo = function(url, params){
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

CmsRepairType.getSearchHtmlInfo = function(obj, url, params){
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
CmsRepairType.loadData = function(obj, action, curpage) {
    var dialogInfo = CmsRepairType.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairType.checkboxInit();</script>";
    $("#data-list-content-list").html(dialogInfo);
    return true;
};

CmsRepairType.loadDataCommon = function(obj, action, value, dom){
    var dialogInfo = CmsRepairType.getHtmlInfo(action, value);
    dialogInfo += "<script>CmsRepairType.checkboxInit();</script>";
    $('#'+dom).html(dialogInfo);
    return true;
}


CmsRepairType.loadSearchData = function(obj, action, curpage){
    var dialogInfo = CmsRepairType.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairType.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
}

CmsRepairType.loadSearchDataModel = function(obj, action, curpage){
    var dialogInfo = CmsRepairType.getSearchHtmlInfo(obj, action, curpage);
    dialogInfo += "<script>CmsRepairType.checkboxInit();</script>";
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
CmsRepairType.loadDataModel = function(obj, action, curpage) {
    var dialogInfo = CmsRepairType.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>CmsRepairType.checkboxInit();</script>";
    $("#userModelList").html(dialogInfo);
    return true;
};


/**
 * 表格头部添加方法
 */
CmsRepairType.add = function(){
    var List = [];
    var y = null;
    var id = $('#serviceTypeTree').treeview('getSelected')[0];

    if(typeof(id) != "undefined"){
        List.push($('#serviceTypeTree').treeview('getSelected')[0].id);
    }

    if(List.length === 0){
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条数据',
            buttonLabel: "确定"
        });
    }else{
        CmsRepairType.addCommon(List[0]);
    }


}




/**
 * 表格头部添加通用方法
 */
CmsRepairType.addCommon = function(parentId) {

    var dialogInfo = CmsRepairType.getHtmlInfo(CmsRepairType.ctxPath + CmsRepairType.url + 'add.html', {'parentId': parentId});
    BootstrapDialog.show({
        title: '添加类型信息',
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
                CmsRepairType.save(CmsRepairType.ctxPath + CmsRepairType.url + 'save.do', dialogItself);
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
CmsRepairType.save = function(url, dialogItself, dom) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formCmsRepairType").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                BootstrapDialog.alert({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '提示',
                    message: data.message,
                    buttonLabel: "确定"
                });

                // 刷新页面
                CmsRepairType.treeNode(CmsRepairType.node);
                CmsRepairType.loadDataCommon(null, CmsRepairType.ctxPath + CmsRepairType.url + 'childList.html', {"curpage" : 1}, dom);
                dialogItself.close();
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
CmsRepairType.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairType.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairType.edit = function() {
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

    CmsRepairType.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
CmsRepairType.editCommon = function(uid) {
    var dialogInfo = CmsRepairType.getHtmlInfo(CmsRepairType.ctxPath + CmsRepairType.url + 'edit.html', {id: uid});
    BootstrapDialog.show({
        title: '编辑类型信息',
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
                CmsRepairType.save(CmsRepairType.ctxPath + CmsRepairType.url + 'save.do', dialogItself, 'data-list-content-list');

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
CmsRepairType.delete = function() {
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
    CmsRepairType.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
CmsRepairType.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairType.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
CmsRepairType.deleteCommon = function(ids) {
    BootstrapDialog.confirm({
        title: '删除提示',
        message: '是否确定删除<span style="color:red;">当前节点</span>与其<span style="color:red;">所有子节点</span>?',
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true,
        btnCancelLabel: '取消',
        btnOKLabel: '删除',
        btnOKClass: 'btn-warning',
        callback: function (result) {
            // CmsRepairType.NodeChildrenCheck(ids);

            if (result) {
                $.ajax({
                    type: "POST",
                    url: CmsRepairType.ctxPath + CmsRepairType.url + "delete.do",
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

CmsRepairType.checkboxInit = function() {
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
CmsRepairType.checkCommon = function(uid){
    var dialogInfo = CmsRepairType.getHtmlInfo(CmsRepairType.ctxPath + CmsRepairType.url + 'check.html', {id: uid});
    BootstrapDialog.show({
        title: '查看类型信息',
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
CmsRepairType.checkOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    CmsRepairType.checkCommon(uid);
};



/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
CmsRepairType.check = function() {
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

    CmsRepairType.checkCommon(arrays[0]);
};

/**
 * 获取选中的搜索key
 * @param e
 */
CmsRepairType.selectKey = function(e){
    var selectKey = $(e).attr("value");
}


/**
 * 根据条件查找信息
 */
CmsRepairType.search = function(dom){
    var searchKey = $(dom).parent().parent().parent().parent().parent().find("select").val();
    var searchValue = $(dom).parent().parent().find("input").val();
    console.log(searchValue);
    if(typeof(searchValue) == "undefined" ){
        searchValue = $(dom).parent().parent().find("select").val();
        console.log(searchValue);
    }
    var search = searchKey+':'+searchValue;

    CmsRepairType.loadSearchData(search, CmsRepairType.ctxPath + CmsRepairType.url + 'search.do', 1);

}




/****
 * 节点树控件
 */
/*refresh*/
CmsRepairType.refresh = function(){
    $("#treeCon").removeClass("col-md-4").addClass("col-md-3");
    $("#listCon").removeClass("col-md-8").addClass("col-md-9");
    setTimeout(function(){
        CmsRepairType.tree("NONE");
        CmsRepairType.refreshTableCommon();
    },300);
}
/*home*/
CmsRepairType.home = function(){
    $("#serviceTypeTree").treeview('collapseAll', { silent:true });
    if(CmsRepairType.editStatus === 0){
        CmsRepairType.refreshTableCommon();
    }

}
/*edit*/
CmsRepairType.treeEdit = function(){
    if(0 === CmsRepairType.editStatus){
        $("#treeCon").removeClass("col-md-3").addClass("col-md-4");
        $("#listCon").removeClass("col-md-9").addClass("col-md-8");
        // $("#listCon").find("div").find("div").find("div").html("");
        CmsRepairType.treeEditCommon("NONE");
        CmsRepairType.editStatus = 1;
        CmsRepairType.layer();
    }else if(1 === CmsRepairType.editStatus){
        CmsRepairType.refresh();
    }


}


/**
 * 刷新内容表格
 */
CmsRepairType.refreshTable = function(){
    var List = [];
    var id = $('#serviceTypeTree').treeview('getSelected')[0];

    if(typeof(id) != "undefined"){
        List.push($('#serviceTypeTree').treeview('getSelected')[0].id);
    }

    CmsRepairType.loadDataCommon(null, CmsRepairType.ctxPath + CmsRepairType.url + 'list.html', {"curpage" : 1, "parentId": List[0]}, 'data-list-content-list');
}


/**
 * 刷新内容表格通用方法
 */
CmsRepairType.refreshTableCommon = function(){
    CmsRepairType.editStatus = 0;
    CmsRepairType.loadDataCommon(null, CmsRepairType.ctxPath + CmsRepairType.url + 'list.html', {"curpage" : 1}, 'data-list-content-list');
}



/**
 * 流程管理model内容刷新
 */
CmsRepairType.modelRefresh = function(){
    CmsRepairType.loadDataModel(null, CmsRepairType.ctxPath + CmsRepairType.url + 'listModel.html', 1);
}





/**
 * model内根据条件查找信息
 */
CmsRepairType.modelSearch = function(dom){
    var searchKey = $(dom).parent().parent().parent().parent().parent().find("select").val();
    var searchValue = $(dom).parent().parent().find("input").val();
    console.log(searchValue);
    if(typeof(searchValue) == "undefined" ){
        searchValue = $(dom).parent().parent().find("select").val();
    }
    var search = searchKey+':'+searchValue;


    CmsRepairType.loadSearchDataModel(search, CmsRepairType.ctxPath + CmsRepairType.url + 'searchModel.do', 1);

}


/**
 * 设置客户状态通用方法
 */
CmsRepairType.blackList = function(){
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

    CmsRepairType.blackListCommon(arrays[0]);
}


/**
 * 设置客户状态通用方法
 * @param uid
 */
CmsRepairType.blackListCommon = function(uid){
    var dialogInfo = CmsRepairType.getHtmlInfo(CmsRepairType.ctxPath + CmsRepairType.url + 'blackList.html', {id: uid});
    BootstrapDialog.show({
        title: '设置客户状态',
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
                CmsRepairType.saveBlackList(CmsRepairType.ctxPath + CmsRepairType.url + 'setBlackList.do', dialogItself);
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

CmsRepairType.saveBlackList = function (url, dialogItself) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formBlackList").serialize(),
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

                CmsRepairType.loadData(null, CmsRepairType.ctxPath + CmsRepairType.url + 'list.html', 1);
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

CmsRepairType.treeBuild = function(uid){

    //声明树结构
    var treeList = [];

    $.ajax({
        type: "post",
        async:false,
        url: CmsRepairType.ctxPath + CmsRepairType.url + 'tree.do',
        data: {parentId: uid},
        dataType: "json",
        success: function(data) {
            data = data.result.resultList;
            $.each(data, function(k,v){
                treeList.push(CmsRepairType.treeBuildCommon(v));
            });

        }
    });
    return treeList;
}

/**
 * 构造tree数据结构
 * @param data
 */
CmsRepairType.treeBuildCommon = function(data){

    var treeNode = {};

    //构造树
    if(data.type == 1) {
        treeNode["lazyLoad"] = true;
    }
    treeNode["text"] = data.typeName;
    treeNode["id"] = data.id;
    treeNode["icon"] = data.icon;

    return treeNode;
    console.log(treeNode);
}


CmsRepairType.tree = function(uid){
    //填充树
    $('#serviceTypeTree').treeview({

        data: CmsRepairType.treeBuild(),
        showBorder: false,
        levels:1,
        loadingIcon:"fa fa-hourglass",
        selectedBackColor: '#454849',
        selectedColor: '#fff',
        showCheckbox: false,
        lazyLoad: function(node,addNode){
            CmsRepairType.node = node;
            CmsRepairType.treeLazyLoad(node);
        }
    });
}


CmsRepairType.treeEditCommon = function(uid){
    //填充编辑树
    $('#serviceTypeTree').treeview({
        data: CmsRepairType.treeBuild(),
        showBorder: false,
        levels:1,
        loadingIcon:"fa fa-hourglass",
        selectedBackColor: '#ffffff',
        selectedColor: '#333',
        showCheckbox: true,
        uncheckedIcon: 'fa fa-square-o animated wobble',
        checkedIcon: 'fa fa-check-square animated bounceIn',
        lazyLoad: function(node,addNode){
            CmsRepairType.node = node;
            CmsRepairType.treeLazyLoad(node);
        }
    });

}


/**
 * 节点鼠标经过事件
 * @type {string}
 */
/*CmsRepairType.*/treeEnter = function(e){
    var status = CmsRepairType.editStatus;
    if(1 === status){
        CmsRepairType.treeEditButton(e);
    }
}



CmsRepairType.treeEditButton = function(e){
    var buttonGroup =
            '<span class=\'NodeEdit icon node-icon fa fa-pencil animated bounceIn\' data-editId=\'edit\' onclick=\'CmsRepairType.editHandle(this)\'>' +
            '</span>'
    var siblings = $(e).siblings().find("span[data-editId='edit']")
    if(0 ===  siblings.length){
        if($(e).find("span[data-editId='edit']").length === 0){
            $(e).append(buttonGroup);
            CmsRepairType.hw('.NodeEdit', 'width');
        }
    }else{
        siblings.remove();
        if($(e).find("span[data-editId='edit']").length === 0){
            $(e).append(buttonGroup);
            CmsRepairType.hw('.NodeEdit', 'width');
        }
    }
}

/*CmsRepairType.*/treeLeave = function(e){
    // var status = CmsRepairType.editStatus;
    // if(1 === status) {
    //     $(e).find("span[data-editId='edit']").remove();
    // }
}


/**
 *  treeNode操作菜单
 * @param e
 */
CmsRepairType.editHandle = function(e){
    var con = $(e).parent().attr("data-nodeid");
    var treeEditGroup = {
        'width': '130px',
        'border-radius': '2px',
        'background': '#f2f2f2',
        'color': '#333',
        'padding': '3px 5px'
    }
    var dom ='<span class="edit-group">' +
        '<span class="fa fa-plus animated" style="color:#008d4c;" onclick="CmsRepairType.treeAdd(this)"></span>'+
        '<span class="fa fa-edit animated" style="color:#00c0ef;" onclick="CmsRepairType.TreeEdit(this)"></span>'+
        '<span class="fa fa-trash animated" style="color:#f56954;" onclick="CmsRepairType.treeDelete(this)"></span>';
    '</span>'

    $(e).css(treeEditGroup);
    $(e).parent().css({
        'background-color': '#fff!important',
        'color': '#333'
    });
    if(0 === $(".edit-group").length){
        setTimeout(function(){
            $(e).append(dom)
            CmsRepairType.hw(".edit-group span", 'width');
        }, 300);
    }else{
        $(e).find("span[class='edit-group']").remove();
        CmsRepairType.hw(e, 'width');
        $(e).css({
            'border-radius': '50%',
            'background': '#009688',
            'color': '#f2f2f2',
            'padding': '1px 2px 3px 4px'
        });
    }



}





/**
 * 节点懒加载
 * @param node
 */
CmsRepairType.treeLazyLoad = function(node){
    var treeList = [];

    /*$("#serviceTypeTree ul").find("li[id='"+node.id+"']").siblings().remove();*/

    $.ajax({
        type: "post",
        async:false,
        url: CmsRepairType.ctxPath + CmsRepairType.url + 'tree.do',
        data: {parentId: node.id},
        dataType: "json",
        success: function(data){
            data = data.result.resultList;
            $.each(data, function(k,v){
                treeList.push(CmsRepairType.treeBuildCommon(v));
            });
            CmsRepairType.addNode(treeList, 'serviceTypeTree',node);
        }
    });

}


/**
 *节点添加方法
 * @param data
 */
CmsRepairType.addNode = function(data, id,node){
    $('#'+id).treeview("addNode", [data,node]);
}

/**
 * 图标选择页面入口
 */
CmsRepairType.iconPage = function(){
    var dialogInfo = CmsRepairType.getHtmlInfo(CmsRepairType.ctxPath + 'page/icon.html', '');
    BootstrapDialog.show({
        title: '选择图标',
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

                $("input[name='icon']").attr("value", CmsRepairType.modelIcon());
                $("#model-icon").html('<span style="color:#333;">'+CmsRepairType.modelIcon()+'</span>'+'&nbsp;<i class="'+CmsRepairType.modelIcon()+'" style="color:#333;"></i>');
                dialogItself.close();
            }
        },{
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
 * 获取选择的icon信息
 */
CmsRepairType.modelIcon = function(){
    return $("input[name='iconName']").val();
}


/**
 * 获取当前点击节点
 * @param e
 */
/*CmsRepairType.*/treeClick = function(e){
    if(CmsRepairType.editStatus != 1){
        CmsRepairType.childList($(e));
    }

}


/**
 * 动态异步刷新某节点下的子项
 */
CmsRepairType.treeNode = function(node){
    for(var i =0; i<node.nodes.length; i++){
        var id = node.nodes[i].id;
        $("#serviceTypeTree ul").find("li[id='"+id+"']").remove();
        console.log(id);
    }
    CmsRepairType.node.nodes = [];
    CmsRepairType.treeLazyLoad(node);
}




/**
 * 根据parentId刷新子列表
 * @constructor
 */
CmsRepairType.childList = function(node){

    $.ajax({
        type: "post",
        async:false,
        url: CmsRepairType.ctxPath + CmsRepairType.url + 'childList.html',
        data: {parentId: node.attr("id")},
        dataType: "html",
        success: function(data){
            data += "<script>CmsRepairType.checkboxInit();</script>";

            $("#data-list-content-list").html(data);
        },
    });

}



/**
 * 控制input输入数字
 * @constructor
 */
CmsRepairType.InputNumber = function(e){
    if(e.value.length==1) {
        e.value=e.value.replace(/[^1-9]/g,'')
    }
    else{
        e.value=e.value.replace(/\D/g,'')
    }
}

/**
 * width,height等长控制
 * @param e
 * @param type height,width
 */
CmsRepairType.hw = function(e, type){
    var width = $(e)[0].offsetWidth;
    var height = $(e)[0].offsetHeight;
    if('height' === type){
        $(e).css({'height': width});
    }else if('width' === type){
        $(e).css({'width': height+'px'});
    }
}


/**
 *树操作集
 */
/*添加节点*/
CmsRepairType.treeAdd = function(e){
    var e = $(e).parent().parent().parent().attr("id");
    CmsRepairType.addCommon(e);
}
/*编辑节点*/
CmsRepairType.TreeEdit = function(e){
    var e = $(e).parent().parent().parent().attr("id");
    CmsRepairType.editCommon(e);
}
/*删除节点*/
CmsRepairType.treeDelete = function(e){
    var e = $(e).parent().parent().parent().attr("id");
    CmsRepairType.deleteCommon(e);
}


/**
 * 遮罩层
 */
CmsRepairType.layer = function(){
    var css = {
        'margin': '0 14px',
        'width': '96.5%',
        'height': $("#listCon")[0].offsetHeight-20,
        'position': 'absolute',
        'z-index': '999',
        'border-radius': '3px'
    }
    $("#listCon").append('<div class="layer"></div>');
    $(".layer").css(css);
}


/**
 * 节点展开与删除
 */
CmsRepairType.openDel = function(){
    var status = CmsRepairType.editStatus;
    if(0 === status){
        CmsRepairType.openAll();
    }else if(1 === status){
        var list = [];
        var n = $('#serviceTypeTree').treeview('getChecked');
        $.each(n,function(k,v){
            list.push(v.id);
        });
        if (list.length == 0) {
            BootstrapDialog.alert({
                type: BootstrapDialog.TYPE_WARNING,
                title: '提示',
                message: '请选择至少一条需要删除的数据。',
                buttonLabel: "确定"
            });
            return ;
        }
        console.log(list);
        var ids = list.join(",");
        CmsRepairType.deleteCommon(ids);
    }
}


/**
 * 展开所有节点
 */
CmsRepairType.openAll = function(){

}






$(function () {
    CmsRepairType.checkboxInit();
    CmsRepairType.tree("NONE");
});

