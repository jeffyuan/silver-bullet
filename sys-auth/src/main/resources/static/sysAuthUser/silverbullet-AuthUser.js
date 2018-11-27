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
    "        </div></form>"
AuthUser.passwordChange = "<form id=\"formParams\" class=\"m-t\" role=\"form\" method=\"post\"><div class=\"form-group\">\n" +
    "            <label>新密码：</label><label style=\"color: red;\" id=\"password\"></label>\n" +
    "            <input type=\"password\" name=\"newPassword\" class=\"form-control\" value=\"\">\n" +
    "            <label>确认密码：</label>&nbsp;<label id=\"msg\" style=\"color: red;\"></label><label style=\"color: red;\" id=\"passwordCheck\"></label>\n" +
    "            <input type=\"password\" name=\"checkPassword\" class=\"form-control\" value=\"\">\n" +
    "        </div></form>"

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
 * @param dialogItself
 */
AuthUser.save = function (url, dialogItself) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formParams").serialize(),
        dataType: "json",
        success: function (data) {
            console.log(data);
            if (data.result == true) {
                BootstrapDialog.alert({
                    type: BootstrapDialog.TYPE_WARNING,
                    title: '提示',
                    message: data.message,
                    buttonLabel: "确定"
                });
                dialogItself.close();
                // 刷新页面
                AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
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
 * 表格头部添加方法
 */
AuthUser.add = function () {
    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'add.html', {});
    BootstrapDialog.show({
        title: '添加用户',
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
                AuthUser.save(AuthUser.ctxPath + AuthUser.url + 'save.do', dialogItself);
            }
        }, {
            label: '关闭',
            action: function (dialogItself) {
                dialogItself.close();
            }
        }
        ]
    });
    // setTimeout(function () {
    //     AuthUser.findActionTreeName();
    // }, 300)

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
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条需要修改的数据。',
            buttonLabel: "确定"
        });
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
    // AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'dictitem/list/' + uid + '.html',{"curPage": 5});
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
                $("#UserId").val(uid);

                // 保存
                AuthUser.save(AuthUser.ctxPath + AuthUser.url + 'save.do', dialogItself);

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
AuthUser.delete = function () {
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
                    url: AuthUser.ctxPath + AuthUser.url + "delete.do",
                    data: {ids: ids},
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
                            AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
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
 *配置岗位
 */
AuthUser.setAction = function () {
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
    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'loadOrganizationItem.html', {userId: arrays[0]});

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
                AuthUser.save(AuthUser.ctxPath + AuthUser.url + 'setPost.do', dialogItself);

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
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择一条数据。',
            buttonLabel: "确定"
        });
        return;
    }

    var dialogInfo = AuthUser.getHtmlInfo(AuthUser.ctxPath + AuthUser.url + 'dictitem/list/' + arrays[0] + '.html', {"curpage": 1});
    BootstrapDialog.show({
        title: '设置字典项',
        closable: true,
        closeByBackdrop: false,
        closeByKeyboard: false,
        size: BootstrapDialog.SIZE_WIDE,
        message: "<div id='item-list'>" + dialogInfo + "</div><script>AuthUser.ItemCheckboxInit();</script>"
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
        BootstrapDialog.alert({
            type: BootstrapDialog.TYPE_WARNING,
            title: '提示',
            message: '请选择至少一条需要重置密码的数据。',
            buttonLabel: "确定"
        });
        return;
    }
    var ids = arrays.join(",");
    BootstrapDialog.confirm({
        title: '重置密码提示',
        message: '是否确定重置密码?',
        type: BootstrapDialog.TYPE_WARNING,
        closable: true,
        draggable: true,
        btnCancelLabel: '取消',
        btnOKLabel: '重置',
        btnOKClass: 'btn-warning',
        callback: function (result) {
            if (result) {
                $.ajax({
                    type: "POST",
                    url: AuthUser.ctxPath + AuthUser.url + "resetPassword.do",
                    data: {ids: ids},
                    dataType: "json",
                    success: function (data) {
                        if (data.result == true) {
                            BootstrapDialog.alert({
                                type: BootstrapDialog.TYPE_WARNING,
                                title: '提示',
                                message: "重置成功！",
                                buttonLabel: "确定"
                            });
                            // 刷新页面
                            AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
                        } else {
                            BootstrapDialog.alert({
                                type: BootstrapDialog.TYPE_WARNING,
                                title: '提示',
                                message: data.message,
                                buttonLabel: "确定"
                            });
                        }

                    },
                    error: function () {
                        alert("asd")
                    }
                });
            }
        }
    });

};


AuthUser.changePassword = function () {
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
    } else {
        AuthUser.verifyPassword(arrays[0]);
    }

};

AuthUser.verifyPassword = function (id) {
    BootstrapDialog.show({
            title: '验证当前用户密码',
            type: BootstrapDialog.TYPE_DEFAULT,
            closable: true,
            closeByBackdrop: false,
            closeByKeyboard: false,
            size: BootstrapDialog.SIZE_SMALL,
            message: AuthUser.passwordVerify,
            buttons: [{
                label: '确定',
                action: function (dialogItself) {
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
                    })
                }
            },
                {
                    label: '关闭',
                    action:

                        function (dialogItself) {
                            dialogItself.close();
                        }
                }
            ]
        }
    );
};
AuthUser.setPassword = function (id) {
    BootstrapDialog.show({
            title: '更改用户密码',
            type: BootstrapDialog.TYPE_DEFAULT,
            closable: true,
            closeByBackdrop: false,
            closeByKeyboard: false,
            size: BootstrapDialog.SIZE_SMALL,
            message: AuthUser.passwordChange,
            buttons: [{
                label: '确定',
                action: function (dialogItself) {
                    $.ajax({
                        type: "POST",
                        url: AuthUser.ctxPath + AuthUser.url + "changePassword.do",
                        data: "id="+id+"&"+$("#formParams").serialize(),
                        dataType: "json",
                        success: function (data) {
                            console.log($("#formParams").serialize())
                            if (data.result == "true") {
                                BootstrapDialog.alert({
                                    type: BootstrapDialog.TYPE_WARNING,
                                    title: '提示',
                                    message: "重置成功！",
                                    buttonLabel: "确定"
                                });
                                dialogItself.close();
                                // 刷新页面
                                AuthUser.loadData(null, AuthUser.ctxPath + AuthUser.url + "list.html", 1);
                            } else {
                                $("#msg").text(data.message);
                            }


                        }
                    })
                }
            },
                {
                    label: '关闭',
                    action:

                        function (dialogItself) {
                            dialogItself.close();
                        }
                }
            ]
        }
    );
}


