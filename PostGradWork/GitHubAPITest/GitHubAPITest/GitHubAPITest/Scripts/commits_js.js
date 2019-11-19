
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
    var $div = $('.commits');
    var $table = $('<table class="table"></table>');
    var $thead = $('<thead></thead>');
    var $tr = $('<tr></tr>');

    $tr.append('<th class="text-center bg-warning">SHA</th>');
    $tr.append('<th class="text-center bg-warning">Timestamp</th>');
    $tr.append('<th class="text-center bg-warning">Committer</th>');
    $tr.append('<th class="text-center bg-warning">Commit Message</th>');

    $thead.append($tr);
    $table.append($thead);

    var $tbody = $('<tbody></tbody>');
    $.each(data, function (index, value) { //Looping through the data
        var $tr = $('<tr></tr>');        
        $tr.append('<td class="text-center">' + value.sha + '</td>');
        $tr.append('<td class="text-center">' + value.timestamp + '</td>');
        $tr.append('<td class="text-center">' + value.committer_name + '</td>');
        $tr.append('<td class="text-left">' + value.message + '</td>');
        $tbody.append($tr);
    });

    $table.append($tbody);
    $div.append($table);
}

function errorAjax(data) {
    console.log("There was an error."); //There was an error... 
}