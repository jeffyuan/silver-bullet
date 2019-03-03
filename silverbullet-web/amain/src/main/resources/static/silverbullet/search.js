/**
 * Created by OFG on 2018/7/31.
 */


var ctxPath = $(".logo").attr('href');


/**
 * 根据select变更输入控件
 */
function select(dom){

    var data_control = $(dom).children('option:selected').attr("data-control");
    var searchObj = $(dom).attr("data-obj");
    var refresh = $(dom).attr("data-re");
    console.log(data_control);

    $.ajax({
        type:'post',
        url: ctxPath + 'control.html',
        dataType:"html",
        data: {"controller": data_control, 'obj': searchObj, 'refresh': refresh},
        beforeSend: beforeSend,
        success: function (data) {
            console.log(data);
            console.log();
            $(dom).parent().parent().find('div[id="box-tools"]').html(data);
            // $("#selectKey").parent().parent().find('div[id="model-box-tools"]').html(data);
        },

    });
}

    /**
     * 字符串重新组合
     * @param text
     * @returns {Array|*}
     * Eg: a_bcd -> aBcd
     */
    function textFactory (text){
        var e = text.split('_');
        if(e.length == 1){
            e = e[0];
        }else{
            e = e[0] + letterCapital(e[1]);
        }

        return e;
    }


    /**
     *英文首字母大写
     * @param e
     * @returns {string}
     * Eg: abc -> Abc
     */
    function letterCapital (e){
        var str = e.toLowerCase();
        str = str.split(' ');
        var result = '';
        for(var i in str){
            result += str[i].substring(0,1).toUpperCase()+str[i].substring(1)+' ';
        }

        return result;
    }

