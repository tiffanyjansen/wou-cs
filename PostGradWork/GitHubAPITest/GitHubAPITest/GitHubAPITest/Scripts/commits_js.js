
function getCommits(user, repo) {
    var $div = $('.commits');
    $div.html('');    
    $div.append('<h3>Commits in ' + repo + '</h3>');

    $.ajax({ //The Ajax call
        type: "GET",
        dataType: "json",
        url: "/api/commits?user=" + user + "&repo=" + repo,
        success: successAjax,
        error: errorAjax
    });
}

function successAjax(data) {
    console.log("We made it to success.");

    var $div = $('.commits');
    var $table = $('<table class="table"></table>');
    var $thead = $('<thead></thead>');
    var $tr = $('<tr></tr>');

    $tr.append('<th class="text-center bg-warning">SHA</th>');
    $tr.append('<th class="text-center fixed-width bg-warning">Timestamp</th>');
    $tr.append('<th class="text-center bg-warning">Committer</th>');
    $tr.append('<th class="text-center bg-warning">Commit Message</th>');

    $thead.append($tr);
    $table.append($thead);

    var $tbody = $('<tbody></tbody>');
    var $fixedHeight = $('<div class="fixed-height"></div>');
    $.each(data, function (index, value) { //Looping through the data
        var $tr = $('<tr></tr>');        
        $tr.append('<td class="text-center"><a href=' + value.sha_link + '>' + value.sha + '</a></td>');
        $tr.append('<td class="text-center fixed-width">' + value.timestamp + '</td>');
        $tr.append('<td class="text-center">' + value.committer_name + '</td>');
        $tr.append('<td class="text-left">' + value.message + '</td>');
        $tbody.append($tr);
    });

    $table.append($tbody);
    $div.append($table);
}

function errorAjax() {
    console.log("There was an error."); //There was an error... 
}