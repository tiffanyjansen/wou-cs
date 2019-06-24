var ajax_call = function () {
    clearTable();
    var pageURL = window.location.href;
    console.log(pageURL);
    var URLList = pageURL.split('/');
    var id = URLList[URLList.length - 1];
    console.log(id);
    var source = "/Ajax/Item/" + id;
    console.log(source);
    $.ajax({
        type: "GET",
        dataType: "json",
        url: source,
        success: successAjax,
        error: errorAjax
    });
}

function successAjax(bids) {
    console.log("IT WORKED");
    var json = JSON.parse(bids);
    console.log(json);
    $('#Bidss table').append('<tbody>');
    success = true;
    i = 0;
    while (i < json.length) {
        $('#Bids table').append('<tr><td>' + json[i]["Buyer"] + '</td><td>' + json[i]["Price"] + '</td></tr>');
        i++;
    }
    $('#Bids table').append('</tbody>');
}
function errorAjax() {
    console.log("Error");
}

function createTable() {
    $('#Bids').append('<table align="center" style="width: 80%;"/>');
    $('#Bids table').append('<thead><tr><th>' + 'Buyer' + '</th><th>' + 'Price' + '</th></tr></thead>');
}

function clearTable() {
    $('#Bids table').remove();
    createTable();
}

var interval = 5000;
console.log("interval = " + interval);
window.setInterval(ajax_call, interval);
console.log("We are in the Javascript!");