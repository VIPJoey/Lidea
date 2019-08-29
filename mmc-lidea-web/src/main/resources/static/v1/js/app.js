
function bindDataToGallery(resp) {

    for (let i = 0; i < resp.data.length; i++) {

        let d = resp.data[i];

        let tpl = $("#divTpl").html();
        let html = Nora.Util.StringUtil.format(tpl, d.appName, d.appName);

        $("#divContainer").append(html);
    }

    let mid = resp.data.length / 2;

    initContainer();
    setJump();
    setDrag();
    bindEvent();

    showImgAt(mid);

}

function openServicePage(e) {

    let appName = $(e).attr("data-name");
    $("#txtAppName").val(appName);

    window.location.href = "/lidea/serviceList?appName=" + appName;
    //$("#frmMethodList").submit();
}

