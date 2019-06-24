/*
 * The error function adds an error message for incorrect input.
 */
function error(){
    var node = document.getElementById("error");
    var heading = document.createElement("h2");
    var message = document.createTextNode("ERROR! Input received was not a polynomial. Please try again.");
    node.style.background = "red";
    node.style.color = "white";
    node.style.padding = "10px";
    heading.appendChild(message);
    node.appendChild(heading);
}
/*
 * The enter function is the function that is called when 
 * the submit button is clicked on.
 */
function enter(){
    var words = document.getElementById("function").value;
    document.getElementById("function").value = "";
    console.log(words);
    if(check(words) == false){
        error();
    }
    else{
        matched(words);
    }
}
/*
 * The check functionchecks to see if the input
 * is a polynomial or not.
 */
function check(s){
    var reg = /\d+x?\^?\d*(\+|\-)?/g;
    var found = reg.test(s);
    return found;
}
/*
 * The matched function happens when the input received
 * is in fact a polynomial.
 */
function matched(fx){
    document.getElementById("text").remove();
    document.getElementById("text2").remove();
    document.getElementById("func").remove();
    document.getElementById("error").remove();
    document.getElementById("note").remove();
    console.log(fx);
    var firstDer = findDerType(fx);
    var secondDer = findDerType(firstDer);
    if(firstDer === 0){
        fx = parseFunc(fx);
    }
    createTable(fx, firstDer, secondDer);
}
/*
 * The findPieces function finds all of the pieces that matter in 
 * a polynomial function and splits them into the respective parts.
 */
function findPieces(fx){
    var splitReg = /(\+|\-)/;
    var func = fx.split(splitReg).map(String);
    var coef = [];
    var expo = [];
    var k = 0;
    console.log("func = " + func);
    for(var i = 0; i<func.length; i++){
        var poly = /\d*x\^\d+/;
        var spot1 = func[i] + " ";
        var spot2 = func[i] + " ";       
        console.log("For " + i + " testing came out " + poly.test(spot1)); 
        if(poly.test(spot1) == true){
            var coefficient = /\d*x/;
            var exponent = /\^\d+/;
            console.log(i + "   " + coefficient.test(spot1));
            console.log("For " + i + " we made it to the match part.");
            console.log("k = " + k);
            if(spot1.match(coefficient) != null){
                coef[k] = spot1.match(coefficient).map(String);
                console.log(i + "     " + coef[k]);
            }
            if(spot2.match(exponent) != null){
                expo[k] = spot2.match(exponent).map(String);
                console.log(i + "     " + expo[k]);
            }
            k++;
            console.log("k = " + k);
        }
        else{
            var num = /\d+/;
            if(num.test(spot1) == true){
                console.log("YES!");
                func[i] = 0;;
            }
            var coefficient = /\d*x/;
            if(coefficient.test(spot1) == true){
                console.log("YESS!!!");
                expo[k] = "^1";
                coef[k] = spot1.match(coefficient).map(String);
                k++;
            }
        }
    }
    console.log("expo = " + expo);
    console.log("coef = " + coef);
    console.log("coef.length = " + coef.length);
    var ders = [];
    for(var j = 0; j < coef.length; j++){
        var c = coef[j] + " ";
        var e = expo[j] + " ";
        var numC = c.slice(0, -2);
        if(numC == 0){
            numC = 1;
        }
        var numE = e.slice(1, -1);
        console.log("numC = " + numC);
        console.log("numE = " + numE);
        var firDer = findDer(numC, numE);
        console.log("We made it to the loop.");
        ders[j] = firDer;
    }
    console.log(ders);
    var q = 0;
    if(func[0] === ""){
        console.log("Made it to the change of index");
        for(var p = 2; p<func.length; p = p + 2){
            func[p] = ders[q];
            q++;
        }
    }
    else{
        for(var p = 0; p<func.length; p = p + 2){
            func[p] = ders[q];
            q++;
        }
    }
    console.log(func);
    return func;
}
/*
 * The findDer function does the math for the derivatives.
 */
function findDer(coefficient, exponent){
    var newCoef = coefficient*exponent;
    var newExpo = exponent-1;
    console.log("new exponent = " + newExpo);
    console.log("new coefficient = " + newCoef);
    if(newExpo === 0){
        return newCoef;
    }
    if(newExpo === 1){
        return newCoef + "x";
    }
    return newCoef + "x^" + newExpo;
}
/*
 * The findDerType function decides if the function inputted was
 * just a number or a polynomial.
 */
function findDerType(fx){
    var reg = /x+/g;    
    if(reg.test(fx)==true){
        var array = findPieces(fx);
        var der = array[0];
        for(var i = 1; i < array.length; i++){
            der = der + array[i];
        }
        return der;
    }
    else{
        console.log(0);
        return 0;
    }
}
/*
 * The createTable function creates the table.
 */
function createTable(fx, firDer, secDer){
    var data =[{
        Function: fx,
        FirstDer: firDer,
        SecondDer: secDer
      }]
    var columnHead = ["Function f(x)", "First Derivative f'(x)", "Second Derivative f''(x)"];
    var columnHeadings = Object.keys(data[0]);
    var columnCount = columnHeadings.length;
    var rowCount = data.length;
    var table = $('<table>').prependTo('#output');
    var header = $('<thead />').appendTo(table);
    for (var i = 0; i < columnCount; i++) {
        $('<th />', { text: columnHead[i] }).appendTo(header);
    }
    var tBody = $('<tbody />').appendTo(table);
    //get row set up right.
    // Add the data rows to the table body.
    for (var i = 0; i < rowCount; i++) { // each row
        var row = $('<tr />').appendTo(tBody);
        for (var j = 0; j < columnCount; j++) { // each column
            var obj = data[i];
            $('<td />', {
                text: obj[columnHeadings[j]]
            }).attr('data-label', columnHeadings[j].toUpperCase()).appendTo(row);
        }
    }
    document.getElementById("output").style.visibility = "visible";
    document.getElementById("Title").style.background = "lightgreen";
    document.getElementById("picture").style.visibility = "visible";
    document.getElementById("res").style.visibility = "visible";
}
/*
 * The parseFunc function parses the function.
 */
function parseFunc(func){
    var numReg = /\d+/;
    var found = func.match(numReg);
    return found;
}
/*
 * The reset function reloads the page.
 */
function reset(){
    location.reload();
}