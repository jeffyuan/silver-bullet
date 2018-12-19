/**
 *@Auther: OFG
 *@Despriction:
 *@Data: Created in 16:07 2018/11/6
 *@Modify By:
 **/
(function ($) {

    var TreeView = {
        uid: 'NONE',
        url: undefined,
        node: undefined,
        selectedBackColor: '#454849',
        loadingIcon: 'fa fa-hourglass',
        levels: 1,
        showBorder: false,
        selectedColor: '#fff',
        showCheckbox: false,
        backColor: '#f2f2f2',
        onhoverColor: '#e0e0e0',
        uncheckedIcon: 'fa fa-circle-o',
        checkedIcon: 'fa fa-dot-circle-o',
        nodeChecked: undefined,
        nodeUnchecked: undefined,
        nodeExpanded: undefined,  /*展开*/
        nodeCollapsed: undefined,  /*折叠*/
        postId: undefined,
        ActionList: undefined
    }

    var treeNode = {
        dom: undefined,
        url: undefined
    }

    /**
     * 通用ajax
     * @param type
     * @param url
     * @param data
     */
    TreeView.getData = function(type, url, data){
        var datas = [];

        $.ajax({
            type: type,
            async: false,
            url: url,
            data: data,
            dataType: "json",
            success: function(data){
                TreeView.ActionList = data.list != null? TreeView.postActionChange(data.list):undefined;
                datas = data.result.resultList;
            }
        });

        return datas;
    }

    /**
     * 将postAction转换为actionList
     * @param data
     */
    TreeView.postActionChange = function(data){
        var list = [];

        $.each(data, function(k,v){
            list.push(v.actionId);
        });

        return list;
    }




    /**
     * 构建树结构
     * @param uid
     * @param url
     * @returns {Array}
     */
    TreeView.treeBuild = function(option){

        //声明树结构
        var treeList = [];

        // 获取树信息
        var datas = TreeView.getData('post', option.url, {parentId: option.uid, postId: option.postId});

        if(datas[0] != ""){
            $.each(datas, function(k,v){
                if(v == datas[0]){
                    v["pos"] = 1;
                }else if(v == datas[datas.length-1]){
                    v["pos"] = 0;
                }else{
                    v["pos"] = '#';
                }

                if($.inArray(v.id, TreeView.ActionList) >= 0){
                    v.state = {};
                    v.state.checked = true;
                }else{
                    v.state = {};
                }

                treeList.push(TreeView.treeBuildCommon(v));
            });
        }

        return treeList;
    }


    /**
     * 构造tree数据结构通用方法
     * @param data
     * @param pos
     */
    TreeView.treeBuildCommon = function(data){

        var treeNode = {};

        //构造树
        if(data.type == 1) {
            treeNode["lazyLoad"] = true;
        }
        treeNode["text"] = data.name;
        treeNode["id"] = data.id;
        treeNode["icon"] = data.icon;
        treeNode["sort"] = data.sort;
        treeNode["parentuid"] = data.parentId;
        treeNode["position"] = data.pos;
        treeNode["state"] = data.state;

        return treeNode;

    }


    /**
     * 节点懒加载
     * @param node
     * @param url
     * @param dom
     */
    TreeView.treeLazyLoad = function(node, option){

        //声明树结构
        var treeList = [];

        // 获取树信息
        var datas = TreeView.getData('post', option.url, {parentId: node.id, postId: option.postId});

        if(datas != ""){
            $.each(datas, function(k,v){
                if(v == datas[0]){
                    v["pos"] = 1;
                }else if(v == datas[datas.length-1]){
                    v["pos"] = 0;
                }else{
                    v["pos"] = '#';
                }

                if($.inArray(v.id, TreeView.ActionList) >= 0){
                    v.state = {};
                    v.state.checked = true;
                }else{
                    v.state = {};
                }
                treeList.push(TreeView.treeBuildCommon(v));
            });
            TreeView.addNode(treeList, option.dom ,node);
        }
    }


    /**
     * 动态修改节点选中状态
     * @param node
     * @param url
     * @param dom
     * @param status
     */
    TreeView.treeNodeCkeckStatus = function(node, url, dom, status) {

    }


    /**
     * 节点添加方法
     * @param data
     * @param dom
     * @param node
     */
    TreeView.addNode = function(data, dom, node){
        dom.treeview("addNode", [ data, node ]);
    }




    /**
     * 动态异步刷新某节点下的子项
     */
    $.extend({
        treeNode: function(options){

            var $opts = $.extend(treeNode, options);

            for(var i =0; i<TreeView.node.nodes.length; i++){
                var id = TreeView.node.nodes[i].id;
                $opts.dom.find("li[id='"+id+"']").remove();
            }

            TreeView.node.nodes = [];
            TreeView.treeLazyLoad(TreeView.node, $opts.url, $opts.dom);
        }
    })


    /**
     * 事件通用方法
     * @param options
     */
    TreeView.event = function($opts, event) {

        $opts.dom.on(event[0], function(event, node){
            if('undefined' === typeof(node.nodes)){
                return ;
            }

            var treeList = node.nodes;
            $.each(treeList, function(k,v){
                event.type === "nodeChecked" ?
                    v.state["checked"] = true :
                    v.state["checked"] = false;
            })
            TreeView.node.nodes = [];
            TreeView.addNode(treeList,$opts.dom, TreeView.node);
        });
    }



    /**
     * 渲染树结构
     * @param options
     */
    TreeView.tree = function(options){
        //填充树
        var node = options.dom.treeview({
            data: TreeView.treeBuild(options),
            showBorder: options.showBorder,
            levels: options.levels,
            loadingIcon: options.loadingIcon,
            selectedBackColor: options.selectedBackColor, /*#454849*/
            selectedColor: options.selectedColor,
            showCheckbox: options.showCheckbox,
            backColor: options.backColor,
            onhoverColor: options.onhoverColor,
            uncheckedIcon: options.uncheckedIcon,
            checkedIcon: options.checkedIcon,
            lazyLoad: function(node,addNode){
                TreeView.treeLazyLoad(node, options);
                TreeView.node = node;
            }
        });


        // checked
        TreeView.event(options, ['nodeChecked', 'checkNode']);
        //nodeUnchecked
        TreeView.event(options, ['nodeUnchecked', 'uncheckNode']);
    }


    /**
     * 加载插件
     * @param options
     */
    $.fn.bsTableBuild = function (options){

        var defaults = {
            uid: TreeView.uid,
            dom: this,
            url: TreeView.url,
            selectedBackColor: TreeView.selectedBackColor,
            loadingIcon: TreeView.loadingIcon,
            levels: TreeView.levels,
            showBorder: TreeView.showBorder,
            selectedColor: TreeView.selectedColor,
            showCheckbox: TreeView.showCheckbox,
            backColor: TreeView.backColor,
            onhoverColor: TreeView.onhoverColor,
            uncheckedIcon: TreeView.uncheckedIcon,
            checkedIcon: TreeView.checkedIcon,
            nodeChecked: TreeView.nodeChecked,
            nodeUnchecked: TreeView.nodeUnchecked,
            nodeExpanded: TreeView.nodeExpanded,
            nodeCollapsed: TreeView.nodeCollapsed,
            postId: TreeView.postId
        };

        var $opts = $.extend(defaults, options);

        TreeView.tree($opts);
    }


})(window.jQuery)
