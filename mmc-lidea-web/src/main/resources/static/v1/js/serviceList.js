
function bindDataToServices(resp) {

    let tpl = $("#tplRow").html();

    for (let i = 0; i < resp.data.length; i++) {
        let d = resp.data[i];
        let html = Nora.Util.StringUtil.format(tpl,
            d.appName,
            d.serviceName,
            Nora.Util.StringUtil.format("<span class='tdClass' data-appName='{}' data-serviceName='{}' onclick='openMethodPage(this)'>查看</span>", d.appName, d.serviceName)
        );
        $("#divContainer").append(html);

    }

    $("#tbList").basictable();
}

function openMethodPage(sender) {

    let appName = $(sender).attr("data-appName");
    let serviceName = $(sender).attr("data-serviceName");

    window.location.href = "/lidea/methodList?appName=" + appName + "&serviceName=" + serviceName;
    //$("#frmMethodList").submit();
}