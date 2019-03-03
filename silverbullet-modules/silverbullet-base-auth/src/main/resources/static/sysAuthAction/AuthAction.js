/**
 * Created by GESOFT on 2018/3/21.
 */
var AuthAction = {
    'url': 'auth/sysauthaction/',
    'datas' : {},
};
AuthAction.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
AuthAction.getHtmlInfo = function(url, params){
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
AuthAction.loadData = function(obj, action, curpage) {
    var dialogInfo = AuthAction.getHtmlInfo(action, {"curpage" : curpage});
    dialogInfo += "<script>AuthAction.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
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
AuthAction.loadDataCommon = function(obj, action, value, dom){

    var val = {
        curpage: value,
        parentId: ""
    };

    var dialogInfo = AuthAction.getHtmlInfo(action, val);
    dialogInfo += "<script>AuthAction.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
}

/**
 * 表格头部添加方法
 */
AuthAction.add = function(obj) {
    var parentId;
    if (typeof obj == 'string') {
        parentId = obj;
    } else {
        parentId = $(obj).parent().parent().attr("data-u");
    }

    var dialogInfo = AuthAction.getHtmlInfo(AuthAction.ctxPath + AuthAction.url + 'add.html', {parentId: parentId});
    layer.open({
        type: 1,
        title: '添加功能',
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
            AuthAction.save(AuthAction.ctxPath + AuthAction.url + 'save.do', index);
            return false;
        }
    });

};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
AuthAction.save = function(url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        async: false,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                AuthAction.initTable();

                layer.msg(data.message);
                layer.close(layerIndex);
            } else {
                if (data.errors != null) {
                    // 错误信息反馈到页面上
                    for (var i = 0 ; i < data.errors.length; i++) {
                        $("#msg-" + data.errors[i].field).text('  (' + data.errors[i].defaultMessage + ')').fadeIn(200);
                    }
                } else {
                    $("#msg").text(data.message);
                }
            }
        }
    });
};

/**
 * 表格中编辑按钮
 */
AuthAction.editOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    AuthAction.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
AuthAction.edit = function() {
    var arrays = [];
    $("#data-list div[class='icheckbox_flat-blue checked']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条需要修改的数据。');
        return ;
    }

    AuthAction.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
AuthAction.editCommon = function(uid) {
    var dialogInfo = AuthAction.getHtmlInfo(AuthAction.ctxPath + AuthAction.url + 'edit.html', {"id":uid});
    layer.open({
        type: 1,
        title: '修改功能',
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
            AuthAction.save(AuthAction.ctxPath + AuthAction.url + 'save.do', index);

            return false;
        }
    });
};

/**
 * 表格头部，统一删除按钮
 */
AuthAction.delete = function() {
    var arrays = [];
    $("#data-list div[class='icheckbox_flat-blue checked']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        layer.msg('请选择至少一条需要删除的数据。');
        return ;
    }
    var ids = arrays.join(",");
    AuthAction.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
AuthAction.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    AuthAction.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
AuthAction.deleteCommon = function(ids) {
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: AuthAction.ctxPath + AuthAction.url + "delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");
                    // 刷新页面
                    AuthAction.initTable();
                    layer.close(index);
                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};

AuthAction.checkboxInit = function() {
    $('#data-list input[type="checkbox"]').iCheck({
        checkboxClass: 'icheckbox_flat-blue',
        radioClass: 'iradio_flat-blue'
    });

    //Enable check and uncheck all functionality
    $(".checkbox-toggle").unbind("click").click(function () {
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

AuthAction.getActionSelect = function () {
    $.ajax({
        type: "POST",
        url: AuthAction.ctxPath + AuthAction.url + "list.do",
        dataType: "json",
        data:{},
        success: function (data) {
            var list = data.list.resultList;
            if (list.length >= 0) {
                var s = "<option value='NONE'>NONE</option>";
                for(var i = 0;i < list.length;i++){
                    if(list[i]['resourceType']==="menu"){
                        s += '<option value='+list[i]['id']+">"+list[i]['name']+'</option>';
                    }
                }
                $("#ActionSelect").append(s);
            } else {
                $("#ActionSelect").append(s);
            }
        }
    });
}


/**
 * 搜索框下拉
 * @param e
 */
AuthAction.inputSlideToggle = function(e){
    $("#input").hide(300);
    $("#search").slideToggle();
    $("#return").show(300);
}

/**
 * 搜索框上拉
 * @param e
 */
AuthAction.inputSlideUp = function(e){
    $("#search").slideUp();
    $("#input").show(300);
    $("#return").hide(300);
}

$(function () {
    AuthAction.checkboxInit();
});

AuthAction.initTable = function() {
    var columns = [
        {field: 'selectItem', checkbox: true},
        {title: '名称', field: 'name', align: 'left', valign: 'middle'},
        {title: '链接', field: 'url', align: 'left', valign: 'middle'},
        {title: '类型', field: 'resource_type', align: 'center', valign: 'middle', sortable: true},
        {title: '权限标识', field: 'permission', align: 'left', valign: 'middle', sortable: true},
        {title: '备注', field: 'comments', align: 'left', valign: 'middle', sortable: true},
        {title: '操作', field: 'lastControl', fieldVal:'<button name="添加子元素" class="btn btn-default btn-sm" onclick="AuthAction.add(this)" space="true"><i class="fa fa-fw fa-plus"></i></button><button name="修改" class="btn btn-default btn-sm" onclick="AuthAction.editOne(this)" space="true"><i class="fa fa-fw fa-edit"></i></button><button name="删除" class="btn btn-default btn-sm" onclick="AuthAction.deleteOne(this)" space="true"><i class="fa fa-fw fa-trash-o"></i></button>'}];

    $('#data-list').bootstrapAjaxTreeTable({
        id: "id",// 选取记录返回的值
        code: "id",// 用于设置父子关系
        parentCode: "parent_id",// 用于设置父子关系
        rootCodeValue: "NONE", //设置根节点code值----可指定根节点，默认为null,"",0,"0"
        hasChildren: "children_num",  //是否存在子节点
        type: "POST", //请求数据的ajax类型
        url: AuthAction.ctxPath + AuthAction.url + "list.do",   //请求数据的ajax的url
        ajaxParams: {}, //请求数据的ajax的data属性
        expandColumn: 1,//在哪一列上面显示展开按钮,从0开始
        striped: true,   //是否各行渐变色
        expandAll: true,  //是否全部展开
        columns: columns,		//列数组
        toolbar: "#" + this.toolbarId,//顶部工具条
        height: this.height,
        callBackFun: AuthAction.checkboxInit   //回调处理一些操作的方法
    });
}

$(function () {
    AuthAction.initTable();
});
