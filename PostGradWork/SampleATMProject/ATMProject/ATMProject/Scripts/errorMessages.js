var reload_call = function () {
    $('#error').empty();
    $('#moneyError').empty();
}

//This is the timing part, so the page is only there for 'x' seconds before redirecting.
var interval = 3000; //This is the 'x' part. It will be (interval/1000) seconds.
window.setInterval(reload_call, interval); //Call the function above on the given interval.
