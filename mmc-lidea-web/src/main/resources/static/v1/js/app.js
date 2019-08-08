
function bindDataToGallery(resp) {

    for (let i = 0; i < resp.data.length; i++) {

        let d = resp.data[i];

        let tpl = $("#divTpl").html();
        let html = Nora.Util.StringUtil.format(tpl, d.appName, d.appName);

        $("#divContainer").append(html);
    }

    $(".img-holder").click(function (e) {

        let appName = $(this).attr("data-name");
        $("#txtAppName").val(appName);

        $("#frmMethodList").submit();



    });
}

