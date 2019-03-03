/**
 *@Auther: OFG
 *@Despriction:
 *@Data: Created in 16:07 2018/11/6
 *@Modify By:
 **/

var TreeView = {

}


/**
 * 渲染树结构
 * @param uid
 * @param dom
 * @param url
 */
TreeView.tree = function(uid, dom, url){

    //填充树
    $(dom).treeview({

        data: TreeView.treeBuild(uid, url),
        showBorder: false,
        levels:1,

        loadingIcon:"fa fa-hourglass",
        selectedBackColor: '#454849',
        selectedColor: '#fff',
        showCheckbox: false,
        backColor: '#f2f2f2',
        onhoverColor: '#e0e0e0',
        lazyLoad: function(node,addNode){
            TreeView.node = node;
            TreeView.treeLazyLoad(node, url, dom);
        }
    });

    return TreeView.node;
}


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
 */

TreeView.treeBuildCommon = function(data, pos){

    var treeNode = {};

    //构造树
    if(data.type == 1) {
        treeNode["lazyLoad"] = true;
    }
    treeNode["text"] = data.name;
    treeNode["id"] = data.actionId != undefined? data.actionId : data.id;
    treeNode["icon"] = data.icon;
    treeNode["sort"] = data.sort;
    treeNode["parentuid"] = data.parentId;
    treeNode["position"] = data.pos;

    return treeNode;
}



/**
 * 节点懒加载
 * @param node
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
 *节点添加方法
 * @param data
 */
TreeView.addNode = function(data, dom,node){
    $(dom).treeview("addNode", [data,node]);
}


/**
 * 动态异步刷新某节点下的子项
 * @param node
 */
TreeView.treeNode = function(node, uid, url){
    for(var i =0; i<node.nodes.length; i++){
        var id = node.nodes[i].id;
        $(uid+" ul").find("li[id='"+id+"']").remove();
    }

    TreeView.node.nodes = [];
    TreeView.treeLazyLoad(node, url, uid);
}

