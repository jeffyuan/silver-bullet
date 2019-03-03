/**
 * Created by GESOFT on 2018/3/21.
 */
var AuthUser = {
    'url': 'auth/sysauthuser/',
    'datas': {},
};
AuthUser.ctxPath = $(".logo").attr('href');
/**
 * 密码验证
 * @type {string}
 */
AuthUser.passwordVerify = "<form id=\"formParam\" class=\"m-t\" role=\"form\" method=\"post\"><div class=\"form-group\">\n" +
    "            <label>当前登录用户密码：</label>&nbsp;<label id=\"msg\" style=\"color: red;\"></label><label style=\"color: red;\" id=\"password-always-checkbox\"></label>\n" +
    "            <input type=\"password\" name=\"password\" class=\"form-control\" value=\"\">\n" +
    "        </div></form>";
AuthUser.passwordChange = "<form id=\"formParams\" class=\"m-t\" role=\"form\" method=\"post\"><div class=\"form-group\">\n" +
    "            <label>新密码：</label><label style=\"color: red;\" id=\"password\"></label>\n" +
    "            <input type=\"password\" name=\"newPassword\" class=\"form-control\" value=\"\">\n" +
    "            <label>确认密码：</label>&nbsp;<label id=\"msg\" style=\"color: red;\"></label><label style=\"color: red;\" id=\"passwordCheck\"></label>\n" +
    "            <input type=\"password\" name=\"checkPassword\" class=\"form-control\" value=\"\">\n" +
    "        </div></form>";

/**
 * 获取编辑和保存的子html内容
 * @param url 后台访问地址
 * @param params 参数
 * @returns {string}
 */
AuthUser.getHtmlInfo = function (url, params) {

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
AuthUser.loadData = function (obj, action, curpage) {
    var dialogInfo = AuthUser.getHtmlInfo(action, {"curpage": curpage});
    dialogInfo += "<script>AuthUser.checkboxInit();</script>";
    $("#data-list-content").html(dialogInfo);
    return true;
};


/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
AuthUser.save = function (url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function (data) {
            // console.log(data);
            if (data.result == true) {
                layer.msg(data.message);
                // 刷新页面
                AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
                layer.close(layerIndex);
            } else {
                if (data.errors != null) {
                    // 错误信息反馈到页面上
                    for (var i = 0; i < data.errors.length; i++) {
                        $("#msg-" + data.errors[i].field).text('  (' + data.errors[i].defaultMessage + ')');
                    }
                } else {
                    $("#msg-"+data.address).text(data.message);
                }
            }
        }
    })
};


/**
 * 查看一条数据
 *
 */
AuthUser.check = function() {
    var arrays = [];
    $("div[aria-checked='true']").each(function(){
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条需要查看的数据。');
        return ;
    }
    AuthUser.checkCommon(arrays[0]);
};

/**
 * 查看通用方法
 * @param uid
 */
AuthUser.checkCommon = function(uid){
    // console.log(typeof (uid));
    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'check.html', {id: uid});
    layer.open({
        type: 1,
        title: '查看用户信息',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: dialogInfo
    });
};

/**
 * 表格头部添加方法
 */
AuthUser.add = function () {
    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'add.html', {});
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
            AuthUser.save(AuthUser.ctxPath + AuthUser.url + 'save.do', index);
            return false;
        }
    });
};

/**
 * 表格中编辑按钮
 */
AuthUser.editOne = function (obj) {

    var uid = $(obj).parent().parent().attr("data-u");
    AuthUser.editCommon(uid);
};

/**
 * 表格头部编辑按钮，只能编辑一条记录
 */
AuthUser.edit = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });


    if (arrays.length != 1) {
        layer.msg("请选择一个需要修改的树。");
        return;
    }

    AuthUser.editCommon(arrays[0]);
    // setTimeout(function () {
    //     AuthUser.findActionTreeName();
    // }, 300);
};

/**
 * 编辑通用方法
 * @param uid 编辑的一个id
 */
AuthUser.editCommon = function (uid) {
    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'edit.html', {"id": uid});
    layer.open({
        type: 1,
        title: '修改用户',
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
            AuthUser.save(AuthUser.ctxPath + AuthUser.url + 'save.do', index);
            return false;
        }
    });
};

/**
 * 表格头部，统一删除按钮
 */
AuthUser.delete = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length == 0) {
        layer.msg("请选择至少一条需要删除的数据。");
        return;
    }
    var ids = arrays.join(",");
    AuthUser.deleteCommon(ids);
};

/**
 * 表格中删除一条数据
 */
AuthUser.deleteOne = function (obj) {
    var uid = $(obj).parent().parent().attr("data-u");
    AuthUser.deleteCommon(uid);
};

/**
 * 删除通用该方法
 * @param ids 以,号分割的字符串
 */
AuthUser.deleteCommon = function (ids) {
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: AuthUser.ctxPath + AuthUser.url + "delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");
                    // 刷新页面
                    AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
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
AuthUser.setAction = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg('请选择一条数据。');
        return;
    }
    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'loadOrganizationItem.html', {userId: arrays[0]});
    layer.open({
        type: 1,
        title: '设置字典项',
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
            AuthUser.save(AuthUser.ctxPath + AuthUser.url + 'setPost.do', index);
            return false;
        }
    });
};


/**
 * 设置岗位下拉菜单
 *
 * @param e
 */
AuthUser.setOptionAction = function (e) {
    $.ajax({
        type: 'post',
        url: AuthUser.ctxPath + AuthUser.url + 'findPostName',
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
AuthUser.setDictItem = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });

    if (arrays.length != 1) {
        layer.msg("请选择一条数据。");
        return;
    }

    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'dictitem/list/' + arrays[0] + '.html', {"curpage": 1});
    layer.open({
        type: 1,
        title: '设置字典项',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: "<div id='item-list'>" + dialogInfo + "</div><script>AuthUser.ItemCheckboxInit();</script>"
    });
};

AuthUser.checkboxInit = function () {
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


AuthUser.disable = function ($this) {
    var checkOrgId = $($this).val();
    var option = "option[value='" + checkOrgId + "']";
    $($this).find(option)[1].remove();
}

$(function () {
    $("#actionTreeName").on("click", function () {
        AuthUser.disable(this);
    });
    AuthUser.checkboxInit();
});

AuthUser.resetPassword = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });
    if (arrays.length == 0) {
        layer.msg("请选择至少一条需要重置密码的数据。");
        return;
    }
    var ids = arrays.join(",");
    layer.confirm('是否确定重置密码?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: AuthUser.ctxPath + AuthUser.url + "resetPassword.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("重置成功！");
                    // 刷新页面
                    AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
                    layer.close(index);
                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};
/**
 * 更改密码入口
 */


AuthUser.changePassword = function () {
    var arrays = [];
    $("#data-list div[aria-checked='true']").each(function () {
        arrays.push($(this).parent().parent().attr('data-u'));
    });
    if (arrays.length != 1) {
        layer.msg('请选择一条数据。');
        return;
    } else {
        AuthUser.verifyPassword(arrays[0]);
    }

};

/**
 * 验证密码
 * @param id
 */
AuthUser.verifyPassword = function (id) {
    layer.open({
        type: 1,
        title: '验证当前用户密码',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: AuthUser.passwordVerify,
        btn: ['确定', '取消']
        ,yes: function(index, layero){
            $.ajax({
                type: "POST",
                url: AuthUser.ctxPath + "verifyPassword.do",
                data: $("#formParam").serialize(),
                dataType: "json",
                success: function (data) {
                    if (data.result == "true") {
                        dialogItself.close();
                        AuthUser.setPassword(id);
                    } else {
                        $("#msg").text(data.message);
                    }


                }
            });
            return false;
        }
    });
};
/**
 * 修改密码
 * @param id
 */
AuthUser.setPassword = function (id) {
    layer.open({
        type: 1,
        title: '更改用户密码',
        maxmin: false,
        area: '500px',
        shade: 0.3,
        shadeClose: false,
        content: AuthUser.passwordChange,
        btn: ['确定', '取消']
        ,yes: function(index, layero){
            $.ajax({
                type: "POST",
                url: AuthUser.ctxPath + AuthUser.url + "changePassword.do",
                data: "id="+id+"&"+$("#formParams").serialize(),
                dataType: "json",
                success: function (data) {
                    // console.log($("#formParams").serialize())
                    if (data.result == "true") {
                        layer.msg("修改成功！");

                        // 刷新页面
                        AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);

                        layer.close(index);
                    } else {
                        $("#msg").text(data.message);
                    }


                }
            });
            return false;
        }
    });
};