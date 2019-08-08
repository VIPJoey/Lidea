function bindDataToMethods(resp) {

    let tpl = $("#tplRow").html();

    for (let i = 0; i < resp.data.length; i++) {
        let d = resp.data[i];
        let html = Nora.Util.StringUtil.format(tpl,
            d.time,
            d.serviceName,
            d.methodName,
            Nora.Util.StringUtil.format("<span class='.opClass' onclick='showDetail(this)'  data-appName='{}' data-service='{}' data-method='{}'>操作</span>", d.appName, d.serviceName, d.methodName)
        );
        $("#tbList tbody").append("<tr>" + html + "</tr>");

    }

    $("#tbList").basictable();

}

function showDetail(e) {

    let appName = $(e).attr("data-appName");
    let serviceName = $(e).attr("data-service");
    let methodName = $(e).attr("data-method");

    $("#txtAppName").val(appName);
    $("#txtServiceName").val(serviceName);
    $("#txtMethodName").val(methodName);
    $("#frmDetail").submit();

}
