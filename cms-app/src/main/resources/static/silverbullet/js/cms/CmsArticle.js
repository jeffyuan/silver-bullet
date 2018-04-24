/**
 * Created by jeffyuan on 2018/3/21.
 */
var Cms = {
    'url': 'cms/cmsarticle/'
};
Cms.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
Cms.getHtmlInfo = function(url, params){
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
Cms.loadData = function(obj, action, curpage) {
    var typeId = $("#typeTree").tree('getSelected').id;
    var dialogInfo = Cms.getHtmlInfo(action, {"curpage" : curpage, "typeId": typeId});
    dialogInfo += "<script>Cms.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};

/**
 * 表格头部添加方法
 */
Cms.add = function() {
    var node = $("#typeTree").tree('getSelected');
    if (node == null || node.id == null) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: "请选择一个栏目",
            buttonLabel: "确定"
        });
        return ;
    }

    var dialogInfo = Cms.getHtmlInfo(Cms.ctxPath + Cms.url + 'add.html', {typeId: node.id});
    BootstrapDialog.show({
        title: '添加',
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
                Cms.save(Cms.ctxPath + Cms.url + 'save.do', dialogItself);
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
Cms.save = function(url, dialogItself) {
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

                Cms.loadData(null, Cms.ctxPath + Cms.url + 'list.html', 1);
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
Cms.editOne = function(obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    Cms.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
Cms.edit = function() {
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

    Cms.editCommon(arrays[0]);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
Cms.editCommon = function(uid) {
    var dialogInfo = Cms.getHtmlInfo(Cms.ctxPath + Cms.url + 'edit.html', {id: uid});
    BootstrapDialog.show({
        title: '编辑文章',
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
                Cms.save(Cms.ctxPath + Cms.url + 'save.do', dialogItself);
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
Cms.delete = function() {
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
    Cms.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
Cms.deleteOne = function(obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    Cms.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
Cms.deleteCommon = function(ids) {
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
                    url: Cms.ctxPath + Cms.url + "delete.do",
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

Cms.checkboxInit = function() {
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

$(function () {
    Cms.loadTree();
    Cms.checkboxInit();
});


Cms.loadTree = function(){
    //首先在loadTree时，定义一个声明是否请求所有数据的全局变量isAll，默认值为false，代表逐级加载
    var isAll = false;
    $('#typeTree').tree({
        url: Cms.ctxPath + "cms/cmsarticletypetree/tree/list.do",
        method:"post",
        queryParams:{parentId: "NONE"},//传递请求参数，与下面的方式一相对应

        //添加onBeforeExpand展开前事件处理函数，通过isAll参数，让前端或者后台知道此时应该获取的是全部数据还是逐级加载数据
        onBeforeExpand:function(node){

            //方式一：重置请求参数，让服务器端判断是否获取全部数据
            $('#typeTree').tree('options').queryParams = {parentId:node.id};

            //方式二：重置请求地址，前端进行判断，是否发送到请求全部数据的地址
            // if(isAll == true){
            //     $('#tree').tree('options').url = '/tree/all/ajax';
            // }
        },
        onClick : function(node) {
            Cms.loadData(null, Cms.ctxPath + Cms.url + 'list.html', 1);
        }
    });
}