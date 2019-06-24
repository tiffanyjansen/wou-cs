function on(num) {
    //Display overay panel
    document.getElementById("overlay" + num).style.display = "block";

    //Make background unscrollable
    $('body').css("overflow", "hidden");

    //check to see if the information is already displayed
    if ($('.star' + num).children().length == 0) {

        //add loading text
        //var loadText = $('<p>').text("Loading ...");
        var loadCircle = $('<div>', {class:'loader'})
        $('.star' + num).append(loadCircle);

        var source = "/TopLocations/CostForHotelStars/" + num;
        console.log(source);
        $.ajax({
            type: "GET",
            dataType: "json",
            url: source,
            success: Done,
            error: detectedError
        });
    }
}

function off(num) {
    //hide overlay pannel
    document.getElementById("overlay" + num).style.display = "none";

    //Make background srollable
    $('body').css("overflow", "auto");
}

function Done(data) {
    //setup the data retrived.
    var locationID = data["locationID"];
    var costsByStar = data["CostsByStar"];

    //clear the loading text.
    $('.star' + locationID).empty();

    console.log("------success----(from FavoritesOverlay.js)");
    var mainTable = $('<table>');

    //Table header setup.
    var headRow = $('<tr>');
    var headLabel1 = $('<th>').text("Star Rating");
    var headLabel2 = $('<th>').text("Cost per Day");
    headRow.append(headLabel1);
    headRow.append(headLabel2);
    mainTable.append(headRow);

    //add cost by star value to view.
    for (i = 0; i < 5; i++) {
        var row = $('<tr>');
        var displayStars = $('<td>');
        var displayCost = $('<td>');

        //add correct amount of stars
        for (j = 0; j < i + 1; j++)
            $('<img/>', { src:'/Content/Images/Star.PNG', width:'30px', height:'20px'}).appendTo(displayStars);
        if (costsByStar[i] == 0)
            displayCost.text("No value found.");
        else
            displayCost.text("$" + costsByStar[i]);

        row.append(displayStars);
        row.append(displayCost);
        mainTable.append(row);
    }

    $('.star' + locationID).append(mainTable);

    //add disclaimer at bottom of the table.
    var textStuff = $('<p>').text("Hotel costs are calculated by a single nights stay a day after this page was viewed.");
    textStuff.prepend($('<b>').text("Disclaimer: "));
    $('.star' + locationID).append(textStuff);


    $("input.closeButton" + locationID).prop("disabled", false);
    $(".tempText" + locationID).text("");
}


function detectedError() {
    console.log("Error detected from FavoritesOverlay.js ");
    //clear loading text.
    $('.star' + 2).empty();
    //display error text.
    var errorText = $('<p>').text("Sorry, it looks like there was an error loading the data here.");
    $('.star' + 2).append(errorText);
}