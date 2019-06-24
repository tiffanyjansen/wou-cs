/* This is the code for the changing of the Continents.
 * Anything that has "Start Continent" is meant for the continent of departure,
 * while "End Continent" if for the continent of arrival. */

/*
 * These methods are for sending the Ajax back to the controller.
 * Their main focus is to watch for changes in the drop-downs.
 */
$('#StartContinent').change(function () {
    var id = $('#StartContinent').val().toString(); //get the country name.    
    var source = "/Searches/FindCountries/" + id;   //the source for the json object.  
    console.log("We noticed the change.");
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successStartCountryAjax,
        error: errorAjax
    });
});
$('#EndContinent').change(function () {
    var id = $('#EndContinent').val().toString(); //get the country name.
    var source = "/Searches/FindCountries/" + id; //the source for the json object.    
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successEndCountryAjax,
        error: errorAjax
    });
});
/*
 * These methods are for receiving the Ajax Success response from the controller.
 * They receive a JSON object and parse that to change the data.
 * Their main focus is to change the drop down to the current countries. 
 */
function successStartCountryAjax(countries) {
    var json = JSON.parse(countries); //parse the json object.
    console.log(json.length);
    if (json.length > 0) {
        $('#StartCountry').empty(); //This clears the Drop Down.
        $('#StartCity').empty(); //This clears the cities Drop Down.
        $('#StartAirport').empty(); //clear the airport dropdown
        i = 0; //counter for the while loop
        while (i < json.length) {
            $('#StartCountry').append(getOptions(json, i));
            i++; //increment the counter
        }
        var id = $('#StartCountry').val().toString(); //get the country name. 
        console.log("(Start Country id = " + id);
        var source = "/Searches/FindCities/" + id;   //the source for the json object.                  
        $.ajax({ //ajax call
            type: "GET",
            dataType: "json",
            url: source,
            success: successStartCityAjax,
            error: errorAjax
        });
    }
}
function successEndCountryAjax(countries) {
    var json = JSON.parse(countries); //parse the json object.
    if (json.length > 0) {
        $('#EndCountry').empty(); //This clears the Drop Down.        
        $('#EndCity').empty(); //This clears the cities Drop Down.
        $('#EndAirport').empty(); //clear the airport dropdown
        i = 0; //counter for the while loop
        while (i < json.length) {
            $('#EndCountry').append(getOptions(json, i));
            i++; //increment the counter
        }
        var id = $('#EndCountry').val().toString(); //get the country name.    
        console.log("(End Country id = " + id);
        var source = "/Searches/FindCities/" + id;   //the source for the json object.                  
        $.ajax({ //ajax call
            type: "GET",
            dataType: "json",
            url: source,
            success: successEndCityAjax,
            error: errorAjax
        });
    }
}

/* This is the code for the changing of the Countries.
 * Anything that has "Start Country" is meant for the country of departure,
 * while "End Country" if for the country of arrival. */

/*
 * These methods are for sending the Ajax back to the controller.
 * Their main focus is to watch for changes in the drop-downs.
 */ 
$('#StartCountry').change(function () {
    var id = $('#StartCountry').val().toString(); //get the country name.
    console.log("(Start Country id = " + id);
    var source = "/Searches/FindCities/" + id;   //the source for the json object.                  
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successStartCityAjax,
        error: errorAjax
    });
});
$('#EndCountry').change(function () {
    var id = $('#EndCountry').val().toString(); //get the country name.
    console.log("(End Country id = " + id);
    var source = "/Searches/FindCities/" + id; //the source for the json object.    
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successEndCityAjax,
        error: errorAjax
    });
});
/*
 * These methods are for receiving the Ajax Success response from the controller.
 * They receive a JSON object and parse that to change the data.
 * Their main focus is to change the drop down to the current cities. 
 */
function successStartCityAjax(cities) {    
    var json = JSON.parse(cities); //parse the json object.
    if (json.length > 0) {
        $('#StartCity').empty(); //This clears the Drop Down.
        $('#StartAirport').empty(); //clear the airport dropdown
        i = 0; //counter for the while loop
        while (i < json.length) {
            $('#StartCity').append(getOptions(json, i));
            i++; //increment the counter
        }
        var id = $('#StartCity').val().toString(); //get the city/state name.
        console.log("Start City id = " + id);
        var source = "/Searches/FindAirports/" + id; //the source for the json object.
        $.ajax({ //ajax call
            type: "GET",
            dataType: "json",
            url: source,
            success: successStartAirportAjax,
            error: errorAjax
        });
    }
}
function successEndCityAjax(cities) {    
    var json = JSON.parse(cities); //parse the json object.
    if (json.length > 0) {
        $('#EndCity').empty(); //This clears the Drop Down.
        $('#EndAirport').empty(); //clear the airport dropdown
        i = 0; //counter for the while loop
        while (i < json.length) {
            $('#EndCity').append(getOptions(json, i));
            i++; //increment the counter
        }
        var id = $('#EndCity').val().toString(); //get the city/state name.
        console.log("End City id = " + id);
        var source = "/Searches/FindAirports/" + id; //the source for the json object.
        $.ajax({ //ajax call
            type: "GET",
            dataType: "json",
            url: source,
            success: successEndAirportAjax,
            error: errorAjax
        });
    }
}

/* This is the code for the changing of the Cities/States.
 * Anything that has "Start City" is meant for the City/State of departure,
 * while "End City" if for the City/State of arrival. */

/*
 * These methods are for sending the Ajax back to the controller.
 * Their main focus is to watch for changes in the drop-downs.
 */
$('#StartCity').change(function () {
    var id = $('#StartCity').val().toString(); //get the city/state name.
    console.log("Start City id = " + id);
    var source = "/Searches/FindAirports/" + id; //the source for the json object.
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successStartAirportAjax,
        error: errorAjax
    });
});
$('#EndCity').change(function () {
    var id = $('#EndCity').val().toString(); //get the city name.
    console.log("End City id = " + id);
    var source = "/Searches/FindAirports/" + id; //the source for the json object.      
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successEndAirportAjax,
        error: errorAjax
    });
});
/*
 * These methods are for receiving the Ajax Success response from the controller.
 * They receive a JSON object and parse that to change the data.
 * Their main focus is to change the drop down to the current airports. 
 */
function successStartAirportAjax(airports) {
    console.log("We reached success for start airports");
    var json = JSON.parse(airports); //parse the json object.
    if (json.length > 0) {
        $('#StartAirport').empty(); //clear the airport dropdown
        i = 0 //counter for the while loop
        while (i < json.length) {
            var location = getDirectionString(json[i]["Direction"]); //get the location string.
            $('#StartAirport').append(getAirportOptions(location, json, i));
            i++; //increment the index for the json object.
        }
    }
}
function successEndAirportAjax(airports) {
    console.log("We reached success for end airports");
    var json = JSON.parse(airports); //parse the json object.
    if (json.length > 0) {        
        $('#EndAirport').empty(); //clear the airport dropdown
        i = 0 //counter for the while loop
        while (i < json.length) {
            var location = getDirectionString(json[i]["Direction"]); //get the location string.
            $('#EndAirport').append(getAirportOptions(location, json, i));            
            i++; //increment the index for the json object.
        }       
    }
}

/*
 * This method gets the "word version" of the location abbreviations.
 * It just returns the "word version", or null.
 */ 
function getDirectionString(abbreviation) {
    switch (abbreviation) {
        case "NW":
            return "Northwest";
        case "N":
            return "North";
        case "NE":
            return "Northeast";
        case "C":
            return "Central";
        case "EC":
            return "East-Central";
        case "WC":
            return "West-Central";
       case "S":
            return "South";
       case "SW":
            return "Southwest";
       case "SE":
            return "Southeast";
      default:
            return null;
    }
}

/*
 * This is the error message.
 */
function errorAjax() {
    console.log("An error has occurred");
}

/*
 * This function creates and returns the option depending on what the json passed back in the "name" section.
 */ 
function getOptions(json, i) {
    var string = '<option>' + json[i]["Name"] + '</option>';
    return string;
}

/*
 * This function creates the label and select for when there isn't one for the airports.
 */
function addLabel(string1, string2) {
    $('#' + string1 + 'AirportLabel').append('<label class="control-label col-lg-12">' + string2 + ' Airport' + '</label>');
    $('#' + string1 + 'AirportSpot').append('<select class="form-control" name = "' + string1 +  'Air">'); //create the select thing and name it.
}

/*
 * This function creates and returns the airport options.
 */
function getAirportOptions(location, json, i) {
    if (location == null) { //if the location is null, don't add it.
        var string = '<option ' + 'value = ' + json[i]["AirportID"] + '>' + json[i]["AirportCode"] + '</option>';
        return string;
    }
    else { //if not create the options with the location.
        var string = '<option ' + 'value = ' + json[i]["AirportID"] + '>' + location + " (" + json[i]["AirportCode"] + ")" + '</option>';
        return string;
    }
}