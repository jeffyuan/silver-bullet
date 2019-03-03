/*
/!**
 * 组织管理树状图总和
 *Created by OFG on 2018/4/26.
 *!/

var AuthOrganization = {
    'url': 'auth/sysauthorganization/',
    'datas' : {},
}


AuthOrganization.ctxPath = $(".logo").attr('href');




/!**
 * 弹出框树状图初始化
 *!/
AuthOrganization.checkPermissionTree = function(){

    $.ajax({
        type: "POST",
        url: AuthOrganization.ctxPath + "auth/sysauthactiontree/" + "tree.do",
        data: { },
        dataType: "json",
        success: function (data) {
            var treeList = [];
            $.each(data,function (k,v) {
                var treeNode={};
                console.log(v.children);

                treeNode["text"] = v.name;
                treeNode["id"] = v.id;
                treeNode["nodes"] = [];
                $.each(v.children,function (k,v) {
                    var childrenNode = {};
                    childrenNode["text"] = v.name;
                    childrenNode["id"] = v.id;
                    treeNode["nodes"].push(childrenNode);
                    if(v.children.length !== 0){
                        $.each(v.children,function (k,v) {
                            var childrensNode = {};
                            childrensNode["text"] = v.name;
                            childrensNode["id"] = v.id;
                            childrenNode["nodes"].push(childrensNode);
                        });
                    }
                })
                treeList.push(treeNode);

            })
            $('#organizationTree').treeview({
                data: treeList,
                multiSelect: true,
                showBorder: false,
                selectedBackColor: '#fff',
                onhoverColor: '#fff',
                selectedColor: '#333',
                showCheckbox: true,
                onNodeChecked: function(event, node){
                    var selectNodes = AuthOrganization.getChildrenTree(node);
                    if(selectNodes){
                        $('#treeview-checkable').treeview('checkNode', [ selectNodes,{ silent: true }]);
                    }
                },
                onNodeUnchecked: function (event, node) {
                    var selectNodes = AuthOrganization.getChildrenTree(node);
                    if(selectNodes){
                        $('#treeview-checkable').treeview('uncheckNode', [ selectNodes,{ silent: true }]);
                    }
                }
            });
        }
    });
}


AuthOrganization.getChildrenTree = function(node){
    var ts = [];
    if(node.nodes){
        for(x in node.nodes){
            ts.push(node.nodes[x].nodeId)
            if(node.nodes[x].nodes){
                var getNodeDieDai = getNodeIdArr(node.nodes[x]);
                for(j in getNodeDieDai){
                    ts.push(getNodeDieDai[j]);
                }
            }
        }
    }else{
        ts.push(node.nodeId);
    }
    return ts;
}


/!**
 *定位到当前分支
 *!/

AuthOrganization.TreeChildren = function(){
    $(document).on("click", function(e){
        var val = $(e.target).parent().attr("id")/!*.replace(/[^\u4e00-\u9fa5]/gi,"")*!/;
        console.log(val);
        $.ajax({
            type: "POST",
            url: AuthOrganization.ctxPath + "auth/sysauthactiontree/" + "check.do",
            data : val,
            dataType: "json",
            success: function(){
                console.log("TreeChildren+success");

            }

        })
    })

}




$(function(){
    AuthOrganization.TreeChildren();
    AuthOrganization.checkPermissionTree();
});*/
