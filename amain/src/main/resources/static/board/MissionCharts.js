/**
 * Created by OFG on 2018/10/10.
 */

var chart = {
    'url': 'amain/board/',
};

chart.ctxPath = $(".logo").attr('href');

/**
 *
 */
chart.getCharCommon = function(url, param) {
    var datas = [];
    $.ajax({
        type:'post',
        url: url,
        async: false,
        data: param,
        dataType:"json",
        success: function(data) {
            datas.push(data);
        }
    });

    return datas;
}

/**
 * 获取当前年月日
 */
chart.getData = function() {
    var now = new Date();
    return now.getFullYear()+'-'+(now.getMonth()+1)+'-'+now.getDate();
}


/**
 *  饼状图初始化
 * @param dom
 */
chart.setChartB = function (dom) {
    var char = echarts.init($(dom)[0]);
    var options = chart.getCharCommon(chart.ctxPath+chart.url+'chartB.do', {data: chart.getData()})
    char.showLoading();
    if(options[0] != null && options != ""){
        options[0].series[0].animationDelay = function (idx) {
            return Math.random() * 200;
        };
        char.hideLoading();
        char.setOption(options[0]);
    }
}



/**
 * 树状图初始化
 */
chart.setChartS = function (dom) {
    var char = echarts.init($(dom)[0]);
    char.showLoading();
    var options = chart.getCharCommon(chart.ctxPath+chart.url+'chartSZ.do', {data: new Date()})
    if(options[0] != null && options != ""){
        char.hideLoading();
        char.setOption(options[0]);
    }

}


/**
 * 手机端chart
 */
chart.phoneChart = function() {
    if(767 >= $(window).width()){
        $("#phoneChar").css({
            'width': '100%',
            'height': $(window).height()-100
        });

        $("#phoneChar .swiper-scrollbar").css({
            'height': '20%',
            'top': '40%',
            'right': '10px'
        });

        var swiper = new Swiper('.swiper-container', {
            direction: 'vertical',
            scrollbar: {
                el: '.swiper-scrollbar',
                hide: true
            },
        });
    }
}



$(function() {
    chart.phoneChart();
    setTimeout(function() {
        chart.setChartB("#chartB");
        chart.setChartS("#chartSZ");
    },500);
})