$("#icon_choose").on("click", function(){
    $("#iconContent").show({duration: 100,});
});

$(document).on("click", function(){
    $("#iconContent").hide({duration: 100,});
});
$("#icon_choose").on("click", function(){
    event.stopPropagation();
    $(this).transition({ 'border-color':'#3c8dbc' });
});

$(".boxe tr th").on("click", function(){
    var className = $(this).find("i").attr('class');
    $("#icon_choose").val(className.replace('fa-2x',''));
});




