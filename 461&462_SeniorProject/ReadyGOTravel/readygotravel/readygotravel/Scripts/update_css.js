window.addEventListener('load', updateCSS);

function updateCSS() {
    var sysMonth = new Date().getMonth();
    console.log(sysMonth);
    switch (sysMonth) {
        case 0:
            document.getElementById("update_css").href = "/Content/css-winter";
            break;
        case 1:
            document.getElementById("update_css").href = "/Content/css-winter";
            break;
        case 2:
            document.getElementById("update_css").href = "/Content/css-spring";
            break;
        case 3:
            $("<link rel=\"stylesheet\" href=\"/Content/css-spring\" />").insertBefore(".layout");
            //"body").prepend("<link rel=\"stylesheet\" href=\"/Content/css-spring\" />");
            break;
        case 4:
            document.getElementById("update_css").href = "/Content/css-spring";
            break;
        case 5:
            document.getElementById("update_css").href = "/Content/css-summer";
            break;
        case 6:
            document.getElementById("update_css").href = "/Content/css-summer";
            break;
        case 7:
            document.getElementById("update_css").href = "/Content/css-summer";
            break;
        case 8:
            document.getElementById("update_css").href = "/Content/css-fall";
            break;
        case 9:
            document.getElementById("update_css").href = "/Content/css-fall";
            break;
        case 10:
            document.getElementById("update_css").href = "/Content/css-fall";
            break;
        case 11:
            document.getElementById("update_css").href = "/Content/css-winter";
    }
}

//updateCSS();