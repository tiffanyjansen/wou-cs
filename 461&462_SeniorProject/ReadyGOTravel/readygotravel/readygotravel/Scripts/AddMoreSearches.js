$('#submited').on('click', function () {
    addElements();
});

//addElements();
function addElements() {
    //this presents the total number of searches displayed (by sets of 3)
    var TotalElements = $('#TotalElements').val().toString();
    console.log("TotalElement at start of js: " + TotalElements);
    var source = "/Searches/AddSearches/" + TotalElements;
    console.log(source);
    $.ajax({
        type: "GET",
        dataType: "json",
        url: source,
        success: Done,
        error: detectedError
    });
}

function Done(data) {
    console.log("------success----");

    //setup information needed to be displayed
    var startDates = data["StartDates"]
    var endDates = data["EndDates"];
    var numTravelers = data["NumTravelers"];
    var maxAmounts = data["MaxAmounts"];
    var startAirports = data["StartAirports"];
    var endAirports = data["EndAirports"];
    var startAirportCodes = data["StartAirportCodes"];
    var endAirportCodes = data["EndAirportCodes"];
    var hotelStarValues = data["HotelStarValues"];
    var averageFlightAmounts = data["AverageFlightAmounts"];
    var averageHotelAmounts = data["AverageHotelAmounts"];
    var averageFoodCosts = data["AverageFostCosts"];
    var averageHotelStars = data["AverageHotelStars"];
    var startCountry = data["StartCountry"];
    var endCountry = data["EndCountry"];


    //setup up rows for the searches table and add them
    for (i = 0; i < startDates.length; i++) {
        var TableTr = $('<tr>');
        
        //setting up date format for startDate
        col1 = $('<td>').text(startDates[i]);

        //setting up date format for endDate
        var col2 = $('<td>').text(endDates[i]);

        var col3 = $('<td>').text(numTravelers[i]);
        var col4 = $('<td>').text("$" + maxAmounts[i]);
        
        var col5;

        //check if the search si hotel only.
        if (startAirports[i] == null)
            col5 = $('<td>').text("-");
        else 
            col5 = $('<td>').text(FormatLocationString(startCountry[i], startAirports[i], startAirportCodes[i]));

        var col6 = $('<td>').text(FormatLocationString(endCountry[i], endAirports[i], endAirportCodes[i]));

        var col7 = $('<td>').text(GetHotelStarValueText(hotelStarValues[i]));

        var resultButton = $('<td>').append($('<button class=" btn" data-toggle="collapse" data-target="#demo'+i+'">').text("Results"));

        TableTr.append(col1);
        TableTr.append(col2);
        TableTr.append(col3);
        TableTr.append(col4);
        TableTr.append(col5);
        TableTr.append(col6);
        TableTr.append(col7);
        TableTr.append(resultButton);

        //set result values to nothing if they are bad values.
        averageFlightAmounts[i] = FormatCost(averageFlightAmounts[i]);
        averageHotelAmounts[i] = FormatCost(averageHotelAmounts[i]);
        averageFoodCosts[i]  = FormatCost(averageFoodCosts[i]);
        if (averageHotelStars[i] == 0)
            averageHotelStars[i] = "-";

        //results panel dropdown setup.
        var Content = $('<hr /><h1><u>~Results~</u></h1><p>Average Flight Cost: ' + averageFlightAmounts[i] + '</p><p>Average Hotel Cost: ' + averageHotelAmounts[i] + '</p><p>Average Food Cost: ' + averageFoodCosts[i] + '</p><p>Average Hotel Star: ' + averageHotelStars[i] + '</p><hr />');

        var resultTableTr = $('<tr>').append($('<td colspan="8">').append($('<div id="demo'+i+'" class="collapse text-center">').append(Content)));

        //add 2 rows to table with the class "table"
        $('.table').append(TableTr);
        $('.table').append(resultTableTr);
    }

    //increment the value with TotalElements id to show table is larger
    $('#TotalElements').val(parseInt($('#TotalElements').val()) + 10);

    //decide whether to show to hide the "show more" button
    if (parseInt($('#TotalSearches').val()) > parseInt($('#TotalElements').val())) {
        $('#submited').show();
    }
    else {
        $('#submited').hide();
    }
    
}
function detectedError() {
    console.log("Error. Cause could be from AddSearches method in Searches controller. AddMoreSearches.js could also be the cause. Could also be that javascript isn't working for some other reason");
}

function FormatLocationString(country, airport, airportCode) {
    return (country + ", " + airport + " (" + airportCode + ")");
}

function GetHotelStarValueText(hotelStarValue) {
    if (hotelStarValue == 0)
        return "No Preference";
    else if (hotelStarValue == null)
        return "-";
    else
        return ((hotelStarValue - 1) + "-" + hotelStarValue);
}

function FormatCost(value) {
    if (value == null || value == 0)
        return "-";
    else
        return ("$" + parseFloat(value,10).toFixed(2));
}
