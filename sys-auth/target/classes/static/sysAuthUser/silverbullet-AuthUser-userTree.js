$(function(){
    function getTree(){
        var tree = [
            {
                text: "Parent 1",
                nodes: [
                        {text: "Child 1", nodes: [{text: "Grandchild 1"}, {text: "Grandchild 2"}]},
                        {text: "Child 2"}
                        ]
            },
        ];
        return tree;
    }
    $('#authUserTree').treeview({
        data: getTree(),
        multiSelect: true,
        showBorder: false,
        selectedBackColor: '#fff',
        onhoverColor: '#fff',
        selectedColor: '#333'
    });
});
