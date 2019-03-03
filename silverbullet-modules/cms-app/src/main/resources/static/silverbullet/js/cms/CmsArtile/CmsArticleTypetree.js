/**
 * Created by jeffyuan on 2018/3/21.
 */
var Cms = {
    'url': 'cms/cmsarticletypetree/'
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
 * 表格头部添加方法
 */
Cms.add = function(obj) {
    var parentId;
    if (typeof obj == 'string') {
        parentId = obj;
    } else {
        parentId = $(obj).parent().parent().attr("data-u");
    }

    var dialogInfo = Cms.getHtmlInfo(Cms.ctxPath + Cms.url + 'add.html',  {parentId: parentId});
    layer.open({
        type: 1,
        title: '编辑',
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
            Cms.save(Cms.ctxPath + Cms.url + 'save.do', index);

            return false;
        }
    });
};

/**
 * 编辑和创建保存方法
 * @param url
 * @param layerIndex
 */
Cms.save = function(url, layerIndex) {
    $.ajax({
        type: "post",
        url: url,
        data: $("#formCms").serialize(),
        dataType: "json",
        success: function(data) {
            if (data.result == true) {
                layer.msg(data.message);

                // 刷新页面
                Cms.initTable();

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
        layer.msg('请选择一条需要修改的数据。');
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
    layer.open({
        type: 1,
        title: '编辑文章',
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
            Cms.save(Cms.ctxPath + Cms.url + 'save.do', index);

            return false;
        }
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
        layer.msg('请选择至少一条需要删除的数据。');
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
    layer.confirm('是否确定删除?', {icon: 3, title:'提示'}, function(index){
        $.ajax({
            type: "POST",
            url: Cms.ctxPath + Cms.url + "delete.do",
            data: { ids: ids },
            dataType: "json",
            success: function (data) {
                if (data.result == true) {
                    layer.msg("删除成功！");
                    layer.close(index);
                    // 刷新页面
                    window.location.reload();

                } else {
                    layer.msg(data.message);
                }
            }
        });
    });
};

Cms.checkboxInit = function() {
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

Cms.initTable = function() {
    var columns = [
        {field: 'selectItem', checkbox: true},
        {title: '名称', field: 'name', align: 'left', valign: 'middle'},
        {title: '排序', field: 'sort', align: 'center', valign: 'middle', sortable: true},
        {title: '类型', field: 'type', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'comments', align: 'left', valign: 'middle', sortable: true},
        {title: '操作', field: 'lastControl', fieldVal:'<button name="添加子元素" class="btn btn-default btn-sm" onclick="Cms.add(this)" space="true"><i class="fa fa-fw fa-plus"></i></button><button name="修改" class="btn btn-default btn-sm" onclick="Cms.editOne(this)" space="true"><i class="fa fa-fw fa-edit"></i></button><button name="删除" class="btn btn-default btn-sm" onclick="Cms.deleteOne(this)" space="true"><i class="fa fa-fw fa-trash-o"></i></button>'}];

    $('#data-list').bootstrapAjaxTreeTable({
        id: "id",// 选取记录返回的值
        code: "id",// 用于设置父子关系
        parentCode: "parent_id",// 用于设置父子关系
        rootCodeValue: "NONE", //设置根节点code值----可指定根节点，默认为null,"",0,"0"
        hasChildren: "children_num",  //是否存在子节点
        type: "POST", //请求数据的ajax类型
        url: Cms.ctxPath + Cms.url + "list.do",   //请求数据的ajax的url
        ajaxParams: {}, //请求数据的ajax的data属性
        expandColumn: 1,//在哪一列上面显示展开按钮,从0开始
        striped: true,   //是否各行渐变色
        expandAll: true,  //是否全部展开
        columns: columns,		//列数组
        toolbar: "#" + this.toolbarId,//顶部工具条
        height: this.height,
        callBackFun: Cms.checkboxInit,   //回调处理一些操作的方法
    });
}

$(function () {
    Cms.initTable();
});
