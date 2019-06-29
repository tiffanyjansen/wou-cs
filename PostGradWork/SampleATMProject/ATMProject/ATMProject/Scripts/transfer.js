$('#startAccount').change(function () {
    var id = $('#startAccount').val().toString();
    var pin = $('#PIN').val().toString();
    var source = "/Home/GetAmount/" + id + "/" + pin;   //the source for the json object.                  
    $.ajax({ //ajax call
        type: "GET",
        dataType: "json",
        url: source,
        success: successAjax,
        error: errorAjax
    });
});

function successAjax(amount) {
    var json = JSON.parse(amount); //parse the json object.
    console.log(json.toString());
    if (amount.length > 0) {
        $('#endAccount').empty();
        var end = findOther(start);
        var endName = getName(end);
        if (endName == "Savings") {
            $('#endAccount').append('<option value="' + end + '"> ' + endName + '($' + amount[0].SavingsAmount.toFixed(2) + ') </option>');
        }
        if (endName == "Checking") {
            $('#endAccount').append('<option value="' + end + '"> ' + endName + '($' + amount[0].CheckingAmount.toFixed(2) + ') </option>');
        }
    }
}

/*
 * Figure out what the other account type is, and return the 'value' for the other account.
 * If input was unexpected return null.
 */ 
function findOther(account) {
    if (account == "check")
        return "save";
    if (account == "save")
        return "check";
    else
        return null;
}

/*
 * Return the name of the account provided, if input was unexpected return null.
 */
function getName(account) {
    if (account == "check")
        return "Checking";
    if (account == 'save')
        return "Savings";
    else
        return null;
}

function errorAjax() {
    console.log("An error has occured.");
}