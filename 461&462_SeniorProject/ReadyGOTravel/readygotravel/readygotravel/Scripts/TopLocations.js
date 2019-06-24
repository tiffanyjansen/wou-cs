$.getScript("~/Scripts/Traveling.js");

function FillSearchData(num) {
    var id = GetStateName(num);
    if (id == null) {

    }
    else {
        var source = "/TopLocations/FillData/" + id;   //the source for the json object.  
        $.ajax({ //ajax call
            type: "GET",
            dataType: "json",
            url: source,
            success: successAjax,
            error: errorAjax
        });
    }
}

function successAjax(list) {
    var json = JSON.parse(list); //parse the json object.
    if (json.length > 0) {
        var check = $('#StartAirportSpot select').length;
        if (check == 0) {
            addLabel('Start', 'Departing');
        }
        else {
            $('#StartAirportSpot select').empty(); //clear the current dropdown
        }
        i = 0 //counter for the while loop
        while (i < json.length) {
            var location = getDirectionString(json[i]["Direction"]); //get the location string.
            $('#StartAirportSpot select').append(getAirportOptions(location, json, i));
            i++; //increment the index for the json object.
        }
        if (check == 0) {
            $('#StartAirportSpot select').append('</select'); //add the closing for the select.
        }
    }
}

function errorAjax() {
    console.log("An error has occurred.");
}

function GetStateName(num) {
    switch (num) {
        case 1:
            return "Wyoming";
        case 2:
            return "Hawaii";
        case 3:
            return "Arizona";
        case 4:
            return "New York";
        case 5:
            return "California";
        case 6:
            return "Maryland";
        case 7:
            return "Massachusetts";
        case 8:
            return "Nevada";
        case 9:
            return "Illinois";
        case 10:
            return "Louisiana";
        case 11:
            return "Montana";
        case 12:
            return "Washington";
        default:
            return null;
    }
}