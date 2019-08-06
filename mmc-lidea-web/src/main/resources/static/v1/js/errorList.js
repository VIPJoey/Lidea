

function bindDataToException(resp) {

    let tpl = $("#tplRow").html();

    for (let i = 0; i < resp.data.length; i++) {
        let d = resp.data[i];
        let html = Nora.Util.StringUtil.format(tpl,
            d.time,
            d.serviceName,
            d.methodName,
            wrap(d.traceId),
            d.localIp,
            d.remoteIp,
            d.cost,
            d.args,
            d.response,
            d.msg,
            d.customMsg

        );
        $("#tbList tbody").append("<tr>" + html + "</tr>");
    }

    $("#tbList").basictable();
}

function wrap(src) {

    return (null == src) ? "" : src;
}