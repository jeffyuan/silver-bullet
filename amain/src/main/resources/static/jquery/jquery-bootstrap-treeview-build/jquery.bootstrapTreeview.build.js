/**
 *@Auther: OFG
 *@Despriction:
 *@Data: Created in 16:07 2018/11/6
 *@Modify By:
 **/
(function ($) {
    var TreeView = {

    }

    TreeView.ctxPath = $(".logo").attr('href');


    /**
     * 构建树结构
     * @param uid
     * @param url
     * @returns {Array}
     */

    TreeView.treeBuild = function(uid, url){

        //声明树结构
        var treeList = [];

        $.ajax({
            type: "post",
            async:false,
            url: url,
            data: {parentId: uid},
            dataType: "json",
            success: function(data) {
                data = data.result.resultList;
                $.each(data, function(k,v){
                    if(v == data[0]){
                        v["pos"] = 1;
                    }else if(v == data[data.length-1]){
                        v["pos"] = 0;
                    }else{
                        v["pos"] = '#';
                    }
                    treeList.push(TreeView.treeBuildCommon(v));
                });

            }
        });
        return treeList;
    }


    /**
     * 构造tree数据结构通用方法
     * @param data
     * @param pos
     */
    TreeView.treeBuildCommon = function(data, pos){

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

        return treeNode;
    }


    /**
     * 节点懒加载
     * @param node
     * @param url
     * @param dom
     */
    TreeView.treeLazyLoad = function(node, url, dom){
        var treeList = [];

        $.ajax({
            type: "post",
            async:false,
            url: url,
            data: {parentId: node.id},
            dataType: "json",
            success: function(data){
                data = data.result.resultList;
                $.each(data, function(k,v){
                    if(v == data[0]){
                        v["pos"] = 1;
                    }else if(v == data[data.length-1]){
                        v["pos"] = 0;
                    }else{
                        v["pos"] = '#';
                    }
                    treeList.push(TreeView.treeBuildCommon(v));
                });
                TreeView.addNode(treeList, dom ,node);
            }
        });

    }


    /**
     * 节点添加方法
     * @param data
     * @param dom
     * @param node
     */
    TreeView.addNode = function(data, dom, node){
        dom.treeview("addNode", [data,node]);
    }


    /**
     * 动态异步刷新某节点下的子项
     */
    $.extend({
        treeNode: function(options){
            var defaults = {
                dom: '',
                url: ''
            };

            var $opts = $.extend(defaults, options);

            for(var i =0; i<TreeView.node.nodes.length; i++){
                var id = TreeView.node.nodes[i].id;
                $opts.dom.find("li[id='"+id+"']").remove();
            }

            TreeView.node.nodes = [];
            TreeView.treeLazyLoad(TreeView.node, $opts.url, $opts.dom);
        }
    })


    /**
     * 渲染树结构
     * @param options
     */
    TreeView.tree = function(options){

        //填充树
        var node = options.dom.treeview({

            data: TreeView.treeBuild(options.uid, options.url),
            showBorder: options.showBorder,
            levels: options.levels,
            loadingIcon: options.loadingIcon,
            selectedBackColor: options.selectedBackColor, /*#454849*/
            selectedColor: options.selectedColor,
            showCheckbox: options.showCheckbox,
            backColor: options.backColor,
            onhoverColor: options.onhoverColor,
            lazyLoad: function(node,addNode){
                TreeView.treeLazyLoad(node, options.url, options.dom);
                TreeView.node = node;
            }
        });
    }


    /**
     * 加载插件
     * @param options
     */
    $.fn.bsTableBuild = function (options){

        var defaults = {
            uid: 'NONE',
            dom: this,
            url: '',
            selectedBackColor: '#454849',
            loadingIcon: 'fa fa-hourglass',
            levels:1,
            showBorder: false,
            selectedColor: '#fff',
            showCheckbox: false,
            backColor: '#f2f2f2',
            onhoverColor: '#e0e0e0'
        };

        var $opts = $.extend(defaults, options);

        TreeView.tree($opts);
    }


})(window.jQuery)
