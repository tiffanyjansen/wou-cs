//Function simply appends additional disclaimer details to the page and adds a button to close the disclaimer.
function detailsClicked() {
    $(".appendLoc").append('<div class="row"><p id="hotel" align="left" style="font-size: smaller"></p></div><div class="row"><p id="food" align="left" style="font-size: smaller"></p></div><div class="row"><div class="mx-auto d-block" id="buttonLoc"></div></div>')
    $("#flight").append("1. <b>Flights:</b> Flights are determined by averging a list of the lowest flight price options available. In some cases, you may see some variation in actual flight prices versus the average provided by us. However our goal is to provide you with a generalized cost that you can expect based on your selected destination. Please keep in mind that unless officially booked with a provider, flight prices are always subject to change.")
    $("#hotel").append("2. <b>Hotel:</b> Hotel prices are also determined by averging a list of the most widely visited hotels in the area selected based on hotel star ratings. The price is also based on a specific number of rooms which is determined by alloting 1 room per every 2 people traveling.  Prices shown may vary based on time of year, number of travelers, specific hotel location, and taxes and fees which are not always fully accounted for.")
    $("#food").append("3. <b>Food:</b> Food cost is calculated by first applying the general average cost to feed 1 person while traveling in the US. Then we adjust that amount based on location as the average US dollar will be worth more or less depending on your destination. Lastly we simply multiply that amount by the total number of days and the total number of people traveling.")
    $("#submit").hide();
    $("#buttonLoc").append("<input id='submit2' class='btn btn-default' type='submit' value='Close Disclaimer Details' onclick='detailsClosed()' />")
}
//Function resets the disclaimer.
function detailsClosed() {
    $("#submit").show();
    $("#flight").empty();
    $("#hotel").empty();
    $("#food").empty();
    $("#buttonLoc").empty();
    $(".appendLoc").empty();
};