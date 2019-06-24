/*
 * This module solely tests the getDirectionString() method in the AddMoreSearches.js file.
 */
QUnit.module('FormatLocationString Method Tests');

QUnit.test('Format Oregon location string test', function (assert) {
    // Arrange
    var country = "United States";
    var location = "Oregon";
    var airportCode = "PDX";
    // Act
    var output = FormatLocationString(country, location, airportCode);
    // Assert
    assert.equal(output, "United States, Oregon (PDX)");
});

QUnit.test('Format Paris location string test', function (assert) {
    // Arrange
    var country = "France";
    var location = "Paris";
    var airportCode = "CDG";
    // Act
    var output = FormatLocationString(country, location, airportCode);
    // Assert
    assert.equal(output, "France, Paris (CDG)");
});

QUnit.test('Format Durban location string test', function (assert) {
    // Arrange
    var country = "South Africa";
    var location = "Durban";
    var airportCode = "DUR";
    // Act
    var output = FormatLocationString(country, location, airportCode);
    // Assert
    assert.equal(output, "South Africa, Durban (DUR)");
});

/*
 * This module solely tests the GetHotelStarValueText() method in the AddMoreSearches.js file.
 */
QUnit.module('GetHotelStarValueText Method Tests');

QUnit.test('The hotel star value 1 text test', function (assert) {
    // Arrange
    var starValue = 1;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "0-1");
});

QUnit.test('The hotel star value 2 text test', function (assert) {
    // Arrange
    var starValue = 2;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "1-2");
});

QUnit.test('The hotel star value 3 text test', function (assert) {
    // Arrange
    var starValue = 3;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "2-3");
});

QUnit.test('The hotel star value 4 text test', function (assert) {
    // Arrange
    var starValue = 4;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "3-4");
});

QUnit.test('The hotel star value 5 text test', function (assert) {
    // Arrange
    var starValue = 5;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "4-5");
});

QUnit.test('The no hotel star value text test', function (assert) {
    // Arrange
    var starValue = 0;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "No Preference");
});

QUnit.test('The empty hotel star value text test', function (assert) {
    // Arrange
    var starValue = null;
    // Act
    var output = GetHotelStarValueText(starValue);
    // Assert
    assert.equal(output, "-");
});

/*
 * This module solely tests the FormatCostText() method in the AddMoreSearches.js file.
 */
QUnit.module('FormatCost Method Tests');

QUnit.test('The format cost test of integer', function (assert) {
    // Arrange
    var cost = 100;
    // Act
    var output = FormatCost(cost);
    // Assert
    assert.equal(output, "$100.00");
});

QUnit.test('The format cost test of float', function (assert) {
    // Arrange
    var cost = 350.23;
    // Act
    var output = FormatCost(cost);
    // Assert
    assert.equal(output, "$350.23");
});

QUnit.test('The format cost test of float 2', function (assert) {
    // Arrange
    var cost = 2660.23898987788778;
    // Act
    var output = FormatCost(cost);
    // Assert
    assert.equal(output, "$2660.24");
});

QUnit.test('The format cost test float 3', function (assert) {
    // Arrange
    var cost = 11023456.1234567890;
    // Act
    var output = FormatCost(cost);
    // Assert
    assert.equal(output, "$11023456.12");
});


QUnit.test('The format cost test of 0', function (assert) {
    // Arrange
    var cost = 0;
    // Act
    var output = FormatCost(cost);
    // Assert
    assert.equal(output, "-");
});

QUnit.test('The format cost test of null', function (assert) {
    // Arrange
    var cost = null;
    // Act
    var output = FormatCost(cost);
    // Assert
    assert.equal(output, "-");
});