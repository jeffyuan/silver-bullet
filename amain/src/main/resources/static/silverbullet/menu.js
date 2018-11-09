/**
 * Created by OFG on 2018/8/10.
 */

var Menu = {
    'dashboard': ''
}


Menu.url = function(){
    return window.location.pathname;
}

Menu.menu = function(e){
    var href = $(e).find("a");
    href.each(function(k,v){
        if($(this).attr("href") === Menu.url()){
            var dom = $(this).parent();
            dom.addClass("active")
            dom.parent().parent().addClass("active");
        }
    });

}


$(function(){
    Menu.menu("#menus");
});
