/**
 * Created by GESOFT on 2018/3/20.
 */

var amq = org.activemq.Amq;
/**
 * 初始化消息
 * @pageId 为区分页面功能id
 * @clieentId 为用户id，用于过滤消息。如果为null或者空，则不进行消息过滤
 * @topic topic 名称
 * @myHandler 用于处理回调消息的方法
 */
function msgInit(pageId, clientId, topic, myHandler) {

    var ctxPath = $(".logo").attr('href');

    // 初始化，clientId保证浏览器唯一
    //In amq.init, clientId serves to distinguish different web clients sharing the same JSESSIONID.
    // All windows in a single browser need a unique clientId when they call amq.init.
    amq.init({
        uri: ctxPath + 'amq',
        logging: true,
        timeout: 1,
        clientId: (new Date()).getTime().toString()
    });

    //In amq.addListener, clientId is used to associate a message subscription with the callback function
    // which should be invoked when a message is received for that subscription.
    // These clientId values are internal to each web page, and do not need to be unique across multiple windows or tabs.
    if (clientId != null && clientId.length > 0) {
        amq.addListener(pageId,"topic://" + topic, myHandler, { selector:"id='" + clientId + "' or id='all'" });
    } else {
        amq.addListener(pageId,"topic://" + topic, myHandler );
    }
}

/**
 * 发送消息
 * @topic topic 名称
 * @msg 待发布的内容，可以为字符串或者序列化内容
 */
function sendMsg(topic, msg) {
    //alert(msg);
    amq.sendMessage("topic://" + topic, msg);
}
