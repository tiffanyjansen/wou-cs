function addElements() {
    //this presents the total number of searches displayed (by sets of 3)
    var id = $('#NumStates').val().toString();
    console.log("TotalElement at start of js: " + id);
    var source = "/TopLocations/AddElements/" + id;
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
    var EndState = data["EndState"];
    var TimesSearched = data["TimesSearched"];

    //setup up rows for the searches table and add them
    for (i = 0; i < EndState.length; i++) {
        var TableTr = $('<tr>');

        var col1 = $('<td>').text(EndState[i]);
        var col2 = $('<td>').text(TimesSearched[i]);        

        TableTr.append(col1);
        TableTr.append(col2);

        //add row to table with the class "table"
        $('.table').append(TableTr);
    }

    //increment the value with TotalElements id to show table is larger
    $('#NumStates').val(parseInt($('#NumStates').val()) + 10);

    console.log(parseInt($('#TotalNumStates').val()));
    console.log(parseInt($('#NumStates').val()));

    console.log(parseInt($('#TotalNumStates').val()) + " > " + parseInt($('#NumStates').val()));
    console.log((parseInt($('#TotalNumStates').val())) > (parseInt($('#NumStates').val())));

    //decide whether to show to hide the "show more" button
    if ((parseInt($('#TotalNumStates').val())) > (parseInt($('#NumStates').val()))) {
        $('#AddMore').show();
    }
    else {
        $('#AddMore').hide();
    }

}
function detectedError() {
    console.log("Error. Cause could be from AddSearches method in Searches controller. AddMoreSearches.js could also be the cause. Could also be that javascript isn't working for some other reason");
}