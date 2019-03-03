/**
 * Created by GESOFT on 2018/3/21.
 */
var ActModule = {
    'url': 'act/model/',
    'datas': {},
};
ActModule.ctxPath = $(".logo").attr('href');

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
ActModule.getHtmlInfo = function (url, params) {
    var dialogInfo = '';
    $.ajax({
        type: "post",
        url: url,
        async: false,
        data: params,
        dataType: 'html',
        success: function (data) {
            dialogInfo = data.replace(/\r|\n/g, "");
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
ActModule.loadData = function (obj, action, curpage) {
    var dialogInfo = ActModule.getHtmlInfo(action, {"curpage": curpage});
    dialogInfo += "<script>ActModule.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};

/**
 * 表格头部添加方法
 */
ActModule.add = function () {
    var dialogInfo = ActModule.getHtmlInfo(ActModule.ctxPath + ActModule.url + 'add.html', {});
    layer.open({
        type: 1,
        title: '添加模型',
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
            ActModule.save(ActModule.ctxPath + ActModule.url + 'save.do', index);
            return false;
        }
    });
};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
ActModule.save = function (url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (data.result == true) {
                layer.msg(data.message);
                // 刷新页面
                ActModule.loadData(null, ActModule.ctxPath + ActModule.url + "list.html", 1);
                layer.close(layerIndex);
            } else {
                if (data.errors != null) {
                    // 错误信息反馈到页面上
                    for (var i = 0; i < data.errors.length; i++) {
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
ActModule.editOne = function (obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    ActModule.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
ActModule.edit = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
        return;
    }

    ActModule.editCommon(arrays[0]);
    setTimeout(function () {
        ActModule.findActionTreeName();
    }, 300);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
ActModule.editCommon = function (uid) {
    var dialogInfo = ActModule.getHtmlInfo(ActModule.ctxPath + ActModule.url + 'edit.html', {"id": uid});
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
                $("label[id^=msg-]").each(function () {
                    $(this).text("");
                });
                $("#msg").text("");

                // 保存
                ActModule.save(ActModule.ctxPath + ActModule.url + 'save.do', dialogItself);

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
ActModule.delete = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择至少一条需要删除的数据。',
            buttonLabel: "确定"
        });
        return;
    }
    var ids = arrays.join(",");
    ActModule.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
ActModule.deleteOne = function (obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    ActModule.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
ActModule.deleteCommon = function (ids) {
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: ActModule.ctxPath + ActModule.url + "delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");
                    // 刷新页面
                    ActModule.loadData(null, ActModule.ctxPath + ActModule.url + "list.html", 1);
                    layer.close(index);
                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};

/**
 *配置岗位
 */
ActModule.setAction = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条数据。',
            buttonLabel: "确定"
        });
        return;
    }
    var dialogInfo = ActModule.getHtmlInfo(ActModule.ctxPath + ActModule.url + 'dictitem/list/' + arrays[0] + '.html',{"curPage": 5});

    BootstrapDialog.show({
        title: '设置字典项',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_DEFAULT,
        message: dialogInfo,
        buttons: [{
            label: '确定',
            action: function (dialogItself) {

                // 清除提示语
                $("label[id^=msg-]").each(function () {
                    $(this).text("");
                });
                $("#msg").text("");
                $("#UserId").val(arrays[0]);

                // 保存
                ActModule.save(ActModule.ctxPath + ActModule.url + 'setPost.do', dialogItself);

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
 * 设置岗位下拉菜单
 *
 * @param e
 */
ActModule.setOptionAction = function (e) {
    $.ajax({
        type: 'post',
        url: ActModule.ctxPath + ActModule.url + 'findPostName',
        data: {"id": $(e).val()},
        dataType: "json",
        success: function (data) {
            if (data === null) {
                $("#postName").empty();
            } else {
                var list = data;
                var s = "<option value='disabled selected' style='display: none;'></option>";
                if (list.length >= 0) {
                    $("#postName").empty();
                    for (var i = 0; i < list.length; i++) {
                        s += "<option value='" + list[i]['id'] + "'>" + list[i]['name'] + "</option>";
                    }

                    $("#postName").append(s);
                } else {
                    $("#postName").append(s);
                }
            }
        }
    });


};

/**
 * 设置字典项
 */
ActModule.setDictItem = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条数据。',
            buttonLabel: "确定"
        });
        return;
    }

    var dialogInfo = ActModule.getHtmlInfo(ActModule.ctxPath + ActModule.url + 'dictitem/list/' + arrays[0] + '.html', {"curpage": 1});
    BootstrapDialog.show({
        title: '设置字典项',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_WIDE,
        message: "<div id='item-list'>" + dialogInfo + "</div><script>ActModule.ItemCheckboxInit();</script>"
    });
};

ActModule.checkboxInit = function () {
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
 * 隶属部门下拉菜单填充
 */
ActModule.findActionTreeName = function () {
    $.ajax({
        type: 'post',
        url: ActModule.ctxPath + ActModule.url + 'findActionTreeName',
        data: {},
        dataType: "json",
        success: function (data) {
            var list = data;
            var s;
            if (list.length >= 0) {
                for (var i = 0; i < list.length; i++) {
                    s += "<option value='" + list[i]['id'] + "'>" + list[i]['name'] + "</option>";
                }
                $("#actionTreeName").append(s);
            } else {
                $("#actionTreeName").append(s);
            }
        }
    });
}


ActModule.findPostByActionTreeName = function () {
    $("#actionTreeName").on("change", function () {
        var ActionTreeName = $(this).val();
        $.ajax({
            type: 'post',
            url: ActModule.ctxPath + ActModule.url + 'findPostName',
            data: {"id": ActionTreeName},
            dataType: "json",
            success: function (data) {
                if (data === null) {
                    $("#postName").empty();
                } else {
                    var list = data;
                    var s = "<option value='disabled selected' style='display: none;'></option>";
                    if (list.length >= 0) {
                        $("#postName").empty();
                        for (var i = 0; i < list.length; i++) {
                            s += "<option value='" + list[i]['id'] + "'>" + list[i]['name'] + "</option>";
                        }

                        $("#postName").append(s);
                    } else {
                        $("#postName").append(s);
                    }
                }
            }
        });
    });
};


ActModule.disable = function ($this) {
    var checkOrgId = $($this).val();
    var option = "option[value='" + checkOrgId + "']";
    $($this).find(option)[1].remove();
}

$(function () {
    $("#actionTreeName").on("click", function () {
        ActModule.disable(this);
    });
    ActModule.checkboxInit();
    ActModule.findPostByActionTreeName();
});


// $(function () {
//     ActModule.checkboxInit();
// })


