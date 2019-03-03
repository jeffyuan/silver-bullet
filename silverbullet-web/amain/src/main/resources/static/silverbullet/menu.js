/**
 * Created by OFG on 2018/8/10.
 */
var Menu = {
    'dashboard': ''
};
Menu.url = function(){
    return window.location.pathname;
};

Menu.menuActive = function(){
    var href = $("#menus").find("a");
    href.each(function(k,v){
        if($(this).attr("href") === Menu.url()){
            var dom = $(this).parent();
            dom.addClass("active");
            dom.parent().parent().addClass("active");
        }
    });
};
