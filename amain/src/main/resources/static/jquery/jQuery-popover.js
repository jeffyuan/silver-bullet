/**
popover v1.0
creat by ofg in 2018/3/30
*/

(function($) {
    var $DOM = $('<div id="toolTip"><i id="fa"></i><span id="toolTipCon"></span></div>');
    var $time;
    $("body").append($DOM);
    $.fn.toolTip = function(options) {
        var $toolTip = $("#toolTip");
        var $opts = $.extend(defaults, options);
        $toolTip.css({
            "width": "300px",
            "height": "40px",
            "background": "rgba(0,0,0,0.5)",
            "font-size": "16px",
            "color": "#fff",
            "text-align": "center",
            "line-height": "40px",
            "animation-duration": "0.5s",
            "user-select": "none",
            "position": "fixed",
            "top": "60px",
            "bottom": "0",
            "left": "40%",
            "z-index": "10001",
            "border-radius": "5px"
        });
        if (document.body.clientWidth <= 800) {
            $toolTip.css({
                "width": "90%",
                "left": "5%",
                "top": "5px"
            })
        }
        var defaults = {
            con: "成功",
            style: "info"
        };
        var $dic = {
            "success": "#00A65A",
            "warning": "#F39C12",
            "danger": "#DD4B39",
            "info": "#00C0EF"
        };
        var $fadic = {
            "success": " fa-check",
            "warning": " fa-exclamation",
            "danger": " fa-close"
        };
        $toolTip.css("background", $dic[$opts.style]);
        $toolTip.hide().removeClass("fadeInDown fadeOutUp");
        $(function() {
            $("#toolTipCon").html($opts.con);
            $toolTip.show().addClass("animated fadeInDown");
            $("#fa").removeClass().addClass("fa fa-lg fa-fw" + $fadic[$opts.style]);
            var $time = setTimeout(function() {
                $toolTip.removeClass("fadeInDown").addClass("animated fadeOutUp")
            },
            1000)
        });
        $toolTip.hide();
        $toolTip.on("click",
        function() {
            $toolTip.removeClass("fadeInDown").addClass("animated fadeOutUp")
        })
    }
})(jQuery);

