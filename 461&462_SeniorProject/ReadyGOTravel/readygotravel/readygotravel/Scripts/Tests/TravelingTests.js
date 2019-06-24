/*
 * This module solely tests the getDirectionString() method in the Traveling.js file.
 */ 
QUnit.module('getDirectionString Method Tests');

QUnit.test('The Northwest abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "NW";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "Northwest", "Northwest passes.");   
});
QUnit.test('The North abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "N";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "North", "North passes.");
});
QUnit.test('The Northeast abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "NE";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "Northeast", "Northeast passes.");
});
QUnit.test('The Central abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "C";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "Central", "Central passes.");
});
QUnit.test('The East-Central abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "EC";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "East-Central", "East-Central passes.");
});
QUnit.test('The West-Central abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "WC";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "West-Central", "West-Central passes.");
});
QUnit.test('The South abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "S";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "South", "South passes.");
});
QUnit.test('The Southwest abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "SW";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "Southwest", "Southwest passes.");
});
QUnit.test('The Southeast abbreviation test', function (assert) {
    // Arrange
    var abbreviation = "SE";
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.equal(output, "Southeast", "Southeast passes.");
});
QUnit.test('null returns null', function (assert) {
    // Arrange
    var abbreviation = null;
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.deepEqual(output, null, "null passes.");
});
QUnit.test('Random string returns null', function (assert) {
    // Arrange
    var abbreviation = "random_string";
    $('')
    // Act
    var output = getDirectionString(abbreviation);
    // Assert
    assert.deepEqual(output, null, "random_string passes.");
});


/*
 * This module solely tests the getOptions() method in the Traveling.js file.
 */
QUnit.module('getOptions Method Test');

QUnit.test('Test if the getOptions method works.', function (assert) {
    // Arrange
    var text = '[ { "ID" : 1, "Name" : "Squeeze" },' +
        '{ "ID" : 2, "Name" : "Terrarium" },' + 
        '{ "ID" : 3, "Name" : "Fahrenheit" }]';
    var json = JSON.parse(text);

    // Act
    //gets the options from my javascript
    var output1 = getOptions(json, 0);
    var output2 = getOptions(json, 1);
    var output3 = getOptions(json, 2);

    //append them in a select to the qunit-fixture
    $('#qunit-fixture').append('<select>' + output1 + output2 + output3 + '</select>');
    var options = $('#qunit-fixture select option');

    // Assert
    assert.equal(options[0].innerHTML, "Squeeze", "The first option should have 'Squeeze'.");
    assert.equal(options[1].innerHTML, "Terrarium", "The second option should have 'Terrarium'.");
    assert.equal(options[2].innerHTML, "Fahrenheit", "The third option should have 'Fahrenheit'.");
});

/*
 * This module solely tests the getAirportOptions() method in the Traveling.js file.
 */
QUnit.module('getAirportOptions Method Tests');

QUnit.test('Test the getAirportOptions Method for when location is null', function (assert) {
    // Arrange
    var text = '[ { "AirportID" : 1, "AirportCode" : "SNO" },' +
        '{ "AirportID" : 2, "AirportCode" : "ABC" },' +
        '{ "AirportID" : 3, "AirportCode" : "DEF" }]';
    var json = JSON.parse(text);

    // Act
    //gets the options from my javascript
    var output1 = getAirportOptions(null, json, 0);
    var output2 = getAirportOptions(null, json, 1);
    var output3 = getAirportOptions(null, json, 2);

    //append them in a select to the qunit-fixture
    $('#qunit-fixture').append('<select>' + output1 + output2 + output3 + '</select>');
    var options = $('#qunit-fixture select option');

    // Assert
    assert.equal(options[0].innerHTML, "SNO", "The first option should have 'SNO'.");
    assert.equal(options[1].innerHTML, "ABC", "The second option should have 'ABC'.");
    assert.equal(options[2].innerHTML, "DEF", "The third option should have 'DEF'.");
});
QUnit.test('Test the getAirportOptions Method for when location is not null', function (assert) {
    // Arrange
    var text = '[ { "AirportID" : 1, "AirportCode" : "SNO" },' +
        '{ "AirportID" : 2, "AirportCode" : "ABC" },' +
        '{ "AirportID" : 3, "AirportCode" : "DEF" }]';
    var json = JSON.parse(text);

    // Act
    //gets the options from my javascript
    var output1 = getAirportOptions('North', json, 0);
    var output2 = getAirportOptions('South', json, 1);
    var output3 = getAirportOptions('Central', json, 2);

    //append them in a select to the qunit-fixture
    $('#qunit-fixture').append('<select>' + output1 + output2 + output3 + '</select>');
    var options = $('#qunit-fixture select option');

    // Assert
    assert.equal(options[0].innerHTML, "North (SNO)", "The first option should have 'North (SNO)'.");
    assert.equal(options[1].innerHTML, "South (ABC)", "The second option should have 'South (ABC)'.");
    assert.equal(options[2].innerHTML, "Central (DEF)", "The third option should have 'Central (DEF)'.");
});